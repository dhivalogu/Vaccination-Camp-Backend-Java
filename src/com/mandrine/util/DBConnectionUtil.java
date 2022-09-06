
package com.mandrine.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionUtil {

	private static final String DRIVER="org.postgresql.Driver";
	private static final String URL="jdbc:postgresql://localhost:5432/vaccinationdb";
	private static final String USERNAME="postgres";
	private static final String PASSWORD="";
	private static Connection connection=null;
	
	public static Connection openConnection()
	{

	
			try
			{
				Class.forName(DRIVER);
				connection=DriverManager.getConnection(URL,USERNAME,PASSWORD);
				
			}
			
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return connection;	
		
		
	}
	public static void closeConnection() throws SQLException
	{
		if(connection!=null)
		{
			connection.close();
		}
	}

}