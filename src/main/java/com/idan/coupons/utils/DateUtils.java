package com.idan.coupons.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringJoiner;

public class DateUtils {
	
	/**
	 * Convert the date to the string format used in the DB.
	 * @param date - a GregorianCalendar Object of the date.
	 * @return String in the format yyyy-mm-dd that is used in the DB.
	 */
	public static String dateToStrConverter(GregorianCalendar date) {
		
		String[] dateStrArr = {	String.format("%04d", date.get(Calendar.YEAR)),
								String.format("%02d", (date.get(Calendar.MONTH)+1)),  // GregorianCalendar sets January as 0;
								String.format("%02d", date.get(Calendar.DAY_OF_MONTH))};
		
		return stringArrJoiner(dateStrArr);
	}
	
	/**
	 * Convert the string format used in the DB to a date object.
	 * @param dateStr - String in the format yyyy-mm-dd that is used in the DB.
	 * @return a GregorianCalendar Object of the date.
	 */
	public static GregorianCalendar strToDateConverter(String dateStr) {
		
		String[] dateStrArr = stringDevider(dateStr);
		GregorianCalendar gc = new GregorianCalendar();
		
		gc.set(Calendar.YEAR, Integer.parseInt(dateStrArr[0]));
		gc.set(Calendar.MONTH, Integer.parseInt(dateStrArr[1])-1);	// GregorianCalendar sets January as 0;
		gc.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateStrArr[2]));
		
		return gc;
		
	}
	
	/**
	 * Get current date in format used in the DB
	 * @return String in the format yyyy-mm-dd that is used in the DB of the current date.
	 */
	public static String getCurrentDateAndTime() {
		
		return new GregorianCalendar().getTime().toString();
	}

	/**
	 * Rearrange the date used in the DB for the date in European format.
	 * @param dateStr - String in the format yyyy-mm-dd that is used in the DB.
	 * @return String in the format dd-mm-yyyy that is used in the DB.
	 */
	public static String ConvertForDisplay(String dateStr) {
		String[] dateStrArr = stringDevider(dateStr);
		
		String temp = dateStrArr[0];
		dateStrArr[0] = dateStrArr[2];
		dateStrArr[2] = temp;
		
		return stringArrJoiner(dateStrArr);
	}

	/**
	 * Split the date string to its parts as an array.
	 * @param dateStr - String in the format yyyy-mm-dd that is used in the DB.
	 * @return Array of parts of the date.
	 */
	private static String[] stringDevider(String dateStr) { 
		
		return dateStr.split("-");
		
	}

	/**
	 * Join the array of the split date format to a single array.
	 * @param dateStrArr - Array of parts of the date.
	 * @return String in the format yyyy-mm-dd that is used in the DB.
	 */
	private static String stringArrJoiner(String[] dateStrArr) {
		StringJoiner sj = new StringJoiner("-");
		
		sj.add(dateStrArr[0]);
		sj.add(dateStrArr[1]);
		sj.add(dateStrArr[2]);		
		
		return sj.toString();
	}

	
//	public static String addYears(String dateStr, int yearsToAdd) {
//		
//		String[] dateStrArr = stringDevider(dateStr);
//		
//		dateStrArr[0] += yearsToAdd;
//		
//		return stringArrJoiner(dateStrArr);
//		
//		
//	}
//	
//	public static String addMonths(String dateStr, int monthsToAdd) {
//		
//		String[] dateStrArr = stringDevider(dateStr);
//		
//		dateStrArr[1] += monthsToAdd;
//		
//		return stringArrJoiner(dateStrArr);
//		
//		
//	}
//	
//	public static String addDays(String dateStr, int daysToAdd) {
//		
//		String[] dateStrArr = stringDevider(dateStr);
//		
//		dateStrArr[2] += daysToAdd;
//		
//		return stringArrJoiner(dateStrArr);
//		
//		
//	}
	

}
