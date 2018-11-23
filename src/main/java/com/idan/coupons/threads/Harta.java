package com.idan.coupons.threads;

import java.util.Date;
import java.util.TimerTask;

public class Harta extends TimerTask{

	@Override
	public void run() {
		System.out.println(new Date());
		System.out.println("harta");
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		System.out.println("barta");
		
	}

}
