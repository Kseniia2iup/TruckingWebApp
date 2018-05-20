package ru.tsystems.javaschool.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.model.Cargo;
import ru.tsystems.javaschool.repository.CargoDao;
import ru.tsystems.javaschool.service.CargoService;

import java.util.List;

@Service("cargoService")
@Transactional
public class CargoServiceImpl implements CargoService {

    private CargoDao cargoDao;

    @Autowired
    public void setCargoDao(CargoDao cargoDao) {
        this.cargoDao = cargoDao;
    }

    @Override
    public Cargo findCargoById(Integer id) {
        return cargoDao.findCargoById(id);
    }

    @Override
    public void deleteCargo(Integer id) {
        cargoDao.deleteCargo(id);
    }

    @Override
    public void saveCargo(Cargo cargo) {
        cargoDao.saveCargo(cargo);
    }

    @Override
    public void updateCargo(Cargo cargo) {
        cargoDao.updateCargo(cargo);
    }

    @Override
    public List<Cargo> findAllCargoesOfOrder(Integer orderId) {
        return cargoDao.findAllCargoesOfOrder(orderId);
    }
}
