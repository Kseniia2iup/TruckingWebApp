package ru.tsystems.javaschool.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.model.Driver;
import ru.tsystems.javaschool.repository.DriverDao;
import ru.tsystems.javaschool.service.DriverService;

import java.util.List;

@Service("driverService")
@Transactional
public class DriverServiceImpl implements DriverService {

    private DriverDao driverDao;

    @Autowired
    public void setDriverDao(DriverDao driverDao) {
        this.driverDao = driverDao;
    }

    @Override
    public Driver findDriverById(Integer id) {
        return driverDao.findDriverById(id);
    }

    @Override
    public void deleteDriver(Integer id) {
        driverDao.deleteDriver(id);
    }

    @Override
    public void saveDriver(Driver driver) {
        driverDao.saveDriver(driver);
    }

    @Override
    public void updateDriver(Driver driver) {
        driverDao.updateDriver(driver);
    }

    @Override
    public List<Driver> findAllDrivers() {
        return driverDao.findAllDrivers();
    }
}
