//$Id$
package com.mandrine.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.sql.Date;

import com.mandrine.model.City;
import com.mandrine.model.People;
import com.mandrine.util.DBConnectionUtil;
import com.mandrine.util.DataMapper;
import com.mandrine.util.DateFormatUtil;
import org.postgresql.util.PSQLException;
import com.mandrine.cache.CacheDB;
import com.mandrine.db.DBResource;

public class PeopleDAO {
	private static  Connection connection=null;
	public static void create(People person) throws ParseException, SQLException,PSQLException
	{
		PeopleDAO.connection= DBConnectionUtil.openConnection();
		DBResource.PEOPLE.create(person);
		DBConnectionUtil.closeConnection();
	}
	public static HashMap<String,People> fetchAll() throws SQLException
	{
		ResultSet rs=DBResource.PEOPLE.fetchAll(new People());
		HashMap<String,People> peopleData=new HashMap<String,People>();
		List<People> peopleList=DataMapper.Map(People.class, rs);
		for(People person:peopleList)
		{
			peopleData.put(person.getID(),person);
		}
		return peopleData;
	}
	public static void update(People person) throws SQLException
	{
		DBResource.PEOPLE.update(person);
	}

}
