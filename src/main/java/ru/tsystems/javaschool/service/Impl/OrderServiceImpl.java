package ru.tsystems.javaschool.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.model.*;
import ru.tsystems.javaschool.repository.DriverDao;
import ru.tsystems.javaschool.repository.OrderDao;
import ru.tsystems.javaschool.repository.TruckDao;
import ru.tsystems.javaschool.service.CityService;
import ru.tsystems.javaschool.service.OrderService;
import ru.tsystems.javaschool.service.WaypointService;

import java.util.ArrayList;
import java.util.List;

@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;

    private CityService cityService;

    private DriverDao driverDao;

    private TruckDao truckDao;

    private WaypointService waypointService;

    @Autowired
    public void setWaypointService(WaypointService waypointService) {
        this.waypointService = waypointService;
    }

    @Autowired
    public void setTruckDao(TruckDao truckDao) {
        this.truckDao = truckDao;
    }

    @Autowired
    public void setDriverDao(DriverDao driverDao) {
        this.driverDao = driverDao;
    }

    @Autowired
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Autowired
    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    public Order findOrderById(Integer id) {
        return orderDao.findOrderById(id);
    }

    @Override
    public void deleteOrder(Integer id) {
        orderDao.deleteOrder(id);
    }

    @Override
    public void saveOrder(Order order) {
        orderDao.saveOrder(order);
    }

    @Override
    public void updateOrder(Order order) {
        orderDao.updateOrder(order);
    }

    @Override
    public List<Order> findAllOrders() {
        return orderDao.findAllOrders();
    }

    /**
     * Calculate approximate summary distance based on Set of waypoints in the order
     * @param order to distance calculate
     * @return approximate summary distance between the order's waypoints
     */
    @Override
    public Double calculateSumDistanceOfOrder(Order order) {
        List<Waypoint> waypoints = waypointService.findAllWaypointsByOrderId(order.getId());
        Double sumDistance = 0d;
        City cityA;

        List<City> cities = new ArrayList<>();
        for (Waypoint waypoint: waypoints
             ) {
            cities.add(waypoint.getCityDep());
            cities.add(waypoint.getCityDest());
        }
        cityA = cities.get(0);
        for (int i = 1; i < cities.size(); i++) {
            sumDistance += cityService.distanceBetweenTwoCity(cityA, cities.get(i));
            cityA = cities.get(i);
        }
        return sumDistance;
    }

    /**
     * Calculate average time in hours for order complete based on approximate summary distance
     * between the order's waypoints
     * @param order to calculate average time of the order fulfillment
     * @return Integer number of hours
     */
    @Override
    public Integer averageTimeInHoursForOrder(Order order) {
        Double averageTruckSpeedInKmPerHour = 70d;
        Double averageTimeOfDrivingInHours = calculateSumDistanceOfOrder(order)/averageTruckSpeedInKmPerHour;
        int averageTimeOfProcessingInHours = 2;
        List<Waypoint> waypoints = waypointService.findAllWaypointsByOrderId(order.getId());
        return waypoints.size()*averageTimeOfProcessingInHours
                +(int)Math.round(averageTimeOfDrivingInHours);

    }

    @Override
    public void removeTruckAndDriversFromOrder(Order order) {
        Truck truck = order.getTruck();
        if (truck!=null){
            List<Driver> drivers = driverDao.getAllDriversOfTruck(truck);
            if(drivers != null){
                for (Driver driver: drivers
                     ) {
                    driver.setCurrentTruck(null);
                    driverDao.updateDriver(driver);
                }
            }
            truck.setOrder(null);
            truckDao.updateTruck(truck);
        }
    }
}
