package services;

import DAO.RoleDao;
import DAO.UserDao;
import model.Email;
import model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    private String emailContentSuccess = "Your new password to fridge: ";

    private String emailContentFail = "Your account is not active. Contact your administrator";

    private String emailSubject = "Smart fRidge - new password";

    private static final Logger logger = Logger.getLogger(UserAuthentication.class);

    public boolean restoreUserPassword(User user) {

        String newPassword = getRandomString(10);
        user.setPassword(DigestUtils.md5Hex(newPassword));
        try {
            if (user.isActive()) {
                emailService.sendEmail(new Email(emailSubject, emailContentSuccess + newPassword, user.getEmail()));
            } else {
                emailService.sendEmail(new Email(emailSubject, emailContentFail, user.getEmail()));
                return true;
            }
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

    public void save(User user) {
        userDao.save(user);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public Long countAll() {
        return userDao.countAll();
    }

    public void remove(String id) {
        userDao.remove(userDao.findById(id));
    }

    public boolean userExist(String login) {
        return userDao.findByUsername(login) == null ? false : true;
    }

    public User findById(String id) {
        return userDao.findById(id);
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public void userActivation(String id, boolean active) {
        User user = userDao.findById(id);
        user.setActive(active);
        userDao.save(user);
    }

    public void editUserByAdmin(User oldUser, User editedUser) {
        oldUser.setLogin(editedUser.getLogin());
        oldUser.setRole(roleDao.findById(editedUser.getRole().getId()));
        oldUser.setFridge(editedUser.getFridge());
        oldUser.setName(editedUser.getName());
        oldUser.setSurname(editedUser.getSurname());
        oldUser.setEmail(editedUser.getEmail());
        userDao.save(oldUser);
    }

    public List<String> getAllMachingUsernames(String username, Integer limit) {
        List<String> usernames = new ArrayList<String>();
        for (User user : userDao.findUsernameByRegex(username, limit)) {
            usernames.add(user.getLogin());
        }
        return usernames;
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
