package ru.tsystems.javaschool.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.model.Truck;
import ru.tsystems.javaschool.repository.TruckDao;
import ru.tsystems.javaschool.service.TruckService;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("truckService")
@Transactional
public class TruckServiceImpl implements TruckService {

    private TruckDao truckDao;

    @Autowired
    public void setTruckDao(TruckDao truckDao) {
        this.truckDao = truckDao;
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
    public boolean isTruckRegNumberUnique(Integer id, String reg_number) {
        Truck truck = findTruckByRegNumber(reg_number);
        return (truck == null || ((id!=null) && (truck.getId() == id)));
    }

    @Override
    public boolean isTruckRegNumberIsValid(Integer id, String reg_number) {
        Pattern pattern = Pattern.compile("[a-zA-Z]{2}\\d{5}");
        Matcher matcher = pattern.matcher(reg_number);
        return matcher.matches();
    }
}
