package services;

import model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

@Service
public class UserAuthentication implements UserDetailsService {
    private static final Logger logger = Logger.getLogger(UserAuthentication.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    private static Map<Integer, List<GrantedAuthority>> authorityMap = new HashMap<Integer, List<GrantedAuthority>>();
    private static List<GrantedAuthority> adminAuthorities = new ArrayList<GrantedAuthority>();
    private static List<GrantedAuthority> userAuthorities = new ArrayList<GrantedAuthority>();

    static {
        adminAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        adminAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        userAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        authorityMap.put(1, adminAuthorities);
        authorityMap.put(2, userAuthorities);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = getUser(s);
        logger.info("User try to check fridge " + user);
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), true, user.isActive(), true, true, authorityMap.get(user.getRole().getId()));
    }

    public String getSessionIDForUser(String login, String password) {
        User user = mongoTemplate.findOne(new Query(Criteria.where("login").is(login)).addCriteria(Criteria.where("password").is(DigestUtils.md5Hex(password))), User.class);
        if (user != null) {
            user.setSessionCode(DigestUtils.md5Hex(new BigInteger(130, new SecureRandom()).toString(32)));
            user.setCodeUpdatedAt(new Date());
            mongoTemplate.save(user);
            return user.getSessionCode();
        }
        return "error";
    }

    public boolean updateSessionId(String sessionId) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, -30);
        User user = mongoTemplate.findOne(new Query(Criteria.where("sessionCode").is(sessionId)), User.class);
        if (user.getCodeUpdatedAt().compareTo(now.getTime()) > 0) {
            user.setCodeUpdatedAt(new Date());
            mongoTemplate.save(user);
            return true;
        }
        return false;
    }

    private User getUser(String username) {
        return mongoTemplate.findOne(new Query(Criteria.where("login").is(username)), User.class);
    }
}
