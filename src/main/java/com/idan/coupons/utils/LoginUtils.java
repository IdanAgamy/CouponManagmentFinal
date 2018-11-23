package com.idan.coupons.utils;


import javax.servlet.http.HttpServletRequest;


public class LoginUtils {

	/**
	 * Allowing access to default pages for all users.
	 * @param request - The page request from the user.
	 * @return true - if the request is for one of the default access pages.
	 * 		   false - if the request is for a page that needs logging in.
	 */
	public static boolean isDefaultAccess(HttpServletRequest request) {
		
		String pageRequest = request.getRequestURL().toString();
		String pageMethod = request.getMethod();
		return 	pageRequest.endsWith("/login") || 
				(pageRequest.endsWith("/coupons") 	 &&  pageMethod.equals("GET")) ||
				pageRequest.endsWith("/byCouponType") ||
				pageRequest.endsWith("/upToPrice") ||
				pageRequest.endsWith("/upToEndDate") ||
				pageRequest.endsWith("/byCompanyID") ||
				(pageRequest.endsWith("/customers") &&  pageMethod.equals("POST") ) || 
				(pageRequest.endsWith("/companies") && (pageMethod.equals("GET") || pageMethod.equals("POST"))) ||
				pageRequest.endsWith("/byCompanyName");
	}

}
