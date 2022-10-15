
package com.mandrine.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.mandrine.db.Column;
import com.mandrine.util.DateFormatUtil;

public class People extends Resource{
	
	@Column(name="AADHAR")
	private String AadharID;
	
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="LAST_NAME")
	private String lastName;
	
	@Column(name="MOBILE")
	private String mobile;
	
	@Column(name="DOB")
	public Date DOB;
	
	@Column(name="DOSAGE COUNT")
	private int dosageCount;
	
	private List<Booking> bookingList=new ArrayList<Booking>();
	
	public void setID(String AadharID)
	{
		this.AadharID=AadharID;
	}
	public String getID()
	{
		return this.AadharID;
	}
	public void setFirstName(String firstName)
	{
		this.firstName=firstName;
	}
	public String getFirstName()
	{
		return this.firstName;
	}
	public void setLastName(String lastName)
	{
		this.lastName=lastName;
	}
	public String getLastName()
	{
		return this.lastName;
	}
	public void setMobile(String mobile)
	{
		this.mobile=mobile;
	}
	public String getMobile()
	{
		return this.mobile;
	}
	public void setDosageCount(int dosageCount)
	{
		this.dosageCount=dosageCount;
	}
	public int getDosageCount()
	{
		return this.dosageCount;
	}
	public String getDetails()
	{
		return " "+this.AadharID+" "+this.firstName+" "+this.lastName+" "+this.mobile+" "+this.dosageCount;
	}
	public void setDOB(String DOB)
	{
		this.DOB=DateFormatUtil.toDate(DOB);
	}
	public Date getDOB()
	{
		return this.DOB;
	}
	public List<Booking> getBookingList()
	{
		return this.bookingList;
	}
	public void addToBookingList(Booking bookingDetail)
	{
		this.bookingList.add(bookingDetail);
	}

}
