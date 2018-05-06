package ru.tsystems.javaschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.model.Truck;
import ru.tsystems.javaschool.repository.TruckDao;

import java.util.List;

@Service("truckServicr")
@Transactional
public class TruckServiceImpl implements TruckService {

    @Autowired
    private TruckDao truckDao;

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
        Truck truck1 = truckDao.findTruckById(truck.getId());
        if(truck1!=null){
            truck1.setRegNumber(truck.getRegNumber());
            truck1.setShiftPeriod(truck.getShiftPeriod());
            truck1.setCapacityTon(truck.getCapacityTon());
            truck1.setCondition(truck.getCondition());
        }
    }

    @Override
    public boolean isTruckRegNumberUnique(Integer id, String reg_number) {
        Truck truck = findTruckByRegNumber(reg_number);
        return (truck == null || ((id!=null) && (truck.getId() == id)));
    }
}
