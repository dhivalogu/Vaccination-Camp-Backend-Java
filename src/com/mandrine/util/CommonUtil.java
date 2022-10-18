//$Id$
package com.mandrine.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.mandrine.exception.InvalidRequestException;

public class CommonUtil {

	static List<String> resourceList = Arrays.asList("cities", "camps", "slots", "accounts", "people", "bookings","authentication","invalidate");

	public static Boolean isNumber(String value) {
		try {
			Long.parseLong(value);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public static Queue<String> parseRequest(HttpServletRequest request) throws Exception {

		List<String> requestResources = Arrays.asList(request.getRequestURI().replace("/api/", "").split("/"));
		for (String resource : requestResources) {
			if (!isNumber(resource) && !resourceList.contains(resource))
				throw new InvalidRequestException();
		}
		Queue<String> resourceQueue = new LinkedList<String>();
		resourceQueue.addAll(requestResources);
		return resourceQueue;

	}

	public static Boolean isValidAadhar(String aadharID) {
		String regex = "^[2-9]{1}[0-9]{11}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(aadharID);
		return matcher.matches();
	}
}
