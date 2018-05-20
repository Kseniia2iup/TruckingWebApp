package ru.tsystems.javaschool.controller.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.tsystems.javaschool.model.*;
import ru.tsystems.javaschool.model.enums.CargoStatus;
import ru.tsystems.javaschool.model.enums.OrderStatus;
import ru.tsystems.javaschool.service.*;

import java.util.List;

@Controller
public class OrderController {

    private static final String ORDER_LIST_VIEW_PATH = "redirect:/manager/listOrders";
    private static final String ADD_ORDER_VIEW_PATH = "neworder";

    @Autowired
    OrderService orderService;

    @Autowired
    CargoService cargoService;

    @Autowired
    WaypointService waypointService;

    @Autowired
    CityService cityService;

    @Autowired
    TruckService truckService;

    @Autowired
    DriverService driverService;

    @GetMapping(path = "manager/listOrders")
    public String listOfOrders(Model model){
        model.addAttribute("orders", orderService.findAllOrders());
        return "allorders";
    }

    @GetMapping(path = "manager/newOrder")
    public String newOrder(Model model){
        Order order = new Order();
        order.setOrderStatus(OrderStatus.CREATED);
        orderService.saveOrder(order);
        model.addAttribute("order", orderService.findOrderById(order.getId()));
        return "redirect:/manager/"+order.getId()+"/listOrderCargoes";
    }

    @GetMapping(path = "manager/{orderId}/listOrderCargoes")
    public String listCargoes(@PathVariable Integer orderId, Model model){
        model.addAttribute("order", orderService.findOrderById(orderId));
        model.addAttribute("cargoes", cargoService.findAllCargoesOfOrder(orderId));
        return ADD_ORDER_VIEW_PATH;
    }

    @GetMapping(path = "manager/{orderId}/newCargo")
    public String newCargo(@PathVariable Integer orderId, Model model){
        if(orderService.findOrderById(orderId).getTruck()!=null ||
                orderService.findOrderById(orderId).getOrderStatus() != OrderStatus.CREATED){
            return ORDER_LIST_VIEW_PATH;
        }
        model.addAttribute("order", orderService.findOrderById(orderId));
        model.addAttribute("cargo", new Cargo());
        return "newcargo";
    }

    @PostMapping(path = "manager/{orderId}/newCargo")
    public String saveCargo(@PathVariable Integer orderId, Cargo cargo, Model model){
        model.addAttribute("order", orderService.findOrderById(orderId));
        Order order = orderService.findOrderById(orderId);
        cargo.setOrder(order);
        cargo.setDelivery_status(CargoStatus.PREPARED);
        cargoService.saveCargo(cargo);
        Waypoint waypoint = new Waypoint();
        waypoint.setOrder(order);
        waypoint.setCargo(cargo);
        waypoint.setCityDep(cargo.getWaypoint().getCityDep());
        waypoint.setCityDest(cargo.getWaypoint().getCityDest());
        waypointService.saveWaypoint(waypoint);
        return "redirect:/manager/"+orderId+"/listOrderCargoes";
    }

    @GetMapping(path = "manager/{id}/setOrderTruck")
    public String setTruckToOrder(@PathVariable Integer id, Model model){
        if(cargoService.findAllCargoesOfOrder(id)==null ||
                orderService.findOrderById(id).getOrderStatus() != OrderStatus.CREATED){
            return ORDER_LIST_VIEW_PATH;
        }
        Order order = orderService.findOrderById(id);
        orderService.removeTruckAndDriversFromOrder(order);
        model.addAttribute("order", order);
        model.addAttribute("trucks",
                truckService.findAllTrucksReadyForOrder(order));
        return "setordertruck";
    }

    @PostMapping(path = "manager/{id}/setOrderTruck")
    public String saveTruckToOrder(@PathVariable Integer id, Order order, Model model){
        Order entityOrder = orderService.findOrderById(id);
        model.addAttribute("order", entityOrder);
        model.addAttribute("trucks",
                truckService.findAllTrucksReadyForOrder(entityOrder));
        entityOrder.setTruck(order.getTruck());
        orderService.updateOrder(entityOrder);
        return "redirect:/manager/"+id+"/setOrderDrivers";
    }

    @GetMapping(path = "manager/{id}/setOrderDrivers")
    public String setDriversToOrder(@PathVariable Integer id, Model model){
        if(orderService.findOrderById(id).getTruck()==null ||
                orderService.findOrderById(id).getOrderStatus() != OrderStatus.CREATED){
            return ORDER_LIST_VIEW_PATH;
        }
        Order order = orderService.findOrderById(id);
        List<Driver> orderDrivers = driverService.getAllDriversOfOrder(order);
        model.addAttribute("order", order);
        model.addAttribute("driver", new Driver());
        model.addAttribute("orderDrivers", orderDrivers);
        model.addAttribute("drivers", driverService.findAllDriversSuitableForOrder(order));
        return "setorderdrivers";
    }

    @PostMapping(path = "manager/{id}/setOrderDrivers")
    public String saveDriversToOrder(@PathVariable Integer id, Driver newDriver, Model model){
        Driver driver = driverService.findDriverById(newDriver.getId());
        Order entityOrder = orderService.findOrderById(id);
        driver.setOrder(entityOrder);
        driver.setCurrentTruck(entityOrder.getTruck());
        driverService.updateDriver(driver);
        return "redirect:/manager/"+id+"/setOrderDrivers";
    }

    @GetMapping(path = "manager/{id}/complete")
    public String completeOrderCreation(@PathVariable Integer id, Model model){
        if(cargoService.findAllCargoesOfOrder(id)==null||
                orderService.findOrderById(id).getTruck()==null ||
                driverService.getAllDriversOfOrder(orderService.findOrderById(id))==null ||
                orderService.findOrderById(id).getOrderStatus() != OrderStatus.CREATED){
            return ORDER_LIST_VIEW_PATH;
        }
        Order order = orderService.findOrderById(id);
        order.setOrderStatus(OrderStatus.IN_PROCESS);
        orderService.updateOrder(order);
        return ORDER_LIST_VIEW_PATH;
    }

    @GetMapping(path = "manager/{id}/cancel")
    public String cancelOrderCreation(@PathVariable Integer id, Model model){
        if(orderService.findOrderById(id).getOrderStatus() != OrderStatus.CREATED){
            return ORDER_LIST_VIEW_PATH;
        }
        orderService.removeTruckAndDriversFromOrder(orderService.findOrderById(id));
        orderService.deleteOrder(id);
        return ORDER_LIST_VIEW_PATH;
    }

    @ModelAttribute("cities")
    public List<City> cityList(){
        return cityService.findAllCities();
    }

    @ModelAttribute("orderStatuses")
    public OrderStatus[] orderStatuses(){
        return OrderStatus.values();
    }
}
