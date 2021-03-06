package com.idan.coupons.api;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.idan.coupons.beans.CompanyEntity;
import com.idan.coupons.beans.CustomerEntity;
import com.idan.coupons.beans.UserLoginInfo;
import com.idan.coupons.controller.CompanyController;
import com.idan.coupons.controller.CustomerController;
import com.idan.coupons.enums.UserType;
import com.idan.coupons.exceptions.ApplicationException;
import com.idan.coupons.utils.CookieUtil;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/login")
public class LoginApi {
	
	@Autowired
	CompanyController companyConroller;
	@Autowired
	CustomerController customerController;
	
	
	/**
	 * Logging in to the web site.
	 * @param request - an HttpServletRequest object, for creating session and cookies
	 * @param response - an HttpServletResponse object, for setting the response to the client.
	 * @param userLoginInfo - UserLoginInfo object with the login parameters
	 * @return - response with the status for the client
	 * @throws ApplicationException
	 */
	@RequestMapping(method = RequestMethod.POST)
	public UserLoginInfo login(HttpServletRequest request, HttpServletResponse response, @RequestBody  UserLoginInfo userLoginInfo) throws ApplicationException {
		
		if (userLoginInfo != null) {
			// Validating the admin login.
			if(userLoginInfo.getUserType() == UserType.ADMIN && userLoginInfo.getName().equals("admin") && userLoginInfo.getPassword().equals("qwer1234") && userLoginInfo.getEmail().equals("admin@coupons")) {
				request.getSession();
				// Adding cookies for admin.
				List<Cookie> loginCookies = CookieUtil.loginCookies();
				
				response = CookieUtil.addCookies(response, loginCookies);	
				userLoginInfo.setUserID(0L);
				return userLoginInfo;
			}
			// Validating a company login.
			if(userLoginInfo.getUserType() == UserType.COMPANY) {
				
				CompanyEntity company = companyConroller.login(userLoginInfo.getName(), userLoginInfo.getPassword());
				if(company != null) {
					request.getSession();					
					List<Cookie> loginCookies = CookieUtil.loginCookies(company);
					response = CookieUtil.addCookies(response, loginCookies);	
					userLoginInfo.setUserID(company.getCompanyId());
					userLoginInfo.setEmail(company.getCompanyEmail());
					return userLoginInfo;
				}
				response.setStatus(401);
				return null;
			}
			// Validating a customer login.
			if(userLoginInfo.getUserType() == UserType.CUSTOMER) {
				CustomerEntity customer = customerController.login(userLoginInfo.getEmail(), userLoginInfo.getPassword());
				if(customer != null) {
					request.getSession();	
					List<Cookie> loginCookies = CookieUtil.loginCookies(customer);
					response = CookieUtil.addCookies(response, loginCookies);
					userLoginInfo.setUserID(customer.getCustomerId());
					userLoginInfo.setName(customer.getCustomerName());
					return userLoginInfo;
				}
				response.setStatus(401);
				return null;
			}
		}
		response.setStatus(401);
		return null;
		
	}
	
	@RequestMapping(value ="/logout", method = RequestMethod.GET)
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if( session != null) {
			session.invalidate();
		}
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie cookie: cookies) {
				cookie.setValue("");
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		}
	}

}
