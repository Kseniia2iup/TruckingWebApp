package ru.tsystems.javaschool.repository;

import ru.tsystems.javaschool.model.Driver;

import java.util.List;

public interface DriverDao {

    Driver findDriverById(Integer id);

    void deleteDriver(Integer id);

    void saveDriver(Driver driver);

    void updateDriver(Driver driver);

    List<Driver> findAllDrivers();

    Integer getLastDriverId();
}
