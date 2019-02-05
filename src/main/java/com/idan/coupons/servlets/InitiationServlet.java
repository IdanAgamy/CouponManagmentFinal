package com.idan.coupons.servlets;

//import java.io.IOException;
import java.util.Calendar;
//import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;

import javax.annotation.PostConstruct;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.idan.coupons.threads.DeleteExpiredCouponTimerTask;
//import com.idan.coupons.threads.Harta;


// TODO- make sure the necessity of this class.
/**
 * Servlet implementation class InitiationServlet.
 * This servlet is responsible for every method that needed to be start on load of server
 */
//@WebServlet("/InitiationServlet")
@Component
public class InitiationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Autowired
	DeleteExpiredCouponTimerTask deleteExpiredCouponTimerTask;
	
	@PostConstruct
    public void Init() {
    	
    	startDeleteExpiredCoupons();
    	
    }

    /**
     * start Delete Expired Coupons.
     */
	private void startDeleteExpiredCoupons() {
		
		// Getting the time of startup of the server and set it for next day at midnight.
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(Calendar.HOUR, 00);
		gc.set(Calendar.MINUTE, 00);
		gc.set(Calendar.SECOND, 00);
		gc.add(Calendar.DAY_OF_MONTH, 1);
		System.out.println("started DeleteExpiredCouponTimerTask thread, expierd coupon will be deleted first at: " + gc.getTime() + " and every 24 hourse after that.");
		// Setting the start and time of running of the thread.
		Timer timer = new Timer();
		timer.schedule(deleteExpiredCouponTimerTask, gc.getTime(), 1000 * 60 * 60 * 24);
	}
    
    
	
	

}
