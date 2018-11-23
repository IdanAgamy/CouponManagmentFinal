package com.idan.coupons.threads;

//import java.util.Calendar;
//import java.util.GregorianCalendar;
//import java.util.Timer;
import java.util.TimerTask;

import com.idan.coupons.controller.CouponController;
import com.idan.coupons.exceptions.ApplicationException;

public class DeleteExpiredCouponTimerTask extends TimerTask{

//	private final static long onceADay= 1000 * 60 * 60 * 24; // Time period between activation set to once a day.	
	private CouponController couponController = new CouponController();


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
			System.out.println("Deleting expired coupons");
			couponController.deleteExpiredCoupon();
		}
		catch (ApplicationException e){
			// To keep the thread going this method is surrounded with an empty catch.
			// TODO- add logger.
		}

	}



}
