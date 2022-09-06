//$Id$
package com.mandrine.service;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import com.mandrine.DAO.BookingDAO;
import com.mandrine.DAO.CampDAO;
import com.mandrine.DAO.CityDAO;
import com.mandrine.DAO.PeopleDAO;
import com.mandrine.DAO.SlotDAO;
import com.mandrine.cache.CacheDB;
import com.mandrine.exception.ExistingDataException;
import com.mandrine.model.Booking;
import com.mandrine.model.Camp;
import com.mandrine.model.City;
import com.mandrine.model.People;
import com.mandrine.model.Slot;

public class AdminService {
	static String[] slotSession= {"Morning(7 AM to 11 AM)","Noon(12 PM to 3PM)","Evening(4 PM to 6PM)"};

	public static void addCamp(Camp camp) throws ExistingDataException, SQLException
	{
		if(CacheDB.getCityCache().get(camp.getCityID()).getCamp()!=null)
		{
			throw new ExistingDataException("Only one camp allowed per city");
		}
		CampDAO.addCamp(camp);
		CacheDB.loadOverallData();
	}
	public static void createSlots(Camp camp) throws SQLException
	{
		LocalDate start=camp.getBeginDate().toLocalDate();
		LocalDate end=camp.getEndDate().toLocalDate();
		
		while(!start.isAfter(end))
		{
			for(String session:slotSession)
			{
				Slot slot=new Slot();
				slot.setCampID(camp.getCampID());
				slot.setCapacity(10);
				slot.setBookings(0);
				slot.setDate(Date.valueOf(start));
				slot.setSession(session);
				SlotDAO.addSlot(slot);
				
			}
			start=start.plusDays(1);
		}
	
	}
	public static void vaccinated(Booking bookingDetails) throws SQLException
	{
		
		BookingDAO.approveVaccination(bookingDetails);
		CacheDB.getBookingCache().get(bookingDetails.getBookingID()).setStatus("VACCINATED");
		People person=CacheDB.getPeopleCache().get(bookingDetails.getAADHAR());
		person.setDosageCount(person.getDosageCount()+1);
		PeopleDAO.updateDosageCount(person);
		City city=CacheDB.getCityCache().get(bookingDetails.getCityID());
		city.setVaccinatedCount(city.getVaccinatedCount()+1);
		city.setStock(city.getStock()-1);
		CityDAO.updateVaccinatedCount(city);
		
	}
	
	//To add a new city
	
	public static void addCity(City city) throws SQLException
	{
		CityDAO.addCity(city);
		CacheDB.getCityCache().put(city.getCityID(), city);
	}
}
