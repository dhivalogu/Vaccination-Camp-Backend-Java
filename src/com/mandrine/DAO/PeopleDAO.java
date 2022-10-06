//$Id$
package com.mandrine.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.sql.Date;
import com.mandrine.model.People;
import com.mandrine.util.DBConnectionUtil;
import com.mandrine.util.DateFormatUtil;
import org.postgresql.util.PSQLException;
import com.mandrine.cache.CacheDB;
import com.mandrine.db.DBResource;

public class PeopleDAO {
	private static  Connection connection=null;
	public static void addPerson(People person) throws ParseException, SQLException,PSQLException
	{
		PeopleDAO.connection= DBConnectionUtil.openConnection();
		PreparedStatement stmt=connection.prepareStatement("INSERT INTO \"PEOPLE\" VALUES(?,?,?,?,?,?);");
		stmt.setString(1,person.getID());
		stmt.setString(2,person.getFirstName());
		stmt.setString(3,person.getLastName());
		stmt.setDate(4,DateFormatUtil.toDate(person.getDOB()));
		stmt.setString(5,person.getMobile());
		stmt.setInt(6, person.getDosageCount());
		stmt.executeUpdate();
		DBConnectionUtil.closeConnection();
	}
	public static HashMap<String,People> getPeopleData() throws SQLException
	{
		ResultSet rs=DBResource.PEOPLE.fetchAll(new People());
		HashMap<String,People> peopleData=new HashMap<String,People>();
		while(rs.next())
		{
			People person=new People();
			String id=rs.getString("AADHAR");
			person.setID(id);
			person.setFirstName(rs.getString("FIRST_NAME"));
			person.setLastName(rs.getString("LAST_NAME"));
			person.setMobile(rs.getString("MOBILE"));
			person.setDOB(rs.getString("DOB"));
			person.setDosageCount(rs.getInt("DOSAGE COUNT"));
			peopleData.put(id,person);
			
		}
		return peopleData;
	}
	public static void updateDosageCount(People person) throws SQLException
	{
		connection=DBConnectionUtil.openConnection();
		PreparedStatement stmt=connection.prepareStatement("UPDATE \"PEOPLE\" SET \"DOSAGE COUNT\"=\"DOSAGE COUNT\"+1 WHERE \"AADHAR\"=?");
		stmt.setString(1,person.getID());
		person.setDosageCount(person.getDosageCount()+1);
		stmt.executeUpdate();
	}

}
