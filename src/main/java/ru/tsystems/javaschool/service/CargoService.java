package ru.tsystems.javaschool.service;

import ru.tsystems.javaschool.model.Cargo;
import ru.tsystems.javaschool.model.enums.CargoStatus;

import java.util.List;

public interface CargoService {

    Cargo findCargoById(Integer id);

    void deleteCargo(Integer id);

    void saveCargo(Cargo cargo);

    void updateCargo(Cargo cargo);

    List<Cargo> findAllCargoesOfOrder(Integer orderId);

    String setCargoStatus(Cargo cargo, CargoStatus newStatus);
}
