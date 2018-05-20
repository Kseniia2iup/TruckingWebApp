package ru.tsystems.javaschool.repository.Impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.model.Truck;
import ru.tsystems.javaschool.model.enums.TruckStatus;
import ru.tsystems.javaschool.repository.AbstractDao;
import ru.tsystems.javaschool.repository.TruckDao;

import java.util.List;

@Repository("truckDao")
public class TruckDaoImpl extends AbstractDao<Integer, Truck> implements TruckDao {

    @Override
    public Truck findTruckById(int id) {
        Query query = getSession().createQuery("Select T from Truck T Join Fetch T.city" +
                " WHERE T.id = :id");
        query.setParameter("id", id);
        return (Truck) query.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Truck findTruckByRegNumber(String regNumber) {
        Query query = getSession().createQuery("SELECT T FROM Truck T Join Fetch T.city" +
                " WHERE T.regNumber = :regNumber");
        query.setParameter("regNumber", regNumber);
        return (Truck) query.uniqueResult();
    }

    @Override
    public void saveTruck(Truck truck) {
        persist(truck);
    }

    @Override
    public void updateTruck(Truck truck) {
        update(truck);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteTruckByRegNumber(String regNumber) {
        Query query = getSession().createQuery("Delete Truck T WHERE T.regNumber = :regNumber");
        query.setParameter("regNumber", regNumber);
        query.executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Truck> findAllTrucks() {
        Query query = getSession().createQuery("Select T from Truck T Join Fetch T.city " +
                " Order by T.regNumber");
        return query.list();
    }

    /**
     * Finds all trucks with OK condition
     * @return list of suitable trucks
     */
    @Override
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Truck> findAllOKTrucks() {
        Query query = getSession().createQuery("Select T from Truck T Join Fetch T.city " +
                "Where T.condition = :condition Order by T.regNumber");
        query.setParameter("condition", TruckStatus.OK);
        return query.list();
    }
}
