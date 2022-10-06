
package com.mandrine.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.mandrine.db.Column;

public class Slot extends Resource {

	@Column(name="SLOT_ID")
	private int slotID;
	
	@Column(name="SESSION")
	private String session;
	
	@Column(name="CAPACITY")
	private int capacity;
	
	@Column(name="BOOKINGS")
	private int bookings;
	
	@Column(name="CAMP_ID")
	private int campID;
	
	@Column(name="DATE")
	private Date date;

	private String address;
	private List<Booking> bookingList=new ArrayList<Booking>();
	
	public void setSlotID(int slotID)
	{
		this.slotID=slotID;
	}
	public int getSlotID()
	{
		return this.slotID;
	}
	public void setSession(String session)
	{
		this.session=session;
	}
	public String getSession()
	{
		return this.session;
	}
	public int getCapacity()
	{
		return this.capacity;
	}
	public void setCapacity(int capacity)
	{
		this.capacity=capacity;
	}
	public Date getDate()
	{
		return this.date;
	}
	public void setDate(Date date)
	{
		this.date=date;
	}
	public void setCampID(int campID)
	{
		this.campID=campID;
	}
	public int getCampID()
	{
		return this.campID;
	}
	public int getBookings()
	{
		return this.bookings;
	}
	public void setBookings(int bookings)
	{
		this.bookings=bookings;
	}
	public void setAddress(String address)
	{
		this.address=address;
	}
	public String getAddress()
	{
		return this.address;
	}
	public List<Booking> getBookingList()
	{
		return this.bookingList;
	}
	public void addToBookingList(Booking e)
	{
		this.bookingList.add(e);
	}
}
