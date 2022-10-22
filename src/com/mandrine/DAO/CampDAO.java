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
import com.mandrine.model.Camp;
import com.mandrine.service.AdminService;
import com.mandrine.util.DBConnectionUtil;

public class CampDAO {
	static Connection connection=null;
	public static HashMap<Integer,Camp> fetchAll() throws SQLException
	{
		connection=DBConnectionUtil.openConnection();
		ResultSet rs=DBResource.CAMPS.fetchAll(new Camp());
		HashMap<Integer,Camp> campData=new HashMap<Integer,Camp>();
		//List<Camp> campList=DataMapper.Map(Camp.class, rs);
		while(rs.next())
		{
			Camp camp=new Camp();
			int cityID=rs.getInt("CITY_ID");
			int campID=rs.getInt("CAMP_ID");
			camp.setCampID(campID);
			camp.setAddress(rs.getString("ADDRESS"));
			camp.setCityID(cityID);
			String date=rs.getString("DATE");
			camp.setBeginDate(date.substring(1,11));
			camp.setEndDate(date.substring(12,22));
			camp.calcAvailDate();
			CacheDB.getCityCache().get(cityID).setCamp(camp);
			CacheDB.getCityCache().get(cityID).addToCampList(camp);
			campData.put(campID,camp);
		}
		DBConnectionUtil.closeConnection();
		return campData;
	}
	
	public static void create(Camp camp) throws SQLException
	{

		  CampDAO.connection= DBConnectionUtil.openConnection();
		  PreparedStatement stmt=connection.prepareStatement("INSERT INTO \"CAMP\" (\"CITY_ID\",\"ADDRESS\",\"DATE\") VALUES(?,?,?::daterange);",Statement.RETURN_GENERATED_KEYS);
		  stmt.setInt(1, camp.getCityID());
		  stmt.setString(2,camp.getAddress());
		  stmt.setObject(3,camp.getDate());
		  stmt.executeUpdate();
		  ResultSet keys=stmt.getGeneratedKeys();
		  if(keys.next())
		  {
			  camp.setCampID((int) keys.getLong(1));
		  }
		  CacheDB.getCampCache().put(camp.getCampID(),camp);
		  CacheDB.getCityCache().get(camp.getCityID()).setCamp(camp);
		  AdminService.createSlots(camp);
	}

}
