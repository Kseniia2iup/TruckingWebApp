package ru.tsystems.javaschool.model;

import ru.tsystems.javaschool.model.enums.TruckStatus;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "trucks")
public class Truck {

    @Id
    @GeneratedValue()
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "reg_number", length = 7, unique = true, nullable = false)
    private String reg_number;

    @Column(name = "shift_period", nullable = false)
    private Integer shift_period;

    @Column(name = "capacity_ton", nullable = false)
    private Integer capacity_ton;

    @Column(name = "truck_condition", nullable = false)
    @Enumerated(EnumType.STRING)
    private TruckStatus condition;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    private City city;


    public Truck() {
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegNumber() {
        return reg_number;
    }

    public void setRegNumber(String reg_number) {
        this.reg_number = reg_number;
    }

    public Integer getShiftPeriod() {
        return shift_period;
    }

    public void setShiftPeriod(Integer shift_period) {
        this.shift_period = shift_period;
    }

    public Integer getCapacityTon() {
        return capacity_ton;
    }

    public void setCapacityTon(Integer capacity_ton) {
        this.capacity_ton = capacity_ton;
    }

    public TruckStatus getCondition() {
        return condition;
    }

    public void setCondition(TruckStatus condition) {
        this.condition = condition;
    }

}
