//$Id$
package com.mandrine.model;

import java.sql.Date;

public class Booking extends Resource {

	private int slotID;
	private int bookingID;
	private String AADHAR;
	private Date date;
	private String session;
	private String status;
	private int campID;
	private String address;
	private int cityID;
	public String city;

	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}

	public int getBookingID() {
		return this.bookingID;
	}

	public void setAADHAR(String AADHAR) {
		this.AADHAR = AADHAR;
	}

	public String getAADHAR() {
		return this.AADHAR;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return this.date;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getSession() {
		return this.session;
	}

	public void setSlotID(int slotID) {
		this.slotID = slotID;
	}

	public int getSlotID() {
		return this.slotID;
	}

	public void setCampID(int campID) {
		this.campID = campID;
	}

	public int getCampID() {
		return this.campID;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}

	public void setCityID(int cityID) {
		this.cityID = cityID;
	}

	public int getCityID() {
		return this.cityID;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return this.city;
	}

}
