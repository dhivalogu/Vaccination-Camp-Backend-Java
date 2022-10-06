//$Id$
package com.mandrine.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AuthenticationUtil {

	public static void AuthenticateRequest(HttpServletRequest request) throws IllegalAccessException {
		HttpSession session = request.getSession(false);
		String[] requestURI = request.getRequestURI().split("/");
		String resource = requestURI[requestURI.length - 1];
		if (!(resource.equals("authentication") && request.getMethod().equals("POST")) && !(resource.equals("people") && request.getMethod().equals("POST")) ) {
			if (session == null) {
				throw new IllegalAccessException();
			} else {
				if(session.getAttribute("user")==null || !(request.getRemoteAddr().equals(session.getAttribute("IP"))))
				{
					throw new IllegalAccessException();
				}
			}
		}
	}
}
