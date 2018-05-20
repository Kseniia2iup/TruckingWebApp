package ru.tsystems.javaschool.repository.Impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.model.Cargo;
import ru.tsystems.javaschool.repository.AbstractDao;
import ru.tsystems.javaschool.repository.CargoDao;

import java.util.List;

@Repository
public class CargoDaoImpl extends AbstractDao<Integer, Cargo> implements CargoDao {

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Cargo findCargoById(Integer id) {
        Query query = getSession().createQuery("Select C from Cargo C " +
                "Join Fetch C.order Join Fetch C.waypoints W Join Fetch W.cityDep Join Fetch W.cityDest WHERE C.id = :id");
        query.setParameter("id", id);
        return (Cargo) query.uniqueResult();
    }

    @Override
    public void deleteCargo(Integer id) {
        delete(findCargoById(id));
    }

    @Override
    public void saveCargo(Cargo cargo) {
        persist(cargo);
    }

    @Override
    public void updateCargo(Cargo cargo) {
        update(cargo);
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Cargo> findAllCargoesOfOrder(Integer orderId) {
        Query query = getSession().createQuery("Select C from Cargo C " +
                "Where C.order.id = :id");
        query.setParameter("id", orderId);
        return query.list();
    }
}
