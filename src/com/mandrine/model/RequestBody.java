//$Id$
package com.mandrine.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.mandrine.exception.InvalidRequestException;

public class RequestBody {

	String resource=null;
	String id=null;
	static String[] validResources={"booking","accounts","people","cities","camps","slots"};
	static List<String> resourceList= Arrays.asList(validResources);
	public Queue<String> resourceQueue=new LinkedList<String>();
	
	public void setResource(String resource) throws InvalidRequestException
	{
		if(!resourceList.contains(resource))
		{
			throw new InvalidRequestException();
		}
		this.resource=resource;
	}
	public String getResource()
	{
		return this.resource;
	}
	public void setID(String id)
	{
		this.id=id;
	}
	public String getID()
	{
		return this.id;
	}

}
