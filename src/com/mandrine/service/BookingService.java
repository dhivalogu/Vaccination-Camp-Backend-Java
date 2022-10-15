//$Id$
package com.mandrine.service;

import com.mandrine.DAO.BookingDAO;
import com.mandrine.DAO.SlotDAO;
import com.mandrine.cache.CacheDB;
import com.mandrine.exception.SlotOverflowException;
import com.mandrine.model.Booking;
import com.mandrine.model.Camp;
import com.mandrine.model.City;
import com.mandrine.model.Slot;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import com.google.gson.Gson;

public class BookingService {
	
	
	public static HashMap<Integer, City> getCityData() throws SQLException, NoSuchFieldException, SecurityException
	{
		return CacheDB.getCityCache();
		
	}
	public static City getCityById(int id) throws SQLException
	{
		return CacheDB.getCityCache().get(id);
	}
	public static Camp getCampFromCity(int cityID) throws JSONException, SQLException 
	{
		
		HashMap<Integer,Camp> campData=CacheDB.getCampCache();
		Camp camp=campData.get(cityID);
		return camp;
	}
	public static List<Slot> getSlotFromCamp(int campID) throws SQLException
	{
		if(CacheDB.getCampCache().get(campID)==null)
		{
			throw new NullPointerException("Given Camp Doesn't Exist");
		}
		List<Slot> slotList=CacheDB.getCampCache().get(campID).getSlotList();
		return slotList;
	}
	
	public static Collection<City> getOverallData() throws SQLException, NoSuchFieldException, SecurityException
	{
		CacheDB.loadOverallData();
		return CacheDB.getCityCache().values();
	}
	public static Booking addBooking(Booking bookingData) throws SQLException, SlotOverflowException
	{
		Slot slot=CacheDB.getSlotCache().get(bookingData.getSlotID());
		if(slot.getBookings()>=10)
		{
			throw new SlotOverflowException("Slot Got Filled! Choose Other Slots");
		}
		bookingData.setStatus("UPCOMING");
		int bookingID=BookingDAO.create(bookingData);
		bookingData.setBookingID(bookingID);
		Slot slotUpdateModel=new Slot();
		slotUpdateModel.setSlotID(slot.getSlotID());
		slotUpdateModel.setBookings(slot.getBookings()+1);
		slot.setBookings(slot.getBookings()+1);
		SlotDAO.update(slotUpdateModel);
		return populateBookingDetails(bookingData);

	}
	
	public static Booking populateBookingDetails(Booking bookingData) throws SQLException,SecurityException
	{
		Slot slot=CacheDB.getSlotCache().get(bookingData.getSlotID());
		Camp camp=CacheDB.getCampCache().get(slot.getCampID());
		City city=CacheDB.getCityCache().get(camp.getCityID());
		bookingData.setAddress(camp.getAddress());
		bookingData.setCityID(camp.getCityID());
		bookingData.setCity(city.getName());
		bookingData.setSession(slot.getSession());
		bookingData.setDate(slot.getDate());
		bookingData.setCampID(camp.getCampID());
		return bookingData;
	}
	public static HashMap<Integer, Booking> getBookingDetails() throws SQLException
	{
		return CacheDB.getBookingCache();
	}
	
	public static Booking getBookingByID(int bookingID) throws SQLException
	{
		return CacheDB.getBookingCache().get(bookingID);
	}
	public static List<Booking> getBookingByAadhar(String AADHAR) throws SQLException
	{
		List<Booking> bookingData=new ArrayList<Booking>();
		for(Booking bookingDetails:CacheDB.getBookingCache().values())
		{
			
			if(bookingDetails.getAADHAR().equals(AADHAR))
			{
				
				bookingData.add(bookingDetails);
			}
		}
		return bookingData;
	}
	public static List<Booking> getBookingByCamp(int campID) throws SQLException
	{
		List<Booking> bookingData=new ArrayList<Booking>();
		for(Booking bookingDetails:CacheDB.getBookingCache().values())
		{
			if(bookingDetails.getCampID()==campID)
			{
				bookingData.add(bookingDetails);
			}
		}
		return bookingData;
	}
	
	
	


}
