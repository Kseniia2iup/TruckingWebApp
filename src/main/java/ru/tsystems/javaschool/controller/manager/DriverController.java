package ru.tsystems.javaschool.controller.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.tsystems.javaschool.exceptions.TruckingServiceException;
import ru.tsystems.javaschool.model.City;
import ru.tsystems.javaschool.model.Driver;
import ru.tsystems.javaschool.model.User;
import ru.tsystems.javaschool.model.enums.DriverStatus;
import ru.tsystems.javaschool.model.enums.Role;
import ru.tsystems.javaschool.service.CityService;
import ru.tsystems.javaschool.service.DriverService;
import ru.tsystems.javaschool.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class DriverController {

    private static final String DRIVER_LIST_VIEW_PATH = "redirect:/manager/listDrivers";
    private static final String ADD_DRIVER_VIEW_PATH = "newdriver";

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

    private CityService cityService;

    @Autowired
    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    @RequestMapping(path = "manager/listDrivers")
    public String listOfDrivers(Model model) throws TruckingServiceException {
        model.addAttribute("drivers", driverService.findAllDrivers());
        return "alldrivers";
    }


    @GetMapping(path = { "manager/delete-{id}-driver" })
    public String deleteTruck(@PathVariable Integer id) throws TruckingServiceException {
        driverService.deleteDriver(id);
        userService.delete(id);
        return DRIVER_LIST_VIEW_PATH;
    }

    @GetMapping(path = { "manager/newDriver" })
    public String newDriver(ModelMap model) {
        model.addAttribute("driver", new Driver());
        model.addAttribute("edit", false);
        return ADD_DRIVER_VIEW_PATH;
    }

    @PostMapping(path = { "manager/newDriver" })
    public String saveDriver(@ModelAttribute Driver driver, BindingResult result,
                            ModelMap model) throws TruckingServiceException {

        if (result.hasErrors()) {
            return ADD_DRIVER_VIEW_PATH;
        }
        User user = new User();
        user.setLogin(driverService.generateDriverLogin(driver));
        user.setPassword(driverService.generateDriverPassword());
        user.setRole(Role.DRIVER);
        userService.save(user);
        driver.setId(user.getId());
        driver.setWorkedThisMonth(0);
        driver.setStatus(DriverStatus.REST);
        driverService.saveDriver(driver);

        model.addAttribute("success", "Driver " + driver.getName()
                + " " + driver.getSurname() + " added successfully");
        return DRIVER_LIST_VIEW_PATH;
    }


    @GetMapping(value = { "manager/edit-{id}-driver" })
    public String editDriver(@PathVariable Integer id, ModelMap model) throws TruckingServiceException {
        Driver driver = driverService.findDriverById(id);
        model.addAttribute("driver", driver);
        model.addAttribute("edit", true);
        return ADD_DRIVER_VIEW_PATH;
    }

    @PostMapping(value = { "manager/edit-{id}-driver" })
    public String editDriver(@Valid Driver driver, BindingResult result,
                             ModelMap model, @PathVariable Integer id) throws TruckingServiceException {

        if (result.hasErrors()) {
            model.addAttribute("edit", true);
            return ADD_DRIVER_VIEW_PATH;
        }
        Driver entityDriver = driverService.findDriverById(id);
        driver.setStatus(entityDriver.getStatus());
        driver.setWorkedThisMonth(entityDriver.getWorkedThisMonth());
        driverService.updateDriver(driver);

        return DRIVER_LIST_VIEW_PATH;
    }

    @ModelAttribute("cities")
    public List<City> cityList() throws TruckingServiceException {
        return cityService.findAllCities();
    }

    @ModelAttribute("driverStatuses")
    public DriverStatus[] driverStatuses(){
        return DriverStatus.values();
    }
}
