package ru.tsystems.javaschool.model;

import ru.tsystems.javaschool.model.enums.CargoStatus;

import javax.persistence.*;

@Entity
@Table(name = "cargoes")
public class Cargo {

    @Id
    @GeneratedValue()
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "cargo_name", nullable = false)
    private String name;

    @Column(name = "weight_kg")
    private Integer weight;

    @Column(name = "delivery_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CargoStatus delivery_status;

    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(mappedBy = "cargo")
    private Waypoint waypoint;

    public Cargo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public CargoStatus getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(CargoStatus delivery_status) {
        this.delivery_status = delivery_status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Waypoint getWaypoint() {
        return waypoint;
    }

    public void setWaypoint(Waypoint waypoint) {
        this.waypoint = waypoint;
    }
}
