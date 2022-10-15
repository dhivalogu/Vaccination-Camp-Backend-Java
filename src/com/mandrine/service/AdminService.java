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
import com.mandrine.exception.ResourceNotFoundException;
import com.mandrine.model.Booking;
import com.mandrine.model.Camp;
import com.mandrine.model.City;
import com.mandrine.model.People;
import com.mandrine.model.Slot;
import com.mandrine.util.DateFormatUtil;

public class AdminService {
	static String[] slotSession= {"Morning(7 AM to 11 AM)","Noon(12 PM to 3PM)","Evening(4 PM to 6PM)"};

	public static void addCamp(Camp camp) throws ExistingDataException, SQLException
	{
//		if(CacheDB.getCityCache().get(camp.getCityID()).getCamp()!=null)
//		{
//			throw new ExistingDataException("Only one camp allowed per city");
//		}
		camp.setDate(DateFormatUtil.convertToDateRange(camp.getBeginDate(), camp.getEndDate()));
		CampDAO.create(camp);
		CacheDB.loadOverallData();
	}
	public static void createSlots(Camp camp) throws SQLException
	{
		LocalDate start=camp.getBeginDate().toLocalDate();
		LocalDate end=camp.getEndDate().toLocalDate();
		
		do
		{
			for(String session:slotSession)
			{
				Slot slot=new Slot();
				slot.setCampID(camp.getCampID());
				slot.setCapacity(10);
				slot.setBookings(0);
				slot.setDate(Date.valueOf(start));
				slot.setSession(session);
				SlotDAO.create(slot);
				
			}
			start=start.plusDays(1);
		}while(!start.isAfter(end));
	
	}
	public static void vaccinated(Booking bookingDetails) throws SQLException
	{
		Booking bookingUpdateModel =new Booking();
		bookingUpdateModel.setBookingID(bookingDetails.getBookingID());
		bookingUpdateModel.setStatus("VACCINATED");
		BookingDAO.update(bookingUpdateModel);
		CacheDB.getBookingCache().get(bookingDetails.getBookingID()).setStatus("VACCINATED");
		People person=CacheDB.getPeopleCache().get(bookingDetails.getAADHAR());
		People personModel =new People();
		personModel.setID(person.getID());
		personModel.setDosageCount(person.getDosageCount()+1);
		PeopleDAO.update(personModel);
		person.setDosageCount(person.getDosageCount()+1);
		City city=CacheDB.getCityCache().get(bookingDetails.getCityID());
		City cityModel=new City();
		cityModel.setCityID(city.getCityID());
		cityModel.setVaccinatedCount(city.getVaccinatedCount()+1);
		cityModel.setStock(city.getStock()-1);
		CityDAO.update(cityModel);
		city.setVaccinatedCount(city.getVaccinatedCount()+1);
		city.setStock(city.getStock()-1);
		
	}
	
	//To add a new city
	
	public static void addCity(City city) throws SQLException
	{
		CityDAO.create(city);
		CacheDB.getCityCache().put(city.getCityID(), city);
	}
	public static void updateStock(int cityID,int stock) throws SQLException, ResourceNotFoundException
	{
		City city=CacheDB.getCityCache().get(cityID);
		if(city==null) throw new ResourceNotFoundException("Given city doesn't exist in databse");
		City cityModel=new City();
		cityModel.setCityID(city.getCityID());
		cityModel.setStock(stock);
		CityDAO.update(cityModel);
		city.setStock(stock);
	}
}
