package com.idan.coupons.threads;

import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.idan.coupons.controller.CouponController;
import com.idan.coupons.exceptions.ApplicationException;
import com.idan.coupons.exceptions.ExceptionsHandler;
import com.idan.coupons.utils.DateUtils;

@Component
public class DeleteExpiredCouponTimerTask extends TimerTask{

	@Autowired
	private CouponController couponController;
	private static final Logger logger = LogManager.getLogger(ExceptionsHandler.class);

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
