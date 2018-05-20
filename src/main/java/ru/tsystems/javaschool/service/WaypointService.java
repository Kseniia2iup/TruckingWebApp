package ru.tsystems.javaschool.service;

import ru.tsystems.javaschool.model.Waypoint;

import java.util.List;

public interface WaypointService {

    Waypoint findWaypointById(Integer id);

    void deleteWaypoint(Integer id);

    void saveWaypoint(Waypoint waypoint);

    void updateWaypoint(Waypoint waypoint);

    List<Waypoint> findAllWaypointsByOrderId(Integer orderId);

    List<Waypoint> findAllWaypointsByCargoId(Integer cargoId);
}
