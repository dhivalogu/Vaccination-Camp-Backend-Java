
package com.mandrine.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.mandrine.cache.CacheDB;
import com.mandrine.db.DBResource;
import com.mandrine.model.Slot;
import com.mandrine.util.DBConnectionUtil;
import com.mandrine.util.DataMapper;

public class SlotDAO {
	public static void create(Slot slot) throws SQLException
	{
		  ResultSet keys=DBResource.SLOTS.create(slot);
		  if(keys.next())
		  {
			  slot.setSlotID((int) keys.getLong(1));
		  }
		  CacheDB.getSlotCache().put(slot.getSlotID(), slot);
		  DBConnectionUtil.closeConnection();
		
	}
	
	public static HashMap<Integer,Slot> fetchAll () throws SQLException
	{
		ResultSet rs=DBResource.SLOTS.fetchAll(new Slot());
		HashMap<Integer,Slot> slotData=new HashMap<Integer,Slot>();
		List<Slot> slotList=DataMapper.Map(Slot.class, rs);
		for(Slot slot:slotList)
		{
			slotData.put(slot.getSlotID(),slot);
			CacheDB.getCampCache().get(slot.getCampID()).addToSlotList(slot);
			
		}
		DBConnectionUtil.closeConnection();
		return slotData;
	}
	public static void update(Slot slot) throws SQLException
	{
		DBResource.SLOTS.update(slot);

		
	}
}
