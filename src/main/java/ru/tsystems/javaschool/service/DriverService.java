package ru.tsystems.javaschool.service;

import ru.tsystems.javaschool.model.Driver;

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

}
