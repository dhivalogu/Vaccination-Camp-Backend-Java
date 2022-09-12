//$Id$
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
import com.mandrine.model.City;
import com.mandrine.util.DBConnectionUtil;
public class CityDAO {
	static Connection connection=null;
	
	public static HashMap<Integer,City> getCityData() throws SQLException
	{
		HashMap<Integer,City> cityData=new HashMap<Integer,City>();
		connection=DBConnectionUtil.openConnection();
		PreparedStatement stmt=connection.prepareStatement("SELECT * FROM \"CITY\";");
		ResultSet rs=stmt.executeQuery();
		while(rs.next())
		{
			City city=new City();
			int id=rs.getInt("CITY_ID");
			city.setCityID(id);
			city.setName(rs.getString("NAME"));
			city.setStock(rs.getInt("STOCK"));
			city.setVaccinatedCount(rs.getInt("VACCINATED_COUNT"));
			cityData.put(id, city);
			
		}
		DBConnectionUtil.closeConnection();
		return cityData;
		
	}
	public static void updateVaccinatedCount(City city) throws SQLException
	{
		connection=DBConnectionUtil.openConnection();
		PreparedStatement stmt=connection.prepareStatement("UPDATE \"CITY\" SET \"VACCINATED_COUNT\"=\"VACCINATED_COUNT\"+1,\"STOCK\" =\"STOCK\"-1 WHERE \"CITY_ID\"=?;");
		stmt.setInt(1,city.getCityID());
		stmt.executeUpdate();
	
	}
	public static void addCity(City city) throws SQLException
	{
		connection=DBConnectionUtil.openConnection();
		PreparedStatement stmt=connection.prepareStatement("INSERT INTO \"CITY\" (\"NAME\",\"STOCK\",\"VACCINATED_COUNT\") VALUES(?,?,0);",Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1,city.getName());
		stmt.setInt(2,city.getStock());
		stmt.executeUpdate();
		ResultSet keys=stmt.getGeneratedKeys();
		if(keys.next())
		{
			city.setCityID((int) keys.getLong(1));
		}
		city.setVaccinatedCount(0);
		DBConnectionUtil.closeConnection();
		
	}
	public static void updateStock(City city, int stock) throws SQLException 
	{
		connection=DBConnectionUtil.openConnection();
		PreparedStatement stmt = connection.prepareStatement("UPDATE \"CITY\" SET \"STOCK\"=? WHERE \"CITY_ID\"=?;");
		stmt.setInt(1,stock);
		stmt.setInt(2,city.getCityID());
		stmt.executeUpdate();
		DBConnectionUtil.closeConnection();
		
	}

}
