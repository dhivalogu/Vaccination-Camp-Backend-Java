//$Id$
package com.mandrine.db;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mandrine.model.Booking;
import com.mandrine.model.People;
import com.mandrine.model.Resource;
import com.mandrine.util.DBConnectionUtil;

public enum DBResource {
	
	PEOPLE("PEOPLE",new String[]{"AADHAR","FIRST_NAME","LAST_NAME","DOB","MOBILE","DOSAGE_COUNT"}),
	BOOKINGS("BOOKING_DATA",new String[] {"BOOKING_ID","AADHAR","SLOT_ID","STATUS"}),
	CITIES("CITY",new String[] {"CITY_ID","NAME","STOCK","VACCINATED_COUNT"}),
	CAMPS("CAMP",new String[] {"CAMP_ID","CITY_ID","ADDRESS","DATE"}),
	SLOTS("SLOTS",new String[] {"SLOT_ID","DATE","SESSION","CAPACITY","BOOKINGS","CAMP_ID"}),
	CREDENTIALS("CREDENTIALS",new String[] {"USERNAME","PASSWORD","ACCESS_LEVEL"});
	
	DBResource(String tableName, String[] fields) {
		this.tableName = tableName;
		this.fields=fields;
	}
	private final  String tableName;
	private final String[] fields;
	private Class clazz=null;
	/**
	 * @return the tableName
	 */
	
	public String getTableName() {
		return tableName;
	}
	/**
	 * @return the fields
	 */
	public String[] getFields() {
		return fields;
	}
	public void setClazz(Class clazz)
	{
		this.clazz=clazz;
	}
	
	public ResultSet fetchAll(Resource resource) throws SQLException
	{
		Connection connection=DBConnectionUtil.openConnection();
		String query="SELECT * FROM $tableName";
		query=query.replace("$tableName","\""+tableName+"\"");
		HashMap<String,Object> conditionMap=getConditionMap(resource);
		if(!conditionMap.isEmpty())
		{
			query=query+" WHERE";
			for(Map.Entry<String, Object> m:conditionMap.entrySet())
			{
				query=query+" \""+m.getKey()+"\""+" = ? AND";
			}
			query=query.substring(0, query.length()-3);
		}
		PreparedStatement stmt=connection.prepareStatement(query);
		int index=1;
		for(Map.Entry<String, Object> m:conditionMap.entrySet())
		{
			if(m.getValue() instanceof Integer)
			{
				stmt.setInt(index, (Integer) m.getValue());
			}
			if(m.getValue() instanceof String)
			{
				stmt.setString(index, (String) m.getValue());
			}
			index++;
		}
		ResultSet rs=stmt.executeQuery();
		connection.close();
		return rs;
	}
	public void create(Resource resource) throws SQLException
	{
		Connection connection=DBConnectionUtil.openConnection();
		String query="INSERT INTO \""+tableName+"\"($fields) VALUES($values)";
		HashMap<String,Object> fieldMap=getConditionMap(resource);
		Set<String> fieldArray = fieldMap.keySet();
		String fields=String.join(",",fieldArray.toArray(new String[fieldArray.size()]));
		query=query.replace("$fields", fields);
		String values=String.join(",", Collections.nCopies(fieldArray.size(),"?"));
		query=query.replace("$values", values);
		System.out.println("");
		PreparedStatement stmt=connection.prepareStatement(query);
		int index=1;
		for(Object obj:fieldMap.values())
		{
			if(obj instanceof Integer)
			{
				stmt.setInt(index,(Integer) obj);
			}
			if(obj instanceof String)
			{
				stmt.setString(index,(String) obj);
			}
			if(obj instanceof Date)
			{
				stmt.setDate(index, (Date)obj);
			}
			index++;
		}
		System.out.println("");
//		for(Map.Entry<String, Object> fm:fieldMap.entrySet())
//		{
//			fields=query+fm.getKey()+",";
//		}
		
		
	}
	public HashMap<String, Object> getConditionMap(Resource resource)
	{
		Field[] fields=resource.getClass().getDeclaredFields();
		HashMap<String,Object> conditionMap=new HashMap<String,Object>();
		for(Field field:fields)
		{
			try {
				field.setAccessible(true);
				Object o=field.get(resource);
				if(o!=null && field.getAnnotation(Column.class)!=null)
				{
					if(o instanceof Integer)
					{
						if((Integer)o !=0)
						{
							conditionMap.put(field.getAnnotation(Column.class).name(), o);
						}
					}
					else
					{
						String name=field.getAnnotation(Column.class).name();
						conditionMap.put(field.getAnnotation(Column.class).name(),o);
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return conditionMap;
	}
//	public getColumnMap(Resource resource)
//	{
//		Field[] fields=resource.getClass().getDeclaredFields();
//		HashMap<Field>
//		for(Field field:fields)
//		{
//			col.
//			field.getAnnotation(Column.class);
//		}
//	}


	

}
