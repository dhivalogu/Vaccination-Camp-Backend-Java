//$Id$
package com.mandrine.util;

import java.sql.Date;
public class DateFormatUtil {

	public static Date toDate(String date)
	{
		return Date.valueOf(date);
	}
	public static String convertToDateRange(Date beginDate,Date endDate)
	{
		return "["+beginDate+","+endDate+"]";
	}
}
