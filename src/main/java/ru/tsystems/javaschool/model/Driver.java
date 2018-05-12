package ru.tsystems.javaschool.model;

import ru.tsystems.javaschool.model.enums.DriverStatus;

import javax.persistence.*;

@Entity
@Table(name = "drivers")
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


    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.REMOVE})
    @JoinColumn(name="id", nullable=false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "truck_id")
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Truck getCurrentTruck() {
        return currentTruck;
    }

    public void setCurrentTruck(Truck currentTruck) {
        this.currentTruck = currentTruck;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
