package ru.tsystems.javaschool.repository.Impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import ru.tsystems.javaschool.model.Driver;
import ru.tsystems.javaschool.repository.AbstractDao;
import ru.tsystems.javaschool.repository.DriverDao;

import java.util.List;

@Repository("driverDao")
public class DriverDaoImpl extends AbstractDao<Integer, Driver> implements DriverDao {

    @Override
    public Driver findDriverById(Integer id) {
        return getByKey(id);
    }

    @Override
    public void deleteDriver(Integer id) {
        Query query = getSession().createQuery("Delete Driver D WHERE D.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void saveDriver(Driver driver) {
        persist(driver);
    }

    @Override
    public void updateDriver(Driver driver) {
        update(driver);
    }

    @Override
    public List<Driver> findAllDrivers() {
        Query query = getSession().createQuery("Select D from Driver D Join Fetch D.city");
        return query.list();
    }
}
