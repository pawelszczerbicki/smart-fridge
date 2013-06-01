package controller;

import DAO.RoleDao;
import model.Fridge;
import model.Role;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import services.FridgeService;
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
    private FridgeService fridgeService;

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

    @RequestMapping(value = "/users/get", params = "term", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getUsersLogins(HttpServletRequest request) {
        return userService.getAllMachingUsernames(request.getParameter("term"), 10);
    }

    @RequestMapping(value = "/users/delete/{id}", method = RequestMethod.GET)
    public String deleteUser(Model model, @PathVariable String id) {
        userService.remove(id);
        model.addAttribute("user_removed", true);
        getStandardUsersAttributes(model);
        return "users";
    }

    @RequestMapping(value = "/users/deactivate/{id}", method = RequestMethod.GET)
    public String deactivateUser(Model model, @PathVariable String id) {
        userService.userActivation(id, false);
        model.addAttribute("user_deactivated", true);
        getStandardUsersAttributes(model);
        return "users";
    }

    @RequestMapping(value = "/users/activate/{id}", method = RequestMethod.GET)
    public String activateUser(Model model, @PathVariable String id) {
        userService.userActivation(id, true);
        model.addAttribute("user_activated", true);
        getStandardUsersAttributes(model);
        return "users";
    }

    @RequestMapping(value = "/users/edit/{id}", method = RequestMethod.GET)
    public String editUserPanel(Model model, @PathVariable String id) {
        User user = userService.findById(id);
        user.setPassword("hidden");
        getStandardEditedUserParameters(model, userService.findById(id));
        return "edit";
    }

    @RequestMapping(value = "/users/edit", method = RequestMethod.POST)
    public String editUser(Model model, @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        User oldUser = userService.findById(user.getId());

        if (bindingResult.hasErrors()) {
            model.addAttribute("fail", true);
        } else if (userService.findByUsername(user.getLogin()) != null && !oldUser.getLogin().equals(user.getLogin())) {
            model.addAttribute("user_exist", true);
        } else {
            userService.editUserByAdmin(oldUser, user);
            model.addAttribute("user_edited", true);
        }
        getStandardEditedUserParameters(model, user);
        return "edit";
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

    @RequestMapping(value = "/role/delete/{id}", method = RequestMethod.GET)
    public String deleteRole(Model model, @PathVariable Integer id) {
        roleDao.remove(roleDao.findById(id));
        getStandardUsersAttributes(model);
        model.addAttribute("role_removed", true);
        return "users";
    }

    @RequestMapping(value = "/fridges", method = RequestMethod.GET)
    public String getFridges(Model model) {
        model.addAttribute("fridges", fridgeService.findAll());
        model.addAttribute("fridge", new Fridge());
        return "fridges";
    }

    @RequestMapping(value = "/fridges/add", method = RequestMethod.POST)
    public String addFridge(Model model, @Valid @ModelAttribute("fridge") Fridge fridge, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            getStandardFridgeAttributes(model, fridge);
            model.addAttribute("fail", true);
            return "fridges";
        } else if (fridgeService.fridgeExist(fridge.getName())) {
            getStandardFridgeAttributes(model, fridge);
            model.addAttribute("fridge_exists", true);
            return "fridges";
        }
        fridgeService.save(fridge);
        model.addAttribute("fridge_added", true);
        getStandardFridgeAttributes(model, fridge);
        return "fridges";
    }

    @RequestMapping(value = "/fridges/delete/{id}", method = RequestMethod.GET)
    public String deleteFridge(Model model, @PathVariable String id) {
        fridgeService.remove(id);
        getStandardFridgeAttributes(model, new Fridge());
        model.addAttribute("fridge_removed", true);
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

    private void getStandardFridgeAttributes(Model model, Fridge fridge) {
        model.addAttribute("fridges", fridgeService.findAll());
        model.addAttribute("fridge", fridge);

    }

    private void getStandardEditedUserParameters(Model model, User user) {
        model.addAttribute("roles", roleDao.findAll());
        model.addAttribute("fridges", fridgeService.findAll());
        model.addAttribute("user", user);
    }

    private void getAttributes(Model model, boolean user, boolean role) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("usersCount", userService.countAll());
        model.addAttribute("fridges", fridgeService.findAll());
        if (user)
            model.addAttribute("user", new User());
        model.addAttribute("roles", roleDao.findAll());
        model.addAttribute("rolesCount", roleDao.countAll());
        if (role)
            model.addAttribute("role", new Role());
    }
}
