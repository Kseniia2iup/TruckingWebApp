package ru.tsystems.javaschool.model;

import ru.tsystems.javaschool.model.enums.DriverStatus;

import javax.persistence.*;

//@Entity
//@Table(name = "drivers")
public class Driver {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "driver_name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "worked_this_month", nullable = false)
    private Integer workedThisMonth;

    @Column(name = "phase_of_work", nullable = false)
    @Enumerated(EnumType.STRING)
    private DriverStatus status;

    @Column(name = "current_city", nullable = false)
    private String city;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reg_number")
    private Truck currentTruck;

    public Driver() {
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getWorkedThisMonth() {
        return workedThisMonth;
    }

    public void setWorkedThisMonth(Integer workedThisMonth) {
        this.workedThisMonth = workedThisMonth;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
