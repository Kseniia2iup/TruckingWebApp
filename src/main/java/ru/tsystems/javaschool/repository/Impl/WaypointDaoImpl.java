package ru.tsystems.javaschool.repository.Impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.model.Waypoint;
import ru.tsystems.javaschool.repository.AbstractDao;
import ru.tsystems.javaschool.repository.WaypointDao;

import java.util.List;

@Repository
public class WaypointDaoImpl extends AbstractDao<Integer, Waypoint> implements WaypointDao {

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Waypoint findWaypointById(Integer id) {
        Query query = getSession().createQuery("Select W from Waypoint W " +
                "Join Fetch W.order Join Fetch W.cityDep Join Fetch W.cityDest Join Fetch W.cargo WHERE W.id = :id");
        query.setParameter("id", id);
        return (Waypoint) query.uniqueResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteWaypoint(Integer id) {
        delete(findWaypointById(id));
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void saveWaypoint(Waypoint waypoint) {
        persist(waypoint);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void updateWaypoint(Waypoint waypoint) {
        update(waypoint);
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Waypoint> findAllWaypointsByOrderId(Integer orderId) {
        Query query = getSession().createQuery("Select W from Waypoint W " +
                "Join Fetch W.order Join Fetch W.cityDep Join Fetch W.cityDest Join Fetch W.cargo WHERE W.order.id = :id");
        query.setParameter("id", orderId);
        return query.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Waypoint> findAllWaypointsByCargoId(Integer cargoId) {
        Query query = getSession().createQuery("Select W from Waypoint W " +
                "Join Fetch W.order Join Fetch W.cityDep Join Fetch W.cityDest Join Fetch W.cargo WHERE W.cargo_id = :id");
        query.setParameter("id", cargoId);
        return query.list();
    }
}
