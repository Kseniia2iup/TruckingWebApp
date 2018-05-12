package ru.tsystems.javaschool.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.model.Driver;
import ru.tsystems.javaschool.repository.DriverDao;
import ru.tsystems.javaschool.service.DriverService;

import java.util.List;
import java.util.Random;

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

    @Override
    public Integer getLastDriverId() {
        return driverDao.getLastDriverId();
    }

    /**
     * Generate driver login based on driver name, max id form drivers table and random number
     * @param driver current driver
     * @return random login
     */
    @Override
    public String generateDriverLogin(Driver driver) {
        Random random = new Random();
        return driver.getName()
                +'_'
                +getLastDriverId()
                +random.nextInt(1000);
    }

    /**
     * Generate driver password that contains numbers, lower and upper case characters
     * @return random password
     */
    @Override
    public String generateDriverPassword() {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            result.append(random.nextInt(10));
            result.append((char) (random.nextInt(26) + 65));
            result.append((char) (random.nextInt(26) + 96));
        }
        return result.toString();
    }
}
