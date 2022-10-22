//$Id$
package com.mandrine.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mandrine.db.Column;

public class City extends Resource{
 @Column(name="CITY_ID")
 public int cityID;
 
 @Column(name="NAME")
 private String name;
 
 @Column(name="STOCK")
 private int stock;
 
 @Column(name="VACCINATED_COUNT")
 private int vaccinatedCount;
 private Camp camp;
 private List<Camp> campList=new ArrayList<Camp>();
 
 public List<Camp> getCampList() {
	return campList;
}
public void setCityID(int cityID)
 {
	 this.cityID=cityID;
 }
 public int getCityID()
 {
	 return this.cityID;
 }
 public void setName(String name)
 {
	 this.name=name;
 }
 public String getName()
 {
	 return this.name;
 }
 public void setStock(int stock)
 {
	 this.stock=stock;
 }
 public int getStock()
 {
	 return this.stock;
 }
 public void updateStock(int value)
 {
	 this.stock+=value;
 }
 public void setVaccinatedCount(int count)
 {
	 this.vaccinatedCount=count;
 }
 public int getVaccinatedCount()
 {
	 return this.vaccinatedCount;
 }
 public void setCamp(Camp camp) throws SQLException
 {
	
	 this.camp=camp;
 }
 public Camp getCamp()
 {
	 return this.camp;
 }
 public void addToCampList(Camp camp)
 {
	 campList.add(camp);
 }
}
