package com.idan.coupons.threads;

//import java.util.Calendar;
//import java.util.GregorianCalendar;
//import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.idan.coupons.controller.CouponController;
import com.idan.coupons.exceptions.ApplicationException;
import com.idan.coupons.exceptions.ExceptionsHandler;
import com.idan.coupons.utils.DateUtils;

public class DeleteExpiredCouponTimerTask extends TimerTask{

//	private final static long onceADay= 1000 * 60 * 60 * 24; // Time period between activation set to once a day.	
	private CouponController couponController = new CouponController();
	private static final Logger logger = LogManager.getLogger(ExceptionsHandler.class);


//	public long getOnceADay() {
//		return onceADay;
//	}

//	public static void startDeleteExpiredCouponTimerTask() {
//
//		GregorianCalendar gc = new GregorianCalendar();
//
//		gc.set(Calendar.HOUR, 00);
//		gc.set(Calendar.MINUTE, 00);
//		gc.set(Calendar.SECOND, 00);
//
//		gc.add(Calendar.DAY_OF_MONTH, 1);
//		
////		gc.add(Calendar.SECOND, 10);
//
//		Timer timer = new Timer();
//
//		timer.schedule(new DeleteExpiredCouponTimerTask(), gc.getTime(), onceADay);
//	}

	@Override
	public void run() {

		try {
			logger.info(DateUtils.getCurrentDateAndTime() + ", Expired coupons deleted");
			couponController.deleteExpiredCoupon();
		}
		catch (ApplicationException exception){
			// To keep the thread going this method is surrounded with an empty catch.
			logger.error(DateUtils.getCurrentDateAndTime() + 
						 ", Problem with deleting expired coupons, action was interrupted", exception);
		}

	}



}
