//$Id$
package com.mandrine.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mandrine.cache.CacheDB;
import com.mandrine.db.DBResource;
import com.mandrine.model.Account;
import com.mandrine.util.DBConnectionUtil;

public class AccountDAO {
  private static Connection connection=null;

public static Boolean AccountVerified(Account account) throws SQLException
  {
	  AccountDAO.connection= DBConnectionUtil.openConnection();
	  ResultSet rs= DBResource.CREDENTIALS.fetchAll(account);;
	  if(rs.next())
	  	{
	  		int accessLevel=Integer.parseInt(rs.getString("ACCESS_LEVEL"));
	  		account.setAccessLevel(accessLevel);
	  	    DBConnectionUtil.closeConnection();
	  		return true;
	  	}
	  DBConnectionUtil.closeConnection();
	  return false;
	  	
	  
  }
  public static void addAccount(Account account) throws SQLException
  {
	  AccountDAO.connection= DBConnectionUtil.openConnection();
	  PreparedStatement stmt=connection.prepareStatement("INSERT INTO \"CREDENTIALS\" VALUES(?,?,?);");
	  stmt.setString(1,account.getUsername());
	  stmt.setString(2,account.getPassword());
	  stmt.setInt(3,account.getAccessLevel());
	  System.out.println(stmt.executeUpdate()+ " Account Inserted");
	  DBConnectionUtil.closeConnection();
	  CacheDB.loadPeopleCache();
  }
}
