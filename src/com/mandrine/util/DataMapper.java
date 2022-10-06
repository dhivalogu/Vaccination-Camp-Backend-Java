//$Id$
package com.mandrine.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mandrine.db.Column;

public class DataMapper {
	
	public static <T> List<T> Map(Class<T> clazz,ResultSet resultSet)
	{
	    List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
	    for(Field field: fields) {
	        field.setAccessible(true);
	    }

	    List<T> list = new ArrayList<>();
	    try {
			while(resultSet.next()) {

			    T dto = clazz.getConstructor().newInstance();

			    for(Field field: fields) {
			        Column col = field.getAnnotation(Column.class);
			        if(col!=null) {
			            String name = col.name();
			            try{
			   
			       
			                if(field.getType().getTypeName().equals("int"))
			                {
			                	int value=resultSet.getInt(name);
			                	field.set(dto,value);
			                }
			                else if(field.getType().equals(String.class))
			                {
			                	String value=resultSet.getString(name);
			                	field.set(dto, value);
			                }
			                
			            } catch (Exception e) {
			                e.printStackTrace();
			            }
			        }
			    }

			    list.add(dto);

			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | SQLException e) {
			e.printStackTrace();
		}
	    return list;
	}

}
