package ru.tsystems.javaschool.model;

import ru.tsystems.javaschool.model.enums.OrderStatus;

import javax.persistence.*;

//@Entity
//@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue()
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;


    @Column(name = "order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToOne(mappedBy = "truck_number")
    private Truck truck;


}
