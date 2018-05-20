package ru.tsystems.javaschool.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.model.Driver;
import ru.tsystems.javaschool.model.Order;
import ru.tsystems.javaschool.model.OrderHistory;
import ru.tsystems.javaschool.model.Truck;
import ru.tsystems.javaschool.model.enums.DriverStatus;
import ru.tsystems.javaschool.repository.DriverDao;
import ru.tsystems.javaschool.service.DriverService;
import ru.tsystems.javaschool.service.OrderHistoryService;
import ru.tsystems.javaschool.service.OrderService;
import ru.tsystems.javaschool.service.UserService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service("driverService")
@Transactional
public class DriverServiceImpl implements DriverService {

    private static final Logger LOG = LoggerFactory.getLogger(DriverServiceImpl.class);

    private UserService userService;

    private DriverDao driverDao;

    private OrderService orderService;

    private OrderHistoryService orderHistoryService;

    @Autowired
    public void setOrderHistoryService(OrderHistoryService orderHistoryService) {
        this.orderHistoryService = orderHistoryService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setDriverDao(DriverDao driverDao) {
        this.driverDao = driverDao;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
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
        Integer lastDriverId = getLastDriverId();
        if (lastDriverId == null){
            lastDriverId = 1;
        }
        String result = driver.getName()
                +'_'
                +lastDriverId
                +random.nextInt(1000);
        while (!userService.isUserLoginUnique(result)){
            StringBuilder stringBuilder = new StringBuilder(result);
            stringBuilder.append(random.nextInt(9));
            result = stringBuilder.toString();
        }
        //System.out.println("------------" + result + "------------");
        return result;
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

        //System.out.println("------------" + result + "------------");
        return result.toString();
    }

    /**
     * Returns all drivers whose aren't working right now, situated in the same city as truck is
     * and will not exceed the time limit of work per month (176 hours) during the execution of the order
     * @param order to complete
     * @return List of Drivers suitable for the order execution
     */
    @Override
    public List<Driver> findAllDriversSuitableForOrder(Order order) {
        try {
            List<Driver> drivers = driverDao.getAllFreeDriversForTruck(order.getTruck());
            List<Driver> result = new ArrayList<>();
            int hoursForOrderExecution = orderService.averageTimeInHoursForOrder(order);
            for (Driver driver : drivers
                    ) {
                if (driver.getWorkedThisMonth()+hoursForOrderExecution <= 176) {
                    result.add(driver);
                }
            }
            return result;
        }
        catch (Exception e){
            LOG.debug("From DriverServiceImpl method findAllDriversSuitableForOrder:\n", e);
            throw e;
        }
    }

    @Override
    public List<Driver> getAllDriversOfTruck(Truck truck) {
        return driverDao.getAllDriversOfTruck(truck);
    }

    @Override
    public List<Driver> getAllDriversOfOrder(Order order) {
        return driverDao.getAllDriversOfOrder(order);
    }

    /**
     * Find all co-workers of the driver
     * @param driver whose co-workers needs to be gotten
     * @return List of Drivers with the same Order that driver has
     */
    @Override
    public List<Driver> findCoWorkers(Driver driver) {
        if(driver.getOrder()!=null) {
            List<Driver> drivers = getAllDriversOfOrder(driver.getOrder());
            int index=-1;
            for (Driver dr: drivers
                 ) {
                if(dr.getId()==driver.getId()){
                    index = drivers.indexOf(dr);
                }
            }
            if(index!=-1){
                drivers.remove(index);
            }
            return drivers;
        }
        return null;
    }

    /**
     * Set new status of work to the Driver. If Driver had status REST and newStatus not Rest
     * set current Date to shiftBegined field at OrderHistory
     * If Driver has newStatus as Rest and old one is not set current Date to shiftEnded
     * field at OrderHistory and then calculate approximately hours of work and update
     * Driver field workedThisMonth
     * @param driver that needs status to change
     * @param newStatus new status to the Driver
     */
    @Override
    public void setDriverStatus(Driver driver, DriverStatus newStatus) {
        DriverStatus oldStatus = driver.getStatus();
        OrderHistory history;

        if (driver.getHistory()!=null){
            history = driver.getHistory();
            orderHistoryService.updateHistory(history);
        }
        else {
            history = new OrderHistory();
            history.setDriver(driver);
            orderHistoryService.saveHistory(history);
        }

        if(oldStatus.equals(DriverStatus.REST) && !newStatus.equals(DriverStatus.REST)){
            history.setShiftBegined(new Date(System.currentTimeMillis()));
            orderHistoryService.updateHistory(history);

            driver.setStatus(newStatus);
            updateDriver(driver);
        }

        //If Driver has ended shift add new hours of work to workedThisMonth field
        else if (!oldStatus.equals(DriverStatus.REST) && newStatus.equals(DriverStatus.REST)){
            history.setShiftEnded(new Date(System.currentTimeMillis()));
            orderHistoryService.updateHistory(history);

            LocalDateTime shiftBegan = LocalDateTime.ofInstant(
                    history.getShiftBegined().toInstant(), ZoneId.systemDefault());
            LocalDateTime shiftEnded = LocalDateTime.ofInstant(
                    history.getShiftEnded().toInstant(), ZoneId.systemDefault());

            if (shiftBegan.getMonthValue() != shiftEnded.getMonthValue()){
                driver.setWorkedThisMonth(shiftEnded.getDayOfMonth()*24 + shiftEnded.getHour());
            }
            else {
                driver.setWorkedThisMonth((shiftEnded.getDayOfMonth()-shiftBegan.getDayOfMonth())
                        *24 + shiftEnded.getHour() - shiftBegan.getHour());
            }

            driver.setStatus(newStatus);
            updateDriver(driver);
        }
        else {
            driver.setStatus(newStatus);
            updateDriver(driver);
        }
    }
}
