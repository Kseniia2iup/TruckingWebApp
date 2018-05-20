package ru.tsystems.javaschool.repository;

import ru.tsystems.javaschool.model.Driver;
import ru.tsystems.javaschool.model.Order;
import ru.tsystems.javaschool.model.Truck;

import java.util.List;

public interface DriverDao {

    Driver findDriverById(Integer id);

    void deleteDriver(Integer id);

    void saveDriver(Driver driver);

    void updateDriver(Driver driver);

    List<Driver> findAllDrivers();

    Integer getLastDriverId();

    List<Driver> getAllFreeDriversForTruck(Truck truck);

    List<Driver> getAllDriversOfTruck(Truck truck);

    List<Driver> getAllDriversOfOrder(Order order);
}
