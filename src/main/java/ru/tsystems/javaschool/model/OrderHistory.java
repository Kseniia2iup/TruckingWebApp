package ru.tsystems.javaschool.model;

import javax.persistence.*;

//@Entity
//@Table(name = "order_history")
public class OrderHistory {

    @Id
    @GeneratedValue()
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
}
