package ru.tsystems.javaschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.tsystems.javaschool.model.City;
import ru.tsystems.javaschool.model.Truck;
import ru.tsystems.javaschool.model.enums.TruckStatus;
import ru.tsystems.javaschool.service.CityService;
import ru.tsystems.javaschool.service.TruckService;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Controller
public class TruckController {

    private static final String TRUCK_LIST_VIEW_PATH = "redirect:/listTrucks";
    private static final String ADD_TRUCK_VIEW_PATH = "addtruck";

    @Autowired
    TruckService truckService;

    @Autowired
    CityService cityService;

    @Autowired
    MessageSource messageSource;

    @GetMapping(path = {"listTrucks"})
    public String listOfTrucks(Model model) {
        List<Truck> trucks = truckService.findAllTrucks();
        model.addAttribute("trucks", trucks);
        return "alltrucks";
    }

    @GetMapping(path = { "/delete-{reg_number}-truck" })
    public String deleteTruck(@PathVariable String reg_number) {
        truckService.deleteTruckByRegNumber(reg_number);
        return TRUCK_LIST_VIEW_PATH;
    }

    /*
     * This method will provide the medium to add a new truck.
     */
    @GetMapping(path = { "/newTruck" })
    public String newTruck(ModelMap model) {
        Truck truck = new Truck();
        model.addAttribute("truck", truck);
        model.addAttribute("edit", false);
        return ADD_TRUCK_VIEW_PATH;
    }

    /*
     * This method will be called on form submission, handling POST request for
     * saving truck in database. It also validates the user input
     */
    @PostMapping(path = { "/newTruck" })
    public String saveTruck(@Valid Truck truck, BindingResult result,
                               ModelMap model) {

        if (result.hasErrors()) {
            return ADD_TRUCK_VIEW_PATH;
        }

        /*
         * Preferred way to achieve uniqueness of field [regNumber] should be implementing custom @Unique annotation
         * and applying it on field [regNumber] of Model class [Truck].
         *
         * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
         * framework as well while still using internationalized messages.
         *
         */
        if(!truckService.isTruckRegNumberUnique(truck.getId(), truck.getRegNumber())){
            FieldError regNumError = new FieldError("truck","reg_number",messageSource.getMessage("non.unique.reg_number", new String[]{truck.getRegNumber()}, Locale.getDefault()));
            result.addError(regNumError);
            return ADD_TRUCK_VIEW_PATH;
        }

        if (!truckService.isTruckRegNumberIsValid(truck.getId(), truck.getRegNumber())){
            FieldError regNumError = new FieldError("truck","reg_number",messageSource.getMessage("non.valid.reg_number", new String[]{truck.getRegNumber()}, Locale.getDefault()));
            result.addError(regNumError);
            return ADD_TRUCK_VIEW_PATH;
        }

        truckService.saveTruck(truck);

        model.addAttribute("success", "Truck " + truck.getRegNumber() + " added successfully");
        return TRUCK_LIST_VIEW_PATH;
    }

    /*
     * This method will provide the medium to update an existing truck.
     */
    @GetMapping(path = { "/edit-{id}-truck" })
    public String editTruck(@PathVariable Integer id, ModelMap model) {
        Truck truck = truckService.findTruckById(id);
        model.addAttribute("truck", truck);
        model.addAttribute("edit", true);
        return ADD_TRUCK_VIEW_PATH;
    }

    /*
     * This method will be called on form submission, handling POST request for
     * updating truck in database. It also validates the user input
     */
    @PostMapping(path = { "/edit-{id}-truck" })
    public String updateTruck(@Valid Truck truck, BindingResult result,
                                 ModelMap model, @PathVariable Integer id) {

        if (result.hasErrors()) {
            return ADD_TRUCK_VIEW_PATH;
        }

        if(!truckService.isTruckRegNumberUnique(truck.getId(), truck.getRegNumber())){
            FieldError regNumError = new FieldError("truck","reg_number",messageSource.getMessage("non.unique.reg_number", new String[]{truck.getRegNumber()}, Locale.getDefault()));
            result.addError(regNumError);
            return ADD_TRUCK_VIEW_PATH;
        }

        if (!truckService.isTruckRegNumberIsValid(truck.getId(), truck.getRegNumber())){
            FieldError regNumError = new FieldError("truck","reg_number",messageSource.getMessage("non.valid.reg_number", new String[]{truck.getRegNumber()}, Locale.getDefault()));
            result.addError(regNumError);
            return ADD_TRUCK_VIEW_PATH;
        }

        truckService.updateTruck(truck);

        model.addAttribute("success", "Truck " + truck.getRegNumber()  + " updated successfully");
        return TRUCK_LIST_VIEW_PATH;
    }

    @ModelAttribute("cities")
    public List<City> cityList(){
        return cityService.findAllCities();
    }

    @ModelAttribute("truckConditions")
    public TruckStatus[] truckConditions(){
        return TruckStatus.values();
    }
}
