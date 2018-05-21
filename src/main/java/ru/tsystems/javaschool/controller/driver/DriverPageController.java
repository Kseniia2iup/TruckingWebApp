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
import ru.tsystems.javaschool.exceptions.CargoAlreadyDeliveredException;
import ru.tsystems.javaschool.exceptions.TruckingServiceException;
import ru.tsystems.javaschool.model.*;
import ru.tsystems.javaschool.model.enums.CargoStatus;
import ru.tsystems.javaschool.model.enums.DriverStatus;
import ru.tsystems.javaschool.service.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DriverPageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverPageController.class);

    private DriverService driverService;

    private UserService userService;

    private WaypointService waypointService;

    private TruckService truckService;

    private CargoService cargoService;

    private CityService cityService;

    @Autowired
    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    @Autowired
    public void setCargoService(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @Autowired
    public void setTruckService(TruckService truckService) {
        this.truckService = truckService;
    }

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
    public String driverPage(ModelMap model) throws TruckingServiceException {
        User user = userService.findByLogin(getPrincipal());
        model.addAttribute("user", getPrincipal());
        Driver driver =  driverService.findDriverById(user.getId());
        Truck truck = null;
        List<Driver> drivers = new ArrayList<>();
        List<Waypoint> waypoints = new ArrayList<>();
        if(driver.getCurrentTruck()!=null){
            truck  = truckService.findTruckById(driver.getCurrentTruck().getId());
            drivers = driverService.findCoWorkers(driver);
        }
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
    public String setDriverStatus(@PathVariable Integer id, Model model)
            throws TruckingServiceException{
        model.addAttribute("driver", driverService.findDriverById(id));
        return "setdriverstatus";
    }

    @PostMapping(path = "driver/{id}/setStatus")
    public String saveDriverStatus(@PathVariable Integer id, Driver driver, Model model)
            throws TruckingServiceException{
        Driver entityDriver = driverService.findDriverById(id);
        driverService.setDriverStatus(entityDriver,driver.getStatus());
        return "redirect:/driver";
    }

    @GetMapping(path = "driver/{id}/loaded")
    public String loadCargo(@PathVariable Integer id, Cargo cargo, Model model)
            throws TruckingServiceException, CargoAlreadyDeliveredException {
        cargoService.setCargoStatus(cargoService.findCargoById(id), CargoStatus.SHIPPED);
        return "redirect:/driver";
    }

    @GetMapping(path = "driver/{id}/unloaded")
    public String unloadCargo(@PathVariable Integer id, Cargo cargo, Model model)
            throws TruckingServiceException, CargoAlreadyDeliveredException{
        String result =
                cargoService.setCargoStatus(cargoService.findCargoById(id), CargoStatus.DELIVERED);
        if(result.equals("done")){
            return "redirect:/driver/orderisdone";
        }
        return "redirect:/driver";
    }

    @GetMapping(path = "driver/orderisdone")
    public String showOrderIsDonSuccessPage(Model model){
            model.addAttribute("message", "Order is successfully done!" +
                    "\n Thank you!");
        return "orderisdone";
    }

    @GetMapping(path = "driver/{id}/truckIsBroken")
    public String truckIsBroken(@PathVariable Integer id, Model model) throws TruckingServiceException{
        truckService.markTruckAsBrokenWhileOrder(driverService.findDriverById(id).getCurrentTruck().getId());
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

    @ModelAttribute("cities")
    public List<City> cityList() throws TruckingServiceException{
        return cityService.findAllCities();
    }

    @ModelAttribute("driverStatuses")
    public DriverStatus[] driverStatuses(){
        return DriverStatus.values();
    }
}
