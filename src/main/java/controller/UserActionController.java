package controller;

import DAO.UserDao;
import model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import services.UserService;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserActionController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/restorePassword", method = RequestMethod.POST)
    public String restorePassword(@RequestParam("username") String username, Model model) {
        User user = userDao.findByUsername(username);
        Map<String, Boolean> paramsMap = new HashMap<String, Boolean>();


        if (user == null)
            paramsMap.put("no_user", true);

        else if (user.getEmail() == null)
            paramsMap.put("no_email", true);
        else
            paramsMap.put("new_password_is_set", userService.restoreUserPassword(user));

        model.addAllAttributes(paramsMap);
        return "login";
    }
    @RequestMapping(value = "/panel", method = RequestMethod.GET)
    public String panel(Model model, Principal principal) {
        User user = userDao.findByUsername(principal.getName());
        user.setPassword("");
        model.addAttribute("user", user);

        return "panel";
    }
    @RequestMapping(value = "/panel", method = RequestMethod.POST)
    public String changeData(Model model, @ModelAttribute("user") User newUser) {
        User user = userDao.findById(newUser.getId());
        String newPassword = newUser.getPassword()== null || newUser.getPassword().trim() == "" ? user.getPassword(): DigestUtils.md5Hex(newUser.getPassword());
        user.setPassword(newPassword);
        user.setEmail(newUser.getEmail());
        user.setName(newUser.getName());
        user.setSurname(newUser.getSurname());
        user.setUpdatedAt(new Date());
        userDao.save(user);
        user.setPassword("");
        model.addAttribute("user", user);
        model.addAttribute("modified", true);
        return "panel";
    }
}
