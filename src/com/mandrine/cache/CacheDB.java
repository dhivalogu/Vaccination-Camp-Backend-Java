//$Id$
package com.mandrine.cache;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.mandrine.DAO.BookingDAO;
import com.mandrine.DAO.CampDAO;
import com.mandrine.DAO.CityDAO;
import com.mandrine.DAO.PeopleDAO;
import com.mandrine.DAO.SlotDAO;
import com.mandrine.model.Booking;
import com.mandrine.model.Camp;
import com.mandrine.model.City;
import com.mandrine.model.People;
import com.mandrine.model.Slot;

public  class CacheDB {
	
     static HashMap<String,People>  peopleCache=null;
     static HashMap<Integer, City>    cityCache=null; 
     static HashMap<Integer, Camp>   campCache=null;
     static HashMap<Integer, Slot>   slotCache=null;
     static HashMap<Integer,Booking> bookingCache=null;
     static Collection<City> overallData=null;
     
     /**
      * To load data from the People table in database to the cache  
      * @throws SQLException
      */
     
     public static void loadPeopleCache() throws SQLException
     {
    	 peopleCache=PeopleDAO.getPeopleData();
     }
     
     /**
      * To load data from the City table in database to the cache  
      * @throws SQLException
     * @throws SecurityException 
     * @throws NoSuchFieldException 
      */
     
     public static void loadCityCache() throws SQLException,SecurityException
     {
    	 cityCache=CityDAO.getCityData();
     }
     
     public static void loadCampCache() throws SQLException
     {
    	 campCache = CampDAO.getCampData();
     }
     
     public static void loadSlotCache() throws SQLException 
     {
    	 slotCache=SlotDAO.getSlotData();
     }
     public static void loadBookingCache() throws SQLException
     {
    	 bookingCache=BookingDAO.getBookingData();
     }
     public static HashMap<String,People> getPeopleCache() throws SQLException
     {
    	 
    	 if(peopleCache==null)
    	 {
    		 loadPeopleCache();
    	 }
    	 return peopleCache;
     }
     
     public static HashMap<Integer, City> getCityCache() throws SQLException,SecurityException
     {
    	 if(cityCache==null)
    	 {
    		 loadCityCache();
    	 }
    	 return cityCache;
     }
     
     public static HashMap<Integer, Camp> getCampCache() throws SQLException
     {
    	 if(campCache==null)
    	 {
    		 loadCampCache();
    	 }
    	 return campCache;
     }
     public static HashMap<Integer, Slot> getSlotCache() throws SQLException
     {
    	 if(slotCache==null)
    	 {
    		 loadSlotCache();
    	 }
    	 return slotCache;
     }
     public static HashMap<Integer, Booking> getBookingCache() throws SQLException
     {
    	 if(bookingCache==null)
    	 {
    		 loadBookingCache();
    	 }
    	 return bookingCache;
     }
     
     public static void loadOverallData() throws SQLException
     {
    	 loadCityCache();
    	 loadCampCache();
    	 loadSlotCache();
    	 loadPeopleCache();
    	 loadBookingCache();
    	 
     }
     public static Boolean cacheExists()
     {
    	 if(cityCache==null)
    	 {
    		 return false;
    	 }
    	 return true;
     }
     
}

