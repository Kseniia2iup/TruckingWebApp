package ru.tsystems.javaschool.repository;

import ru.tsystems.javaschool.model.Waypoint;

import java.util.List;

public interface WaypointDao {

    Waypoint findWaypointById(Integer id);

    void deleteWaypoint(Integer id);

    void saveWaypoint(Waypoint waypoint);

    void updateWaypoint(Waypoint waypoint);

    List<Waypoint> findAllWaypointsByOrderId(Integer orderId);

    List<Waypoint> findAllWaypointsByCargoId(Integer cargoId);
}
