package ru.tsystems.javaschool.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.model.Cargo;
import ru.tsystems.javaschool.model.Order;
import ru.tsystems.javaschool.model.Truck;
import ru.tsystems.javaschool.model.enums.CargoStatus;
import ru.tsystems.javaschool.model.enums.OrderStatus;
import ru.tsystems.javaschool.model.enums.TruckStatus;
import ru.tsystems.javaschool.repository.CargoDao;
import ru.tsystems.javaschool.repository.OrderDao;
import ru.tsystems.javaschool.repository.TruckDao;
import ru.tsystems.javaschool.service.OrderService;
import ru.tsystems.javaschool.service.TruckService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("truckService")
@Transactional
public class TruckServiceImpl implements TruckService {

    private TruckDao truckDao;

    private OrderDao orderDao;

    private OrderService orderService;

    private CargoDao cargoDao;

    @Autowired
    public void setCargoDao(CargoDao cargoDao) {
        this.cargoDao = cargoDao;
    }

    @Autowired
    public void setTruckDao(TruckDao truckDao) {
        this.truckDao = truckDao;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public Truck findTruckById(int id) {
        return truckDao.findTruckById(id);
    }

    @Override
    public Truck findTruckByRegNumber(String regNumber) {
        return truckDao.findTruckByRegNumber(regNumber);
    }

    @Override
    public void saveTruck(Truck truck) {
        truckDao.saveTruck(truck);
    }

    @Override
    public void deleteTruckByRegNumber(String regNumber) {
        truckDao.deleteTruckByRegNumber(regNumber);
    }

    @Override
    public List<Truck> findAllTrucks() {
        return truckDao.findAllTrucks();
    }

    @Override
    public void updateTruck(Truck truck) {
        truckDao.updateTruck(truck);
    }

    @Override
    public boolean isTruckRegNumberUnique(Integer id, String regNumber) {
        Truck truck = findTruckByRegNumber(regNumber);
        return (truck == null || ((id!=null) && (truck.getId() == id)));
    }

    @Override
    public boolean isTruckRegNumberIsValid(Integer id, String regNumber) {
        Pattern pattern = Pattern.compile("[a-zA-Z]{2}\\d{5}");
        Matcher matcher = pattern.matcher(regNumber);
        return matcher.matches();
    }

    /**
     * Returns all Trucks that have OK condition and enough capacity to carry order's cargoes
     * @param order that needs a truck
     * @return List of Trucks suitable for the order
     */
    @Override
    public List<Truck> findAllTrucksReadyForOrder(Order order) {
        List<Cargo> cargoes = cargoDao.findAllCargoesOfOrder(order.getId());
        int cargoMaxWeightKg = 0;
        if(cargoes!=null) {
            for (Cargo cargo : cargoes
                    ) {
                if (!cargo.getDelivery_status().equals(CargoStatus.DELIVERED)&&
                        (cargo.getWeight() > cargoMaxWeightKg)) {
                    cargoMaxWeightKg = cargo.getWeight();
                }
            }
        }
        double maxWeightTon = cargoMaxWeightKg/1000d;
        List<Truck> result = new ArrayList<>();
        List<Truck> trucks = truckDao.findAllOKTrucks();
        for (Truck truck: trucks
             ) {
            if(!orderDao.isTruckHasOrder(truck.getId()) && ((double)truck.getCapacityTon() >= maxWeightTon)){
                result.add(truck);
            }
        }
        return result;
    }

    /**
     * Set Truck status as FAULT, remove all Drivers and Truck from Order, set Order Status
     * as INTERRUPTED, mark all SHIPPED Cargoes as PREPARED and change their cities of
     * departure to the Truck's current City
     * @param id of Truck that was broken
     */
    @Override
    public void markTruckAsBrokenWhileOrder(Integer id) {
        Truck entityTruck = truckDao.findTruckById(id);
        Order order = entityTruck.getOrder();
        List<Cargo> cargoes = cargoDao.findAllCargoesOfOrder(order.getId());
        for (Cargo cargo: cargoes
             ) {
            if(cargo.getDelivery_status().equals(CargoStatus.SHIPPED)) {
                cargo.setDelivery_status(CargoStatus.PREPARED);
                cargo.getWaypoint().setCityDep(entityTruck.getCity());
                cargoDao.updateCargo(cargo);
            }
        }

        orderService.removeTruckAndDriversFromOrder(order);
        order.setOrderStatus(OrderStatus.INTERRUPTED);
        orderService.updateOrder(order);

        entityTruck.setCondition(TruckStatus.FAULTY);
        truckDao.updateTruck(entityTruck);
    }
}
