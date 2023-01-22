package com.driver.model;

import javax.persistence.*;


@Entity
@Table(name = "cabs")
public class Cab
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private int perKmRate;

    private Boolean available;

    @OneToOne
    @JoinColumn
    private Driver driver;

    public Cab() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPerKmRate() {
        return perKmRate;
    }

    public void setPerKmRate(int perKmRate) {
        this.perKmRate = perKmRate;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}