package ru.tsystems.javaschool.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.exceptions.TruckingServiceException;
import ru.tsystems.javaschool.model.*;
import ru.tsystems.javaschool.model.enums.CargoStatus;
import ru.tsystems.javaschool.model.enums.DriverStatus;
import ru.tsystems.javaschool.repository.*;
import ru.tsystems.javaschool.service.CityService;
import ru.tsystems.javaschool.service.OrderService;
import ru.tsystems.javaschool.service.WaypointService;

import java.util.ArrayList;
import java.util.List;

@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

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
    public Order findOrderById(Integer id) throws TruckingServiceException {
        try {
            return orderDao.findOrderById(id);
        }
        catch (Exception e){
            LOGGER.warn("Something went wrong\n", e);
            throw new TruckingServiceException(e);
        }
    }

    @Override
    public void deleteOrder(Integer id) throws TruckingServiceException {
        try {
            removeTruckAndDriversFromOrder(findOrderById(id));
            orderDao.deleteOrder(id);
        }
        catch (Exception e){
            LOGGER.warn("Something went wrong\n", e);
            throw new TruckingServiceException(e);
        }
    }

    @Override
    public void saveOrder(Order order) throws TruckingServiceException {
        try {
            orderDao.saveOrder(order);
        }
        catch (Exception e){
            LOGGER.warn("Something went wrong\n", e);
            throw new TruckingServiceException(e);
        }
    }

    @Override
    public void updateOrder(Order order) throws TruckingServiceException {
        try {
            orderDao.updateOrder(order);
        }
        catch (Exception e){
            LOGGER.warn("Something went wrong\n", e);
            throw new TruckingServiceException(e);
        }
    }

    @Override
    public List<Order> findAllOrders() throws TruckingServiceException {
        try {
            return orderDao.findAllOrders();
        }
        catch (Exception e){
            LOGGER.warn("Something went wrong\n", e);
            throw new TruckingServiceException(e);
        }
    }

    /**
     * Calculate approximate summary distance based on Set of waypoints in the order
     * @param order to distance calculate
     * @return approximate summary distance between the order's waypoints
     */
    @Override
    public Double calculateSumDistanceOfOrder(Order order) throws TruckingServiceException {
        try {
            List<Waypoint> waypoints = waypointService.findAllWaypointsByOrderId(order.getId());
            List<Waypoint> waypointsWithNotDeliveredCargoes = new ArrayList<>();
            for (Waypoint wp : waypoints
                    ) {
                if (!wp.getCargo().getDelivery_status().equals(CargoStatus.DELIVERED)) {
                    waypointsWithNotDeliveredCargoes.add(wp);
                }
            }
            Double sumDistance = 0d;
            City cityA;

            List<City> cities = new ArrayList<>();
            for (Waypoint waypoint : waypointsWithNotDeliveredCargoes
                    ) {
                cities.add(waypoint.getCityDep());
                cities.add(waypoint.getCityDest());
            }
            cityA = cities.get(0);
            for (int i = 1; i < cities.size(); i++) {
                sumDistance += cityService.distanceBetweenTwoCities(cityA, cities.get(i));
                cityA = cities.get(i);
            }
            return sumDistance;
        }
        catch (Exception e){
            LOGGER.warn("Something went wrong\n", e);
            throw new TruckingServiceException(e);
        }
    }

    /**
     * Calculate average time in hours for order complete based on approximate summary distance
     * between the order's waypoints
     * @param order to calculate average time of the order fulfillment
     * @return Integer number of hours
     */
    @Override
    public Integer averageTimeInHoursForOrder(Order order) throws TruckingServiceException {
        try {
            Double averageTruckSpeedInKmPerHour = 70d;
            Double averageTimeOfDrivingInHours = calculateSumDistanceOfOrder(order) / averageTruckSpeedInKmPerHour;
            int averageTimeOfProcessingInHours = 2;
            List<Waypoint> waypoints = waypointService.findAllWaypointsByOrderId(order.getId());
            List<Waypoint> waypointsWithNotDeliveredCargoes = new ArrayList<>();
            for (Waypoint wp : waypoints
                    ) {
                if (!wp.getCargo().getDelivery_status().equals(CargoStatus.DELIVERED)) {
                    waypointsWithNotDeliveredCargoes.add(wp);
                }
            }
            return waypointsWithNotDeliveredCargoes.size() * averageTimeOfProcessingInHours
                    + (int) Math.round(averageTimeOfDrivingInHours);
        }
        catch (Exception e){
            LOGGER.warn("Something went wrong\n", e);
            throw new TruckingServiceException(e);
        }

    }

    @Override
    public void removeTruckAndDriversFromOrder(Order order) throws TruckingServiceException {
        try {
            Order entityOrder = orderDao.findOrderById(order.getId());
            Truck truck = entityOrder.getTruck();
            if (truck != null) {
                List<Driver> drivers = driverDao.getAllDriversOfTruck(truck);
                if (drivers != null) {
                    for (Driver driver : drivers
                            ) {
                        driver.setCurrentTruck(null);
                        driver.setOrder(null);
                        driver.setStatus(DriverStatus.REST);
                        driverDao.updateDriver(driver);
                    }
                }
                truck.setOrder(null);
                truckDao.updateTruck(truck);
                entityOrder.setTruck(null);
                orderDao.updateOrder(entityOrder);
            }
        }
        catch (Exception e){
            LOGGER.warn("Something went wrong\n", e);
            throw new TruckingServiceException(e);
        }
    }
}
