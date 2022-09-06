//$Id$
package com.mandrine.util;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

public class JSONUtil {

	public static JSONObject toJSON(HttpServletRequest request) throws IOException
	{
		String requestData = request.getReader().lines().collect(Collectors.joining());
		System.out.print(requestData.length());
		if(requestData.length()<5)
		{
			throw new NullPointerException("Give proper request body!");
		}
		return new JSONObject(requestData);
	}
	public static String listToJSON(List objectList)
	{
	   return new Gson().toJson(objectList);
	}
	
}
