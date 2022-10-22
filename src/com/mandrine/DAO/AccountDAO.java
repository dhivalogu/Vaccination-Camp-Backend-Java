//$Id$
package com.mandrine.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mandrine.cache.CacheDB;
import com.mandrine.db.DBResource;
import com.mandrine.model.Account;

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
