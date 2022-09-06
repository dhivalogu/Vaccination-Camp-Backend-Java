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

	   RequestBody reqObject=new RequestBody();
	   String[] requestResources=request.getRequestURI().replace("/VaccinationApp/api/", "").split("/");
	   Queue<String> resourceQueue=new LinkedList<String>();
	   resourceQueue.addAll(Arrays.asList(requestResources));
	   System.out.print(resourceQueue);
	   return resourceQueue;
		/*
		 * reqObject.resourceQueue.addAll(Arrays.asList(requestResources)); int lastIndex= requestResources.length-1; String lastElement=requestResources[lastIndex];
		 * 
		 * if((isNumber(lastElement) && requestResources.length==1) || (lastElement.equals("") && requestResources.length==1)) { throw new InvalidRequestException(); }
		 * 
		 * if(isNumber(lastElement)) { reqObject.resourceQueue.poll(); reqObject.setID(lastElement); }
		 * 
		 * return reqObject;
		 */
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
