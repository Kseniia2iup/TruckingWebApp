package ru.tsystems.javaschool.service.Impl;

import org.junit.Test;
import org.mockito.InjectMocks;
import ru.tsystems.javaschool.exceptions.TruckingServiceException;
import ru.tsystems.javaschool.model.Driver;
import ru.tsystems.javaschool.model.Truck;
import ru.tsystems.javaschool.model.enums.DriverStatus;
import ru.tsystems.javaschool.service.DriverService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class DriverServiceImplTest {

    @InjectMocks
    private DriverService driverService = new DriverServiceImpl();

    @Test
    public void generateDriverPasswordTest(){
        Pattern pattern = Pattern.compile("[\\w]{15}");
        Matcher matcher = pattern.matcher(driverService.generateDriverPassword());
        assertTrue(matcher.matches());
    }

    @Test
    public void setHoursOfWorkDependsOnStatusChangingTest_01() throws TruckingServiceException{
        Driver driver = new Driver();
        driver.setId(1);
        driver.setName("John");
        driver.setSurname("Doe");
        driver.setWorkedThisMonth(0);
        driver.setStatus(DriverStatus.REST);

        Driver updatedDriver = driverService.setHoursOfWorkDependsOnStatusChanging(driver, DriverStatus.DRIVING);

        assertTrue(updatedDriver.getStatus().equals(DriverStatus.DRIVING)
                && updatedDriver.getWorkedThisMonth() == 0
                && updatedDriver.getShiftBegined().getDate()==(new Date(System.currentTimeMillis())).getDate());
    }


    @Test
    public void setHoursOfWorkDependsOnStatusChangingTest_02() throws TruckingServiceException{
        Driver driver = new Driver();
        driver.setId(1);
        driver.setName("John");
        driver.setSurname("Doe");
        driver.setWorkedThisMonth(0);
        driver.setStatus(DriverStatus.SECOND_DRIVER);
        driver.setShiftBegined(new Date(System.currentTimeMillis()-400000000));

        Driver updatedDriver = driverService.setHoursOfWorkDependsOnStatusChanging(driver, DriverStatus.REST);

        assertTrue(updatedDriver.getStatus().equals(DriverStatus.REST)
                && updatedDriver.getWorkedThisMonth() == 111
                && updatedDriver.getShiftEnded().getDate()==((new Date(System.currentTimeMillis())).getDate())
        );
    }

    @Test
    public void findAllFreeDriversTest() throws TruckingServiceException{
        List<Driver> allDriversMock = new ArrayList<>();

        Truck truck = new Truck();

        Driver driver = new Driver();
        driver.setId(1);
        driver.setName("John");
        driver.setSurname("Doe");
        driver.setWorkedThisMonth(0);
        driver.setStatus(DriverStatus.SECOND_DRIVER);
        driver.setCurrentTruck(truck);

        Driver driver2 = new Driver();
        driver2.setId(2);
        driver2.setName("Johnny");
        driver2.setSurname("Doe");
        driver2.setWorkedThisMonth(0);
        driver2.setStatus(DriverStatus.REST);

        Driver driver3 = new Driver();
        driver3.setId(3);
        driver3.setName("Jonathan");
        driver3.setSurname("Doe");
        driver3.setWorkedThisMonth(0);
        driver3.setStatus(DriverStatus.REST);
        driver3.setCurrentTruck(truck);

        allDriversMock.add(driver);
        allDriversMock.add(driver2);
        allDriversMock.add(driver3);

        assertTrue((driverService.findAllFreeDrivers(allDriversMock)).size()==2);
    }
}
