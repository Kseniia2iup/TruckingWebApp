package ru.tsystems.javaschool.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.model.Waypoint;
import ru.tsystems.javaschool.repository.WaypointDao;
import ru.tsystems.javaschool.service.WaypointService;

import java.util.List;

@Service("waypointService")
@Transactional
public class WaypointServiceImpl implements WaypointService {

    private WaypointDao waypointDao;

    @Autowired
    public void setWaypointDao(WaypointDao waypointDao) {
        this.waypointDao = waypointDao;
    }

    @Override
    public Waypoint findWaypointById(Integer id) {
        return waypointDao.findWaypointById(id);
    }

    @Override
    public void deleteWaypoint(Integer id) {
        waypointDao.deleteWaypoint(id);
    }

    @Override
    public void saveWaypoint(Waypoint waypoint) {
        waypointDao.saveWaypoint(waypoint);
    }

    @Override
    public void updateWaypoint(Waypoint waypoint) {
        waypointDao.updateWaypoint(waypoint);
    }

    @Override
    public List<Waypoint> findAllWaypointsByOrderId(Integer orderId) {
        return waypointDao.findAllWaypointsByOrderId(orderId);
    }

    @Override
    public List<Waypoint> findAllWaypointsByCargoId(Integer cargoId) {
        return waypointDao.findAllWaypointsByCargoId(cargoId);
    }
}
