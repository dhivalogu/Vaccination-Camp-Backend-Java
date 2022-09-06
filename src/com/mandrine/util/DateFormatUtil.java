//$Id$
package com.mandrine.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
public class DateFormatUtil {

	public static Date toDate(String date)
	{
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-mm-dd");
		return Date.valueOf(date);
	}
	public static String convertToDateRange(Date beginDate,Date endDate)
	{
		return "["+beginDate+","+endDate+"]";
	}
}
