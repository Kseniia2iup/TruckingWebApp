package ru.tsystems.javaschool.service;

import ru.tsystems.javaschool.model.Truck;

import java.util.List;

public interface TruckService {

    Truck findTruckById(int id);

    Truck findTruckByRegNumber(String regNumber);

    void saveTruck(Truck truck);

    void deleteTruckByRegNumber(String regNumber);

    List<Truck> findAllTrucks();

    void updateTruck(Truck truck);

    boolean isTruckRegNumberUnique(Integer id, String reg_number);
}
