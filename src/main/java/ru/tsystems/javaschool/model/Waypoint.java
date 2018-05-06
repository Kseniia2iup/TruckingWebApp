package ru.tsystems.javaschool.model;

import javax.persistence.*;

//@Entity
//@Table(name = "waypoints")
public class Waypoint {

    @Id
    @GeneratedValue()
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
}
