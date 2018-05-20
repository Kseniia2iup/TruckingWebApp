package ru.tsystems.javaschool.repository;

import ru.tsystems.javaschool.model.Cargo;

import java.util.List;

public interface CargoDao {

    Cargo findCargoById(Integer id);

    void deleteCargo(Integer id);

    void saveCargo(Cargo cargo);

    void updateCargo(Cargo cargo);

    List<Cargo> findAllCargoesOfOrder(Integer orderId);
}
