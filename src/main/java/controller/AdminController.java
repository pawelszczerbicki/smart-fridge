package controller;

import DAO.FridgeDao;
import DAO.RoleDao;
import model.Fridge;
import model.Role;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private FridgeDao fridgeDao;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String panel(Model model) {
        getStandardUsersAttributes(model);
        return "users";
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public String addUser(Model model, @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            getUsersAttributesWithoutNewUser(model);
            model.addAttribute("fail", true);
            user.setPassword("");
            model.addAttribute("user", user);
            return "users";
        } else if (userService.userExist(user.getLogin())) {
            getUsersAttributesWithoutNewUser(model);
            model.addAttribute("user_exists", true);
            user.setPassword("");
            model.addAttribute("user", user);
            return "users";
        }
        userService.saveUser(user);
        model.addAttribute("user_added", true);
        getStandardUsersAttributes(model);
        return "users";
    }

    @RequestMapping(value = "/users/get", params="term", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getUsersLogins(HttpServletRequest request) {
        return userService.getAllMachingUsernames(request.getParameter("term"), 10);
    }

    @RequestMapping(value = "/role/add", method = RequestMethod.POST)
    public String addRole(Model model, @Valid @ModelAttribute("role") Role role, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            getUsersAttributesWithoutNewRole(model);
            model.addAttribute("role", role);
            model.addAttribute("fail", true);
            return "users";
        }
        roleDao.save(role);
        getStandardUsersAttributes(model);
        model.addAttribute("role_added", true);
        return "users";
    }

    @RequestMapping(value = "/fridges", method = RequestMethod.GET)
    public String getFridges(Model model) {
        model.addAttribute("fridge", new Fridge());
        model.addAttribute("fridges", fridgeDao.findAll());

        return "fridges";
    }


    private void getStandardUsersAttributes(Model model) {

        getAttributes(model, true, true);
    }

    private void getUsersAttributesWithoutNewRole(Model model) {
        getAttributes(model, true, false);
    }

    private void getUsersAttributesWithoutNewUser(Model model) {
        getAttributes(model, false, true);
    }

    private void getAttributes(Model model, boolean user, boolean role) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("usersCount", userService.countAll());
        if (user)
            model.addAttribute("user", new User());
        model.addAttribute("roles", roleDao.findAll());
        model.addAttribute("rolesCount", roleDao.countAll());
        if (role)
            model.addAttribute("role", new Role());
    }
}
