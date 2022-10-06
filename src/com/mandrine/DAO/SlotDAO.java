
package com.mandrine.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mandrine.cache.CacheDB;
import com.mandrine.db.DBResource;
import com.mandrine.model.Camp;
import com.mandrine.model.Slot;
import com.mandrine.util.DBConnectionUtil;

public class SlotDAO {
	private static Connection connection=null;
	public static void addSlot(Slot slot) throws SQLException
	{
		  SlotDAO.connection= DBConnectionUtil.openConnection();
		  PreparedStatement stmt=connection.prepareStatement("INSERT INTO \"SLOTS\"(\"DATE\",\"SESSION\",\"CAPACITY\",\"BOOKINGS\",\"CAMP_ID\") VALUES (?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
		  DBResource.SLOTS.getConditionMap(slot);
		  DBResource.SLOTS.create(slot);
		  stmt.setDate(1,slot.getDate());
		  stmt.setString(2,slot.getSession());
		  stmt.setInt(3, slot.getCapacity());
		  stmt.setInt(4, slot.getBookings());
		  stmt.setInt(5, slot.getCampID());
		  stmt.executeUpdate();
		  ResultSet keys=stmt.getGeneratedKeys();
		  if(keys.next())
		  {
			  slot.setSlotID((int) keys.getLong(1));
		  }
		  CacheDB.getSlotCache().put(slot.getSlotID(), slot);
		  DBConnectionUtil.closeConnection();
		
	}
	
	public static HashMap<Integer,Slot> getSlotData () throws SQLException
	{
		ResultSet rs=DBResource.SLOTS.fetchAll(new Slot());
		HashMap<Integer,Slot> slotData=new HashMap<Integer,Slot>();
		while(rs.next())
		{
			Slot slot=new Slot();
			int campID=rs.getInt("CAMP_ID");
			slot.setCampID(campID);
			slot.setBookings(rs.getInt("BOOKINGS"));
			slot.setCapacity(rs.getInt("CAPACITY"));
			slot.setDate(rs.getDate("DATE"));
			slot.setSession(rs.getString("SESSION"));
			int slotID=rs.getInt("SLOT_ID");
			slot.setSlotID(slotID);
			slotData.put(slotID,slot);
			CacheDB.getCampCache().get(slot.getCampID()).addToSlotList(slot);
			
		}
		DBConnectionUtil.closeConnection();
		return slotData;
	}
	public static void addBookings(Slot slot) throws SQLException
	{
		connection=DBConnectionUtil.openConnection();
		PreparedStatement stmt= connection.prepareStatement("UPDATE \"SLOTS\" SET \"BOOKINGS\"=\"BOOKINGS\"+1 WHERE \"SLOT_ID\"=?;");
		stmt.setInt(1, slot.getSlotID());
		stmt.executeUpdate();
		
	}
}
