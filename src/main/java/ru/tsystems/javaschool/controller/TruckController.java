package ru.tsystems.javaschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.tsystems.javaschool.model.Truck;
import ru.tsystems.javaschool.service.TruckService;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Controller
public class TruckController {

    @Autowired
    TruckService truckService;

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = {"listTrucks"})
    public String listOfTrucks(Model model) {
        List<Truck> trucks = truckService.findAllTrucks();
        model.addAttribute("trucks", trucks);
        return "alltrucks";
    }

    @RequestMapping(value = { "/delete-{reg_number}-truck" }, method = RequestMethod.GET)
    public String deleteTruck(@PathVariable String reg_number) {
        truckService.deleteTruckByRegNumber(reg_number);
        return "redirect:/listTrucks";
    }

    /*
     * This method will provide the medium to add a new employee.
     */
    @RequestMapping(value = { "/newTruck" }, method = RequestMethod.GET)
    public String newTruck(ModelMap model) {
        Truck truck = new Truck();
        model.addAttribute("tuck", truck);
        model.addAttribute("edit", false);
        return "addtruck";
    }

    /*
     * This method will be called on form submission, handling POST request for
     * saving employee in database. It also validates the user input
     */
    @RequestMapping(value = { "/newTruck" }, method = RequestMethod.POST)
    public String saveEmployee(@Valid Truck truck, BindingResult result,
                               ModelMap model) {

        if (result.hasErrors()) {
            return "addtruck";
        }

        /*
         * Preferred way to achieve uniqueness of field [ssn] should be implementing custom @Unique annotation
         * and applying it on field [ssn] of Model class [Employee].
         *
         * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
         * framework as well while still using internationalized messages.
         *
         */
        if(!truckService.isTruckRegNumberUnique(truck.getId(), truck.getRegNumber())){
            FieldError regNumError = new FieldError("truck","reg_number",messageSource.getMessage("non.unique.reg_number", new String[]{truck.getRegNumber()}, Locale.getDefault()));
            result.addError(regNumError);
            return "addtruck";
        }

        truckService.saveTruck(truck);

        model.addAttribute("success", "Truck " + truck.getRegNumber() + " added successfully");
        return "redirect:/listTrucks";
    }

    /*
     * This method will provide the medium to update an existing employee.
     */
    @RequestMapping(value = { "/edit-{reg_number}-truck" }, method = RequestMethod.GET)
    public String editEmployee(@PathVariable String reg_number, ModelMap model) {
        Truck truck = truckService.findTruckByRegNumber(reg_number);
        model.addAttribute("truck", truck);
        model.addAttribute("edit", true);
        return "addtruck";
    }

    /*
     * This method will be called on form submission, handling POST request for
     * updating employee in database. It also validates the user input
     */
    @RequestMapping(value = { "/edit-{reg_number}-truck" }, method = RequestMethod.POST)
    public String updateEmployee(@Valid Truck truck, BindingResult result,
                                 ModelMap model, @PathVariable String reg_number) {

        if (result.hasErrors()) {
            return "addtruck";
        }

        if(!truckService.isTruckRegNumberUnique(truck.getId(), truck.getRegNumber())){
            FieldError regNumError = new FieldError("truck","reg_number",messageSource.getMessage("non.unique.reg_number", new String[]{truck.getRegNumber()}, Locale.getDefault()));
            result.addError(regNumError);
            return "addtruck";
        }

        truckService.updateTruck(truck);

        model.addAttribute("success", "Truck " + truck.getRegNumber()  + " updated successfully");
        return "redirect:/listTrucks";
    }
}
