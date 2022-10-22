//$Id$
package com.mandrine.model;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.mandrine.db.Column;
import com.mandrine.util.DateFormatUtil;

public class Camp extends Resource {
	
	@Column(name="CAMP_ID")
	private int campID;
	
	@Column(name="CITY_ID")
	private int cityID;
	
	private Date beginDate;
	
	private Date endDate;
	
	@Column(name="ADDRESS")
	private String address;
	private String status;
	
	@Column(name="DATE")
	private String date;
	private List<Date> availDate;
	private List<Slot> slotList = new ArrayList<Slot>();

	public void setCampID(int campID) {
		this.campID = campID;
	}

	public int getCampID() {
		return this.campID;
	}

	public void setCityID(int cityID) {
		this.cityID = cityID;
	}

	public int getCityID() {
		return this.cityID;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = DateFormatUtil.toDate(beginDate);
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public Date getBeginDate() {
		return this.beginDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = DateFormatUtil.toDate(endDate);
	}

	public void calcAvailDate() {
		availDate = new ArrayList<Date>();
		LocalDate start = this.beginDate.toLocalDate();
		LocalDate end = this.endDate.toLocalDate();
		while (!start.isAfter(end)) {
			availDate.add(Date.valueOf(start));
			start = start.plusDays(1);
		}
	}

	public void addToSlotList(Slot slot) throws SQLException {
		this.slotList.add(slot);
	}

	public List<Slot> getSlotList() {
		return this.slotList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
	

}
