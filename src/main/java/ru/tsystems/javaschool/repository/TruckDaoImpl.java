package ru.tsystems.javaschool.repository;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.model.Truck;

import java.util.List;

@Repository("truckDao")
public class TruckDaoImpl extends AbstractDao<Integer, Truck> implements TruckDao {
    @Override
    public Truck findTruckById(int id) {
        return getByKey(id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Truck findTruckByRegNumber(String regNumber) {
        Query query = getSession().createQuery("SELECT T FROM Truck T WHERE T.reg_number = :reg_number");
        query.setParameter("reg_number", regNumber);
        return (Truck) query.uniqueResult();
    }

    @Override
    public void saveTruck(Truck truck) {
        persist(truck);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteTruckByRegNumber(String regNumber) {
        Query query = getSession().createSQLQuery("delete from trucks where reg_number = :reg_number");
        query.setString("reg_number", regNumber);
        query.executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Truck> findAllTrucks() {
        Query query = getSession().createQuery("Select T from Truck T Join Fetch T.city");
        return query.list();
    }
}
