package ru.tsystems.javaschool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.tsystems.javaschool.model.User;
import ru.tsystems.javaschool.model.enums.Role;
import ru.tsystems.javaschool.service.DriverService;
import ru.tsystems.javaschool.service.UserService;
import ru.tsystems.javaschool.service.WaypointService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

    private static final String USER_LIST_VIEW_PATH = "redirect:/admin/listUsers";
    private static final String ADD_USER_VIEW_PATH = "newuser";

    private MessageSource messageSource;

    private UserService userService;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = {"/", "/home", "/welcome", "/index"})
    public String indexPage(Model model)
    {
        return "redirect:/login";
    }

    @GetMapping(path = "/admin")
    public String adminPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "admin";
    }

    @GetMapping(path = "/manager")
    public String managerPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "manager";
    }

    @GetMapping(value = "/Access_Denied")
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "accessDenied";
    }

    @GetMapping(path = "/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping(value="/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }


    @RequestMapping(value = { "/admin/listUsers" }, method = RequestMethod.GET)
    public String listUsers(ModelMap model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "allusers";
    }

    @GetMapping(path = "/admin/newUser")
    public String newRegistration(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("edit", false);
        return ADD_USER_VIEW_PATH;
    }

    @PostMapping(path = "/admin/newUser")
    public String saveRegistration(@Valid User user,
                                   BindingResult result, ModelMap model) {

        if (result.hasErrors()) {
            LOG.debug("There are errors while new user registration");
            return ADD_USER_VIEW_PATH;
        }
        if (!userService.isUserValid(user)){
            return ADD_USER_VIEW_PATH;
        }
        userService.save(user);

        LOG.info("From MainController saveRegistration method:\nLogin : {}\nChecking UsrProfiles....",user.getLogin());

        model.addAttribute("success", "User " + user.getLogin() + " has been registered successfully");
        return "registrationsuccess";
    }

    @RequestMapping(value = { "/admin/edit-user-{login}" }, method = RequestMethod.GET)
    public String editUser(@PathVariable String login, ModelMap model) {
        User user = userService.findByLogin(login);
        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        return ADD_USER_VIEW_PATH;
    }

    @RequestMapping(value = { "/admin/edit-user-{login}" }, method = RequestMethod.POST)
    public String updateUser(@Valid User user, BindingResult result,
                             ModelMap model, @PathVariable String login) {

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("edit", true);
            return ADD_USER_VIEW_PATH;
        }
        userService.updateUser(user);

        model.addAttribute("success", "User " + user.getLogin() + " updated successfully");
        return "registrationsuccess";
    }

    @RequestMapping(value = { "/admin/delete-user-{login}" }, method = RequestMethod.GET)
    public String deleteUser(@PathVariable String login) {
        userService.delete(userService.findByLogin(login).getId());
        return USER_LIST_VIEW_PATH;
    }

    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    @ModelAttribute("adminRegistrationRoles")
    public Role[] adminRegistrationRoles(){
        Role[] roles = new Role[2];
        roles[0] = Role.ADMIN;
        roles[1] = Role.USER;
        return roles;
    }

    @ModelAttribute("roles")
    public Role[] initializeProfiles() {
        return Role.values();
    }

}
