//$Id$
package com.mandrine.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.mandrine.cache.CacheDB;
import com.mandrine.db.DBResource;
import com.mandrine.model.Booking;
import com.mandrine.service.BookingService;
import com.mandrine.util.DBConnectionUtil;

public class BookingDAO {
	private static Connection connection=null;
	public static int create(Booking bookingData) throws SQLException
	{
		ResultSet keys=DBResource.BOOKINGS.create(bookingData);
		keys.next();
		int bookingID=(int) keys.getLong(1);
		CacheDB.getBookingCache().put(bookingID,bookingData);
		CacheDB.getPeopleCache().get(bookingData.getAADHAR()).addToBookingList(bookingData);
		CacheDB.getSlotCache().get(bookingData.getSlotID()).addToBookingList(bookingData);
		return bookingID;
	}
	public static HashMap<Integer,Booking> fetchAll() throws SQLException
	{
		ResultSet rs=DBResource.BOOKINGS.fetchAll(new Booking());
		HashMap<Integer,Booking> bookingData=new HashMap<Integer,Booking>();
		while(rs.next())
		{
			Booking bookingDetails=new Booking();
			int bookingID=rs.getInt("BOOKING_ID");
			bookingDetails.setAADHAR(rs.getString("AADHAR"));
			bookingDetails.setBookingID(bookingID);
			bookingDetails.setSlotID(rs.getInt("SLOT_ID"));
			bookingDetails.setStatus(rs.getString("STATUS"));
			BookingService.populateBookingDetails(bookingDetails);
			CacheDB.getPeopleCache().get(bookingDetails.getAADHAR()).addToBookingList(bookingDetails);
			CacheDB.getSlotCache().get(bookingDetails.getSlotID()).addToBookingList(bookingDetails);
			bookingData.put(bookingID,bookingDetails);
		}
		return bookingData;
	}
	public static void update(Booking bookingData) throws SQLException
	{
		DBResource.BOOKINGS.update(bookingData);
//		connection=DBConnectionUtil.openConnection();
//		PreparedStatement stmt=connection.prepareStatement("UPDATE \"BOOKING_DATA\" SET \"STATUS\"='VACCINATED' WHERE \"BOOKING_ID\"=?");
//		stmt.setInt(1,bookingData.getBookingID());
//		stmt.executeUpdate();
	}

}
