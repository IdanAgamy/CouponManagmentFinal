package com.idan.coupons.utils;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//import com.idan.coupons.beans.UserLoginInfo;
//import com.idan.coupons.enums.UserType;
import com.idan.coupons.exceptions.ApplicationException;

public class UtilTester {
	
	public static void main(String[] args) throws ApplicationException {
		
		DateUtilsTester();
		ValidationUtilsTester();
		LoginTester();
		 
		System.out.println("**********End of test************");
		
	}
	
	private static void LoginTester() throws ApplicationException {
//		boolean ff = LoginUtils.login(new UserLoginInfo("BBB", "BBB@gmail.com", "1234BbBb", UserType.COMPANY));
//		System.out.println(ff);
	}

	private static void DateUtilsTester() {
		System.out.println("**********Testing DateUtils************");
		
		dateToStrConverterTest();
		strToDateConverterTest();
		getCurrentDateAndTimeTest();
		ConvertForDisplayTest();
		
	}
	
	private static void ValidationUtilsTester() {
		System.out.println("**********Testing ValidationUtils************");
		
		isValidDateFormatTest();
		isValidEmailFormatTest();
		isValidNameFormatTest();
		isValidPasswordFormatTest();
		isValidPriceTest();
		isValidAmountTest();
		isValidMessageFormatTest();
		isStartEndDateMissTest();
		isStartDateAlreadyPassedTest();
		
	}
	
	private static void dateToStrConverterTest() {
		System.out.println("**********Testing date To Str Converter************");
		
		GregorianCalendar today = new GregorianCalendar();
		System.out.println(today.getTime());
		System.out.println(DateUtils.dateToStrConverter(today));
		
	}

	private static void strToDateConverterTest() {
		System.out.println("**********Testing str To Date Converter************");
		
		String dateStr= "2010-10-10";
		System.out.println(dateStr);
		System.out.println(DateUtils.strToDateConverter(dateStr).getTime());
		
	}

	private static void getCurrentDateAndTimeTest() {
		System.out.println("**********Testing get Current Date And Time************");
		
		System.out.println(new GregorianCalendar().getTime());
		System.out.println(DateUtils.getCurrentDateAndTime());
		
	}

	private static void ConvertForDisplayTest() {
		System.out.println("**********Testing couponDao Convert For Display************");
		
		String dateStr = "2010-10-30";
		System.out.println(dateStr);
		System.out.println(DateUtils.ConvertForDisplay(dateStr));
		
	}
	
	private static void isValidAmountTest() {
		System.out.println("**********Testing is Valid Amount************");
		Map<Integer,Boolean> checkList = new HashMap<>();
		
		checkList.put(32, true);
		checkList.put(-23, false);
		checkList.put(0, true);
		
		Set<Integer> numbers = checkList.keySet();
		
		boolean isSuccess = true;
		
		for(Integer num:numbers) {
			boolean bool = checkList.get(num);
			boolean result = ValidationUtils.isValidAmount(num);
			if(result!=bool) {
				isSuccess = false;
			}
			System.out.println("is date " + num + " is valid: " + result + " Expected: " + bool);
		}
		
		System.out.println(isSuccess);
	}

	private static void isValidPriceTest() {
		System.out.println("**********Testing is Valid Price************");
		Map<Double,Boolean> checkList = new HashMap<>();
		
		checkList.put(32.0, true);
		checkList.put(-23.0, false);
		checkList.put(0d, false);
		
		Set<Double> doubles = checkList.keySet();
		
		boolean isSuccess = true;
		
		for(Double d:doubles) {
			boolean bool = checkList.get(d);
			boolean result = ValidationUtils.isValidPrice(d);
			if(result!=bool) {
				isSuccess = false;
			}
			System.out.println("is date " + d + " is valid: " + result + " Expected: " + bool);
		}
		
		System.out.println(isSuccess);
	}

	private static void isValidDateFormatTest() {
		System.out.println("**********Testing is Valid Date Format************");
		
		
		
		Map<String,Boolean> checkList = new HashMap<>();
		
		checkList.put("a-b", false);
		checkList.put("2000-10-10", true);
		checkList.put("3115-12-25", true);
		checkList.put("Kirk T", false);
		checkList.put("20-20", false);
		checkList.put("20-20-20", false);
		checkList.put("2018-13-13", false);
		checkList.put("999-10-10", false);
		checkList.put("10-10-10", false);
		checkList.put("2018-02-28", true);
		checkList.put("2018--10-10", false);
		checkList.put("2018-02-29", false);
		checkList.put("2000-02-29", true);
		checkList.put("1900-02-29", false);
		checkList.put("2018-09-31", false);
		
		Set<String> strs = checkList.keySet();
		
		boolean isSuccess = true;
		
		for(String str:strs) {
			boolean bool = checkList.get(str);
			boolean result = ValidationUtils.isValidDateFormat(str);
			if(result!=bool) {
				isSuccess = false;
			}
			System.out.println("is date " + str + " is valid: " + result + " Expected: " + bool);
		}
		
		System.out.println(isSuccess);
		
	}

	private static void isValidMessageFormatTest() {
		System.out.println("**********Testing is Valid Message Format************");
		Map<String,Boolean> checkList = new HashMap<>();
		
		checkList.put("a@b", false);
		checkList.put("lsjhbfglijrnvlidfjvlakjrflkhfbvlaihbvflkjhbfvlairflwiaemjc.scvnlarliadjvnakljdsfbvilawyrhbflsdkjhbfvlairb", true);
		
		Set<String> strs = checkList.keySet();
		
		boolean isSuccess = true;
		
		for(String str:strs) {
			boolean bool = checkList.get(str);
			boolean result = ValidationUtils.isValidMessageFormat(str);
			if(result!=bool) {
				isSuccess = false;
			}
			System.out.println("is name " + str + " is valid: " + result + " Expected: " + bool);
		}
		
		System.out.println(isSuccess);
		
	}

	private static void isStartDateAlreadyPassedTest() {
		System.out.println("**********Testing couponDao************");
		
		boolean results;
		results = ValidationUtils.isStartDateAlreadyPassed("2000-10-10");
		System.out.println("should be true: " + results);
		results = ValidationUtils.isStartDateAlreadyPassed("2020-10-10");
		System.out.println("should be false: " + results);
		
	}
	
	
	
	private static void isStartEndDateMissTest() {
		System.out.println("**********Testing couponDao************");
		
		boolean results;
		results = ValidationUtils.isStartEndDateMiss("2000-10-10","2010-10-10");
		System.out.println("should be false: " + results);
		results = ValidationUtils.isStartEndDateMiss("2010-10-10","2000-10-10");
		System.out.println("should be true: " + results);
		
	}
	
	
	private static void isValidPasswordFormatTest() {
		System.out.println("**********Testing is Valid Password Format************");
		Map<String,Boolean> checkList = new HashMap<>();
		
		checkList.put("a@b", false);
		checkList.put("Jim654aa", true);
		checkList.put("Martin71", true);
		checkList.put("Kirk T", false);
		checkList.put("ralf 123", false);
		checkList.put("Biff,22", false);
		checkList.put("CocaCola", false);
		checkList.put("123456", false);
		checkList.put("123456t", false);
		checkList.put("t1234567", true);
		checkList.put("kjdhbfvsjbvhslbjsn/", false);
		checkList.put("123456789", false);
		checkList.put("123456789kshdfgahfbalisfhd8327461982734jadfhgaskjgf9274356927t54kjdfhakjdh", false);
		
		Set<String> strs = checkList.keySet();
		
		boolean isSuccess = true;
		
		for(String str:strs) {
			boolean bool = checkList.get(str);
			boolean result = ValidationUtils.isValidPasswordFormat(str);
			if(result!=bool) {
				isSuccess = false;
			}
			System.out.println("is password " + str + " is valid: " + result + " Expected: " + bool);
		}
		
		System.out.println(isSuccess);

		
		
	}
	
	private static void isValidNameFormatTest() {
		System.out.println("**********Testing is Valid Name Format************");
		Map<String,Boolean> checkList = new HashMap<>();
		
		checkList.put("a@b", false);
		checkList.put("Jim", true);
		checkList.put("Martin.", true);
		checkList.put("Kirk T", true);
		checkList.put("ralf@Marsh", false);
		checkList.put("Biff, mars", false);
		checkList.put("CocaCola", true);
		checkList.put("Buddy -tim", true);
		checkList.put("k .farmly", true);
		checkList.put("not true", true);
		checkList.put("kjdhbfvsjbvhslbjsn/", false);
		checkList.put("nkafjvnsljbfsfnsfv", true);
		
		Set<String> strs = checkList.keySet();
		
		boolean isSuccess = true;
		
		for(String str:strs) {
			boolean bool = checkList.get(str);
			boolean result = ValidationUtils.isValidNameFormat(str);
			if(result!=bool) {
				isSuccess = false;
			}
			System.out.println("is name " + str + " is valid: " + result + " Expected: " + bool);
		}
		
		System.out.println(isSuccess);
		
	}
	
	private static void isValidEmailFormatTest() {
		System.out.println("**********Testing is Valid Email Format************");
		Map<String,Boolean> checkList = new HashMap<>();
		
		checkList.put("a@b", true);
		checkList.put("a.@b", false);
		checkList.put("a@b.", false);
		checkList.put("a@c@b", false);
		checkList.put("a.a@b.b", true);
		checkList.put("a.c@b", true);
		checkList.put("a.b", false);
		checkList.put("a", false);
		checkList.put("\"a\"@b", false);
		checkList.put("a@b.c.d.e.g", true);
		checkList.put("abb..ss@gmail.com", false);
		
		Set<String> strs = checkList.keySet();
		
		boolean isSuccess = true;
		
		for(String str:strs) {
			boolean bool = checkList.get(str);
			boolean result = ValidationUtils.isValidEmailFormat(str);
			if(result!=bool) {
				isSuccess = false;
			}
			System.out.println("is mail " + str + " is valid: " + result + " Expected: " + bool);
		}
		
		System.out.println(isSuccess);
	}
	
}
