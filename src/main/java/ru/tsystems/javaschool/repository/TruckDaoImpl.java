package ru.tsystems.javaschool.repository;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.tsystems.javaschool.model.Truck;

import java.util.List;

@Repository("truckDao")
public class TruckDaoImpl extends AbstractDao<Integer, Truck> implements TruckDao {
    @Override
    public Truck findTruckById(int id) {
        return getByKey(id);
    }

    @Override
    public Truck findTruckByRegNumber(String reg_number) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("reg_number", reg_number));
        return (Truck) criteria.uniqueResult();
    }

    @Override
    public void saveTruck(Truck truck) {
        persist(truck);
    }

    @Override
    public void deleteTruckByRegNumber(String reg_number) {
        Query query = getSession().createSQLQuery("delete from trucks where reg_number = :reg_number");
        query.setString("reg_number", reg_number);
        query.executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Truck> findAllTrucks() {
        Criteria criteria = createEntityCriteria();
        return (List<Truck>) criteria.list();
    }
}
