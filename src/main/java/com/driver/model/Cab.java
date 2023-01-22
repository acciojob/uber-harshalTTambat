package com.driver.model;

import javax.persistence.*;

@Entity
public class Cab
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int perKmRate;

    private Boolean available;

    @OneToOne
    @JoinColumn
    private Driver driver;
}