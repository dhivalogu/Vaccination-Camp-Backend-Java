//$Id$
package com.mandrine.util;

import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mandrine.cache.CacheDB;

public class MyAppServletContextListener 
               implements ServletContextListener{
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("ServletContextListener destroyed");
	}

        
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			CacheDB.loadOverallData();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}	
	}
}
