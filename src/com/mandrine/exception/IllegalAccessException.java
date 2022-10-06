//$Id$
package com.mandrine.exception;

public class IllegalAccessException extends Exception {
	static String message="Illegal Access";
	
	public IllegalAccessException()
	{
		super(message);
	}

}
