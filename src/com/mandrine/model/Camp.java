//$Id$
package com.mandrine.model;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.mandrine.cache.CacheDB;
import com.mandrine.util.DateFormatUtil;

public class Camp extends Resource {
	private int campID;
	private int cityID;
	private Date beginDate;
	private Date endDate;
	private String address;
	private String status;
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
		return this.endDate = endDate;
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

}
