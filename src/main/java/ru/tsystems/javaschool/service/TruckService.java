package ru.tsystems.javaschool.service;

import ru.tsystems.javaschool.model.Order;
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

    boolean isTruckRegNumberIsValid(Integer id, String reg_number);

    List<Truck> findAllTrucksReadyForOrder(Order order);
}
