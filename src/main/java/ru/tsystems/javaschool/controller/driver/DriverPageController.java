package ru.tsystems.javaschool.controller.driver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.tsystems.javaschool.model.Driver;
import ru.tsystems.javaschool.model.Truck;
import ru.tsystems.javaschool.model.User;
import ru.tsystems.javaschool.model.Waypoint;
import ru.tsystems.javaschool.model.enums.DriverStatus;
import ru.tsystems.javaschool.service.DriverService;
import ru.tsystems.javaschool.service.UserService;
import ru.tsystems.javaschool.service.WaypointService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DriverPageController {

    private static final Logger LOG = LoggerFactory.getLogger(DriverPageController.class);

    private DriverService driverService;

    private UserService userService;

    private WaypointService waypointService;

    @Autowired
    public void setWaypointService(WaypointService waypointService) {
        this.waypointService = waypointService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setDriverService(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping(path = "/driver")
    public String driverPage(ModelMap model) {
        User user = userService.findByLogin(getPrincipal());
        model.addAttribute("user", getPrincipal());
        Driver driver =  driverService.findDriverById(user.getId());
        Truck truck = driver.getCurrentTruck();
        List<Driver> drivers = new ArrayList<>();
        if(truck!=null){
            drivers = driverService.findCoWorkers(driver);
        }
        List<Waypoint> waypoints = new ArrayList<>();
        if(driver.getOrder()!= null){
            waypoints = waypointService.findAllWaypointsByOrderId(driver.getOrder().getId());
        }
        model.addAttribute("driver", driver);
        model.addAttribute("truck", truck);
        model.addAttribute("drivers", drivers);
        model.addAttribute("order", driver.getOrder());
        model.addAttribute("waypoints", waypoints);
        return "driver";
    }

    @GetMapping(path = "driver/{id}/setStatus")
    public String setDriverStatus(@PathVariable Integer id, Model model){
        model.addAttribute("driver", driverService.findDriverById(id));
        return "setdriverstatus";
    }

    @PostMapping(path = "driver/{id}/setStatus")
    public String saveDriverStatus(@PathVariable Integer id, Driver driver, Model model){
        Driver entityDriver = driverService.findDriverById(id);
        driverService.setDriverStatus(entityDriver,driver.getStatus());
        return "redirect:/driver";
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

    @ModelAttribute("driverStatuses")
    public DriverStatus[] driverStatuses(){
        return DriverStatus.values();
    }
}
