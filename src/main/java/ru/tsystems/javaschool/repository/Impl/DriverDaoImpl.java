package ru.tsystems.javaschool.repository.Impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.model.Driver;
import ru.tsystems.javaschool.model.Order;
import ru.tsystems.javaschool.model.Truck;
import ru.tsystems.javaschool.repository.AbstractDao;
import ru.tsystems.javaschool.repository.DriverDao;

import java.util.List;

@Repository("driverDao")
public class DriverDaoImpl extends AbstractDao<Integer, Driver> implements DriverDao {

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Driver findDriverById(Integer id) {
        Query query = getSession().createQuery("Select D from Driver D Join Fetch D.user " +
                "Left Join Fetch D.order Join Fetch D.city Left Join Fetch D.currentTruck WHERE D.id = :id");
        query.setParameter("id", id);
        return (Driver) query.uniqueResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteDriver(Integer id) {
        Query query = getSession().createQuery("Delete Driver D WHERE D.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void saveDriver(Driver driver) {
        persist(driver);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void updateDriver(Driver driver) {
        update(driver);
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Driver> findAllDrivers() {
        Query query = getSession().createQuery("Select D from Driver D Join Fetch D.user " +
                "Left Join Fetch D.order Join Fetch D.city Left Join Fetch D.currentTruck Order by D.name");
        return query.list();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Integer getLastDriverId() {
        Query query = getSession().createQuery("Select max(D.id) from Driver D");
        return (Integer)query.uniqueResult();
    }

    /**
     * Returns all free from work drivers in the same city as truck is
     * @param truck that needs drivers
     * @return list of suitable drivers
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Driver> getAllFreeDriversForTruck(Truck truck) {
        Query query = getSession().createQuery("Select D from Driver D Join Fetch D.city " +
                "Left Join Fetch D.history Left Join Fetch D.currentTruck Left Join Fetch D.order " +
                "Where D.currentTruck is null AND D.city = :truck_city Order by D.name");
        query.setParameter("truck_city", truck.getCity());
        return query.list();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Driver> getAllDriversOfTruck(Truck truck) {
        Query query = getSession().createQuery("Select D from Driver D Join Fetch D.city " +
                "Left Join Fetch D.order Left Join Fetch D.currentTruck Where D.currentTruck = :truck");
        query.setParameter("truck", truck);
        return query.list();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Driver> getAllDriversOfOrder(Order order) {
        Query query = getSession().createQuery("Select D from Driver D Join Fetch D.city " +
                "Left Join Fetch D.order Left Join Fetch D.currentTruck Where D.order = :order");
        query.setParameter("order", order);
        return query.list();
    }
}
