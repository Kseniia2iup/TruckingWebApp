package ru.tsystems.javaschool.service.Impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import ru.tsystems.javaschool.exceptions.TruckingServiceException;
import ru.tsystems.javaschool.model.Order;
import ru.tsystems.javaschool.model.Truck;
import ru.tsystems.javaschool.model.enums.TruckStatus;
import ru.tsystems.javaschool.service.TruckService;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class TruckServiceImplTest {

    private static List<Truck> allTrucksMock;

    @Before
    public void initTrucksList(){
        allTrucksMock = new ArrayList<>();

        Order order = new Order();

        Truck truck = new Truck();
        truck.setId(1);
        truck.setRegNumber("AA20394");
        truck.setCondition(TruckStatus.FAULTY);
        truck.setShiftPeriod(2);
        truck.setCapacityTon(4);

        Truck truck2 = new Truck();
        truck2.setId(2);
        truck2.setRegNumber("AA20395");
        truck2.setCondition(TruckStatus.OK);
        truck2.setShiftPeriod(2);
        truck2.setCapacityTon(4);
        truck2.setOrder(order);

        Truck truck3 = new Truck();
        truck3.setId(3);
        truck3.setRegNumber("AA20396");
        truck3.setCondition(TruckStatus.FAULTY);
        truck3.setShiftPeriod(2);
        truck3.setCapacityTon(4);

        allTrucksMock.add(truck);
        allTrucksMock.add(truck2);
        allTrucksMock.add(truck3);
    }

    @InjectMocks
    private TruckService truckService = new TruckServiceImpl();

    @Test
    public void findAllBrokenTrucksTest() throws TruckingServiceException {
        List<Truck> result = truckService.findAllBrokenTrucks(allTrucksMock);
        assertTrue(result.size()==2 && result.get(0).getId()==1 && result.get(1).getId()==3);
    }

    @Test
    public void findAllTrucksOnOrderTest() throws TruckingServiceException{
        List<Truck> result = truckService.findAllTrucksOnOrder(allTrucksMock);
        assertTrue(result.size()==1 && result.get(0).getId()==2);
    }
}
