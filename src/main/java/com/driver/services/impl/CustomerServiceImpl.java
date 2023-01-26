package com.driver.services.impl;

import com.driver.model.*;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {

		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		List<Customer> customerList = customerRepository2.findAll();
		for (Customer customer: customerList)
		{
			int checkId = customer.getCustomerId();
			if(checkId == customerId)
				customerRepository2.delete(customer);

			break;
		}

	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE).
		// If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		TripBooking tripBooking = new TripBooking();

		List<Driver> driverList = driverRepository2.findAll();

		Driver driverForTrip = null;

		for(Driver driver:driverList)
		{
			if (driver.getCab().getAvailable() == Boolean.TRUE)
			{
				if ((driverForTrip == null)||(driverForTrip.getDriverId() > driver.getDriverId()))
				{
					driverForTrip = driver;
					break;
				}
			}
		}
		if (driverForTrip == null) throw new Exception("No cab available!");

		Customer customer = customerRepository2.findById(customerId).get();
		Cab cab = driverForTrip.getCab();
		cab.setAvailable(Boolean.FALSE);

		int bill = distanceInKm * cab.getPerKmRate();

		tripBooking.setDriver(driverForTrip);
		tripBooking.setCustomer(customer);
		tripBooking.setFromLocation(fromLocation);
		tripBooking.setToLocation(toLocation);
		tripBooking.setDistanceInKm(distanceInKm);
		tripBooking.setBill(bill);
		tripBooking.setStatus(TripStatus.CONFIRMED);

		// Bidirectional mapping
		customer.getTripBookingList().add(tripBooking);
		customerRepository2.save(customer);

		driverForTrip.getDriverTripBookingList().add(tripBooking);
		driverRepository2.save(driverForTrip);

		return tripBooking;
	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
		tripBooking.setStatus(TripStatus.CANCELED);
		tripBooking.setBill(0);
		tripBooking.getDriver().getCab().setAvailable(Boolean.TRUE);
		tripBookingRepository2.save(tripBooking);
	}
	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();

		int bill = tripBooking.getDriver().getCab().getPerKmRate() * tripBooking.getDistanceInKm();
		tripBooking.setBill(bill);

		tripBooking.getDriver().getCab().setAvailable(Boolean.TRUE);
		tripBooking.setStatus(TripStatus.COMPLETED);

		tripBookingRepository2.save(tripBooking);
	}
}
