package com.idan.coupons.api;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.idan.coupons.beans.Company;
import com.idan.coupons.beans.Customer;
import com.idan.coupons.beans.UserLoginInfo;
import com.idan.coupons.controller.CompanyController;
import com.idan.coupons.controller.CustomerController;
import com.idan.coupons.enums.UserType;
import com.idan.coupons.exceptions.ApplicationException;
import com.idan.coupons.utils.CookieUtil;

@RestController
@RequestMapping("/login")
public class LoginApi {
	
	@Autowired
	CompanyController companyConroller;
	@Autowired
	CustomerController customerController;
	
	/**
	 * method for debugging purpose only
	 * TODO - delete method
	 */
	@RequestMapping(method = RequestMethod.GET)
	public void hhhyy(HttpServletRequest request) {
		
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				System.out.println(c.getName() + " " + c.getValue());

			} 
		}
		
		HttpSession session = request.getSession(false);
		System.out.println(session);
	}
	
	/**
	 * logging in to the web site.
	 * @param request - an HttpServletRequest object, for creating session and cookies
	 * @param response - an HttpServletResponse object, for setting the response to the client.
	 * @param userLoginInfo - UserLoginInfo object with the login parameters
	 * @return - response with the status for the client
	 * @throws ApplicationException
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void login(HttpServletRequest request, HttpServletResponse response,@RequestBody  UserLoginInfo userLoginInfo) throws ApplicationException {
		
		
		if (userLoginInfo != null) {
			// Validating the admin login.
			if(userLoginInfo.getUserType() == UserType.ADMIN && userLoginInfo.getName().equals("admin") && userLoginInfo.getPassword().equals("1234") && userLoginInfo.getEmail().equals("admin@coupons")) {
				request.getSession();
				// Adding cookies for admin.
				response.addCookie(new Cookie("userType",UserType.ADMIN.name()));
				return;
			}
			// Validating a company login.
			if(userLoginInfo.getUserType() == UserType.COMPANY) {
				
				Company company = companyConroller.login(userLoginInfo.getName(), userLoginInfo.getPassword());
				if(company != null) {
					request.getSession();					
					List<Cookie> loginCookies = CookieUtil.loginCookies(company);
					response = CookieUtil.addCookies(response, loginCookies);	
					return;
				}
				response.setStatus(401);
				return;
			}
			// Validating a customer login.
			if(userLoginInfo.getUserType() == UserType.CUSTOMER) {
				Customer customer = customerController.login(userLoginInfo.getEmail(), userLoginInfo.getPassword());
				if(customer != null) {
					request.getSession();	
					List<Cookie> loginCookies = CookieUtil.loginCookies(customer);
					response = CookieUtil.addCookies(response, loginCookies);
					return;
				}
				response.setStatus(401);
				return;
			}
		}
		response.setStatus(401);
		return;
		
	}
	
	

	
	
	/*
{
"name":"patric",
			"userType": "CUSTOMER",
			"password":"asdf1234",
			"email":"a@b"
	}
	 */
	

}
