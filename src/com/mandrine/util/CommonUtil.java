//$Id$
package com.mandrine.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.mandrine.exception.InvalidRequestException;
import com.mandrine.model.RequestBody;

public class CommonUtil {
	
	static List<String> resourceList=Arrays.asList("cities","camps","slots","accounts","authentication","people","bookings","invalidate");

	public static Boolean isNumber(String value)
	{
		try
		{
			Integer.parseInt(value);
			return true;
		}
		catch(NumberFormatException e)
		{
			return false;
		}
		
	}
	
   public static Queue<String> parseRequest(HttpServletRequest request) throws Exception
   {

	  
	   String[] requestResources=request.getRequestURI().replace("/VaccinationApp/api/", "").split("/");
	   for(String resource:requestResources)
	   {
		   if(!CommonUtil.isValidAadhar(resource)&& !isNumber(resource) && !resourceList.contains(resource)) throw new InvalidRequestException();
	   }
	   Queue<String> resourceQueue=new LinkedList<String>();
	   resourceQueue.addAll(Arrays.asList(requestResources));
	   System.out.print(resourceQueue);
	   return resourceQueue;
		
   }
   public static Boolean isValidAadhar(String aadharID)
   {
	   String regex="^[2-9]{1}[0-9]{11}$";
	   Pattern pattern=Pattern.compile(regex);
	   Matcher matcher=pattern.matcher(aadharID);
	   return matcher.matches();
   }
  /* public static List paginateRecords(List<T> records,int n)
   {
	
	return records.stream().limit(n).collect(Collectors.toList());
	   
   }*/
   
   
   
}
