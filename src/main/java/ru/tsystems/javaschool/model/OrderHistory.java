package ru.tsystems.javaschool.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "order_history")
public class OrderHistory {

    @Id
    @GeneratedValue()
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "shift_begined")
    private Date shiftBegined;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "shift_ended")
    private Date shiftEnded;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    public OrderHistory() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getShiftBegined() {
        return shiftBegined;
    }

    public void setShiftBegined(Date shiftBegined) {
        this.shiftBegined = shiftBegined;
    }

    public Date getShiftEnded() {
        return shiftEnded;
    }

    public void setShiftEnded(Date shiftEnded) {
        this.shiftEnded = shiftEnded;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
