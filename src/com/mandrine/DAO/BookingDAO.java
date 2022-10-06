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
	public static int doBooking(Booking bookingData) throws SQLException
	{
		connection=DBConnectionUtil.openConnection();
		PreparedStatement stmt=connection.prepareStatement("INSERT INTO \"BOOKING_DATA\"(\"AADHAR\",\"SLOT_ID\",\"STATUS\") VALUES(?,?,?);",Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1,bookingData.getAADHAR());
		stmt.setInt(2,bookingData.getSlotID());
		stmt.setString(3,"UPCOMING");
		stmt.executeUpdate();
		ResultSet keys=stmt.getGeneratedKeys();
		keys.next();
		int bookingID=(int) keys.getLong(1);
		CacheDB.getBookingCache().put(bookingID,bookingData);
		CacheDB.getPeopleCache().get(bookingData.getAADHAR()).addToBookingList(bookingData);
		CacheDB.getSlotCache().get(bookingData.getSlotID()).addToBookingList(bookingData);
		DBConnectionUtil.closeConnection();
		return bookingID;
	}
	public static HashMap<Integer,Booking> getBookingData() throws SQLException
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
	public static void approveVaccination(Booking bookingData) throws SQLException
	{
		connection=DBConnectionUtil.openConnection();
		PreparedStatement stmt=connection.prepareStatement("UPDATE \"BOOKING_DATA\" SET \"STATUS\"='VACCINATED' WHERE \"BOOKING_ID\"=?");
		stmt.setInt(1,bookingData.getBookingID());
		stmt.executeUpdate();
	}

}
