package com.idan.coupons.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.idan.coupons.utils.LoginUtils;

@Component
public class LoginFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;		
		HttpSession session = req.getSession(false);
		Cookie[] cookies = req.getCookies();
		String pageMethod = req.getMethod();
		
		if(session != null || LoginUtils.isDefaultAccess(req) || pageMethod.equals("OPTIONS")) {
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					req.setAttribute(cookie.getName(), cookie.getValue());
				} 
			}
			chain.doFilter(request, response);
			return;
		}
		HttpServletResponse res = (HttpServletResponse) response;
		res.setStatus(401);
		System.out.println("access denied");
		
	}

	

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
		
	}

	@Override
	public void destroy() {
		
		
	}

}
