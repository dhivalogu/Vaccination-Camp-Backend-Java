//$Id$
package com.mandrine.exception;

public class InvalidRequestException extends Exception {

	static String message="Invalid API Request,Try Again";
	public InvalidRequestException() {
		
		super(message);
		
	}
	public InvalidRequestException(String msg)
	{
		super(msg);
	}

	

}
