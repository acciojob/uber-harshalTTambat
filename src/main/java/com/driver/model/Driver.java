package com.driver.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "drivers")
public class Driver
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer driverId;

    private String mobile;

    private String password;

    @OneToOne(mappedBy = "driver",cascade = CascadeType.ALL)
    private Cab cab;

    @OneToMany(mappedBy = "driver",cascade = CascadeType.ALL)
    private List<TripBooking> driverTripBookingList;

    public Driver() {
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Cab getCab() {
        return cab;
    }

    public void setCab(Cab cab) {
        this.cab = cab;
    }

    public List<TripBooking> getDriverTripBookingList() {
        return driverTripBookingList;
    }

    public void setDriverTripBookingList(List<TripBooking> driverTripBookingList) {
        this.driverTripBookingList = driverTripBookingList;
    }
}