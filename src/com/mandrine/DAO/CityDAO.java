//$Id$
package com.mandrine.DAO;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
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
import com.mandrine.model.City;
import com.mandrine.util.DBConnectionUtil;
import com.mandrine.util.DataMapper;
public class CityDAO {
	static Connection connection=null;
	
	public static HashMap<Integer,City> getCityData() throws SQLException,  SecurityException
	{
		HashMap<Integer,City> cityData=new HashMap<Integer,City>();
		DBResource cityResource=DBResource.CITIES;
		cityResource.setClazz(City.class);
		ResultSet rs= DBResource.CITIES.fetchAll(new City());
		List<City> cityList=DataMapper.Map(City.class, rs);
		for(City city:cityList)
		{
			cityData.put(city.getCityID(), city);
		}
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
