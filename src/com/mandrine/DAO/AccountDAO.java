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

public static ResultSet fetch(Account account) throws SQLException
  {
	  return DBResource.CREDENTIALS.fetchAll(account);	
	  
  }
  public static void create(Account account) throws SQLException
  {
	  DBResource.CREDENTIALS.create(account);
	  CacheDB.loadPeopleCache();
  }
}
