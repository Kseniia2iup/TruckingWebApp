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
import ru.tsystems.javaschool.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class MainController {

    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

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
        model.addAttribute("greeting", "Oh, It's finally works!");
        return "index";
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

    @GetMapping(path = "/driver")
    public String driverPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "driver";
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


    @GetMapping(path = "/newUser")
    public String newRegistration(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        return "newuser";
    }

    @PostMapping(path = "/newUser")
    public String saveRegistration(@Valid User user,
                                   BindingResult result, ModelMap model) {

        if (result.hasErrors()) {
            LOG.debug("There are errors while new user registration");
            return "newuser";
        }
        userService.save(user);

        LOG.info("From MainController saveRegistration method:\nLogin : {}\nChecking UsrProfiles....",user.getLogin());

        model.addAttribute("success", "User " + user.getLogin() + " has been registered successfully");
        return "registrationsuccess";
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

    @ModelAttribute("roles")
    public Role[] initializeProfiles() {
        return Role.values();
    }

}
