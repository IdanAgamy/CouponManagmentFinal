//package com.idan.coupons.filters;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import com.idan.coupons.enums.CouponType;
//import com.idan.coupons.enums.UserType;
//import com.idan.coupons.utils.LoginUtils;
//
//@WebFilter("/companies/*")
//public class CompanyFilter implements Filter{
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		HttpServletRequest req = (HttpServletRequest) request;
//		UserType userType =UserType.valueOf( (String) req.getAttribute("userType"));
//		HttpSession session = req.getSession(false);
//		String pageRequest = req.getRequestURL().toString();
//		if(userType==UserType.COMPANY || LoginUtils.isDefultAccess(req, session, pageRequest)) {
//			
//			chain.doFilter(request, response);			
//			return;
//		}
//		HttpServletResponse res = (HttpServletResponse) response;
//		res.setStatus(401);
//		
//	}
//
//	@Override
//	public void init(FilterConfig arg0) throws ServletException {
//		
//		
//	}
//
//	@Override
//	public void destroy() {
//		
//	}
//
//}
