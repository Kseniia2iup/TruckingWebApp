package ru.tsystems.javaschool.service;

import ru.tsystems.javaschool.model.Driver;
import ru.tsystems.javaschool.model.Order;
import ru.tsystems.javaschool.model.Truck;
import ru.tsystems.javaschool.model.enums.DriverStatus;

import java.util.List;

public interface DriverService {

    Driver findDriverById(Integer id);

    void deleteDriver(Integer id);

    void saveDriver(Driver driver);

    void updateDriver(Driver driver);

    List<Driver> findAllDrivers();

    Integer getLastDriverId();

    String generateDriverLogin(Driver driver);

    String generateDriverPassword();

    List<Driver> findAllDriversSuitableForOrder(Order order);

    List<Driver> getAllDriversOfTruck(Truck truck);

    List<Driver> getAllDriversOfOrder(Order order);

    List<Driver> findCoWorkers(Driver driver);

    void setDriverStatus(Driver driver, DriverStatus newStatus);
}
