//$Id$
package com.mandrine.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AuthenticationUtil {

	public static void AuthenticateRequest(HttpServletRequest request) throws IllegalAccessException {
		Map<String, String> cookieMap = new HashMap<>();
		if (request.getCookies() != null) {
			for (Cookie ck : request.getCookies()) {
				cookieMap.put(ck.getName(), ck.getValue());
			}
		}
		HttpSession session = request.getSession(false);
		String[] requestURI = request.getRequestURI().split("/");
		String resource = requestURI[requestURI.length - 1];
		if (!(resource.equals("authentication") && request.getMethod().equals("POST")) && !(resource.equals("people") && request.getMethod().equals("POST"))) {
			if (session == null) {
				throw new IllegalAccessException();
			} else {

				if (session.getAttribute("user") == null || !(request.getRemoteAddr().equals(session.getAttribute("IP")))) {
					throw new IllegalAccessException();
				}
				if(!session.getAttribute("user").equals(cookieMap.get("user")) || !session.getAttribute("accessLevel").equals(cookieMap.get("accessLevel")))
				{
					throw new IllegalAccessException();
				}
						
			}
		}
	}
}
