package ru.tsystems.javaschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.tsystems.javaschool.model.Driver;
import ru.tsystems.javaschool.model.User;
import ru.tsystems.javaschool.service.DriverService;
import ru.tsystems.javaschool.service.UserService;

@Controller
public class DriverController {

    private static final String DRIVER_LIST_VIEW_PATH = "redirect:/listDrivers";
    private static final String ADD_DRIVER_VIEW_PATH = "adddriver";

    private DriverService driverService;

    @Autowired
    public void setDriverService(DriverService driverService) {
        this.driverService = driverService;
    }

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "listDrivers")
    public String listOfDrivers(Model model){
        model.addAttribute("drivers", driverService.findAllDrivers());
        return "alldrivers";
    }


    @GetMapping(path = { "/newDriver" })
    public String newDriver(ModelMap model) {
        model.addAttribute("driver", new Driver());
        model.addAttribute("edit", false);
        return ADD_DRIVER_VIEW_PATH;
    }

    @PostMapping(path = { "/newDriver" })
    public String saveDriver(@ModelAttribute Driver driver, BindingResult result,
                            ModelMap model) {

        if (result.hasErrors()) {
            return ADD_DRIVER_VIEW_PATH;
        }
        User user = driver.getUser();

        driverService.saveDriver(driver);

        model.addAttribute("success", "Driver " + driver.getName()
                + " " + driver.getSurname() + " added successfully");
        return DRIVER_LIST_VIEW_PATH;
    }
}
