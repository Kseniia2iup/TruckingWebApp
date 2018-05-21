package ru.tsystems.javaschool.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.model.Cargo;
import ru.tsystems.javaschool.model.City;
import ru.tsystems.javaschool.model.Order;
import ru.tsystems.javaschool.model.Truck;
import ru.tsystems.javaschool.model.enums.CargoStatus;
import ru.tsystems.javaschool.model.enums.OrderStatus;
import ru.tsystems.javaschool.repository.CargoDao;
import ru.tsystems.javaschool.service.CargoService;
import ru.tsystems.javaschool.service.CityService;
import ru.tsystems.javaschool.service.OrderService;
import ru.tsystems.javaschool.service.TruckService;

import java.util.List;

@Service("cargoService")
@Transactional
public class CargoServiceImpl implements CargoService {

    private CargoDao cargoDao;

    private OrderService orderService;

    private TruckService truckService;

    private CityService cityService;

    @Autowired
    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    @Autowired
    public void setTruckService(TruckService truckService) {
        this.truckService = truckService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

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

    /**
     * Update status of Cargo and status of Order if all Cargoes have already delivered
     * @param cargo with old status
     * @param newStatus to change
     * @return String with new Status and Status of Order if it changed
     */
    @Override
    public String setCargoStatus(Cargo cargo, CargoStatus newStatus) {
        Cargo entityCargo = findCargoById(cargo.getId());
        CargoStatus oldStatus = entityCargo.getDelivery_status();
        Order order = orderService.findOrderById(entityCargo.getOrder().getId());
        Truck truck = truckService.findTruckById(order.getTruck().getId());
        if(oldStatus.equals(CargoStatus.DELIVERED)
                || cargo.getOrder().getOrderStatus().equals(OrderStatus.DONE)){
            //
        }
        if(newStatus.equals(CargoStatus.SHIPPED)){
            City currentProcessCity = cityService.findCityById(entityCargo.getWaypoint().getCityDep().getId());
            truck.setCity(currentProcessCity);
            truckService.updateTruck(truck);

            entityCargo.setDelivery_status(newStatus);
            cargoDao.updateCargo(entityCargo);
            return "shipped";
        }
        else {
            City currentProcessCity = cityService.findCityById(entityCargo.getWaypoint().getCityDest().getId());
            truck.setCity(currentProcessCity);
            truckService.updateTruck(truck);

            entityCargo.setDelivery_status(newStatus);
            cargoDao.updateCargo(entityCargo);
            List<Cargo> cargoes = cargoDao.findAllCargoesOfOrder(cargo.getOrder().getId());
            int count = 0;
            for (Cargo cargoFromList: cargoes
                 ) {
                if(cargoFromList.getDelivery_status().equals(CargoStatus.DELIVERED)){
                    count++;
                }
            }
            if(cargoes.size()==count){
                order.setOrderStatus(OrderStatus.DONE);
                orderService.updateOrder(order);
                orderService.removeTruckAndDriversFromOrder(order);
                return "done";
            }
            else {
                return "delivered";
            }
        }
    }
}
