package ru.tsystems.javaschool.repository;

import ru.tsystems.javaschool.model.Truck;

import java.util.List;

public interface TruckDao {

    Truck findTruckById(int id);

    Truck findTruckByRegNumber(String regNumber);

    void saveTruck(Truck truck);

    void deleteTruckByRegNumber(String regNumber);

    List<Truck> findAllTrucks();
}
