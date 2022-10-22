//$Id$
package com.mandrine.DAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.mandrine.db.DBResource;
import com.mandrine.model.City;
import com.mandrine.util.DataMapper;
public class CityDAO {
	static Connection connection=null;
	
	public static HashMap<Integer,City> fetchAll() throws SQLException,  SecurityException
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
	public static void create(City city) throws SQLException
	{
		ResultSet keys=DBResource.CITIES.create(city);
		if(keys.next())
		{
			city.setCityID((int) keys.getLong(1));
		}
		city.setVaccinatedCount(0);
		
	}
	public static void update(City city) throws SQLException 
	{
		DBResource.CITIES.update(city);	
	}

}
