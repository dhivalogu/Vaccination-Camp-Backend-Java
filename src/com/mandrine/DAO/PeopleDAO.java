//$Id$
package com.mandrine.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import org.postgresql.util.PSQLException;

import com.mandrine.db.DBResource;
import com.mandrine.model.People;
import com.mandrine.util.DataMapper;

public class PeopleDAO {
	public static void create(People person) throws ParseException, SQLException,PSQLException
	{

		DBResource.PEOPLE.create(person);
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
