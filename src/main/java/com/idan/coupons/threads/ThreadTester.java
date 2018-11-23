package com.idan.coupons.threads;

//import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
//import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
//import java.util.TimerTask;

//import com.idan.coupons.utils.JdbcUtils;

public class ThreadTester {
	
	public static void main(String[] args) throws SQLException, InterruptedException {
		
//		Connection conection = JdbcUtils.getConnection();
		
//		Thread.sleep(3000);
		
//		TimerTask tt = new TimerTask() {
//			
//			@Override
//			public void run() {
//				System.out.println(new Date());
//				
//				try {
//					Thread.sleep(2500);
//				} catch (InterruptedException e) {
//					
//					e.printStackTrace();
//				}
//				
//				System.out.println("doing something");
//				
//			}
//		};
		
//		Date date = new Date();
		GregorianCalendar gc = new GregorianCalendar();
		
		gc.add(Calendar.SECOND, 5);
//		
//		
//		System.out.println(gc.getTime());
////		System.out.println(date);
////		
////		date.setTime(date.getTime()+5000);
////		
////		System.out.println(date);
////		gc.set(Calendar.HOUR_OF_DAY,20);
		Timer t = new Timer();
//		
//		t.schedule(tt, gc.getTime(), 5*1000);
//		
		t.schedule(new Harta(), gc.getTime(), 5000);
//		
//		gc.set(Calendar.HOUR, 00);
//		 gc.set(Calendar.MINUTE, 00);
//		 gc.set(Calendar.SECOND, 00);
//		 
//		 gc.add(Calendar.DAY_OF_MONTH, 1);
		
//		DeleteExpiredCouponTimerTask.startDeleteExpiredCouponTimerTask();
		
//		DeleteExpiredCouponTimerTask t = new DeleteExpiredCouponTimerTask();
//		
//		t.run();
	}

}
