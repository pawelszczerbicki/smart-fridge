package services;

import DAO.RoleDao;
import DAO.UserDao;
import model.Email;
import model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private EmailService emailService;

    private String emailContent = "Your new password to fridge: ";

    private String emailSubject = "Smart fRidge - new password";

    private static final Logger logger = Logger.getLogger(UserAuthentication.class);

    public boolean restoreUserPassword(User user) {
        String newPassword = getRandomString(10);
        user.setPassword(DigestUtils.md5Hex(newPassword));
        try {
            emailService.sendEmail(new Email(emailSubject, emailContent + newPassword, user.getEmail()));
        } catch (Exception e) {
            logger.error("Error sending message", e);
            return false;
        }
        userDao.save(user);
        return true;
    }

    public void saveUser(User user) {
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        user.setRole(roleDao.findById(user.getRole().getId()));
        userDao.save(user);

    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public Long countAll() {
        return userDao.countAll();
    }

    private String getRandomString(int length) {
        String charset = "!@#$%^&*()0123456789abcdefghijklmnopqrstuvwxyz";
        Random rand = new Random(System.currentTimeMillis());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int pos = rand.nextInt(charset.length());
            sb.append(charset.charAt(pos));
        }
        return sb.toString();
    }

}
