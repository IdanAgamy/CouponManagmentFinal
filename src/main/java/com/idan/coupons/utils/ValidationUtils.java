package com.idan.coupons.utils;

import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import com.idan.coupons.enums.ErrorType;
import com.idan.coupons.enums.UserType;
import com.idan.coupons.exceptions.ApplicationException;

public class ValidationUtils {


	
	/**
	 * This method if the provided date in the yyyy-mm-dd format.
	 * @param str - input of the date as a string.
	 * @return If the date input is valid or not.
	 */
	public static boolean isValidDateFormat(String str) {
		String[] date = str.split("-");
		
		if(date.length !=3) {
			return false;
		}
		
		int year,month,day;
		
		try {
			year 	= Integer.parseInt(date[0]);
			month 	= Integer.parseInt(date[1]);
			day 	= Integer.parseInt(date[2]);
		} catch (NumberFormatException e) {
			// If an exception was thrown then it is not a valid date format.
			return false;
		}
		
		// Checking if the year is in the range provided by MySQL.
		if(year < 1000 || year > 9999) {
			return false;
		}
		
		if(month < 1 || month > 12) {
			return false;
		}
		
		if((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && (day > 31 || day < 1)) {
			return false;
		}
		
		if((month == 4 || month == 6 || month == 9 || month == 11) && (day > 30 || day < 1)) {
			return false;
		}
		boolean isLeapYear = false;
		
		// For February doing a leap year check.
		if(month == 2) {
			if(year % 4 == 0)
			{
				if( year % 100 == 0)
				{
					if ( year % 400 == 0) {
						isLeapYear = true;
					}
					else {
						isLeapYear = false;
					}
				}
				else {
					isLeapYear = true;
				}
			}
			else {
				isLeapYear = false;
			}
			
			if(isLeapYear && (day <1 || day > 29)) {
				return false;
			}
			
			if(!isLeapYear && (day <1 || day > 28)) {
				return false;
			}
		}
			
		
		
		return true;
	}
	
	/**
	 * This method if the provided email address in the local-part@domain format.
	 * Because the users of this system are supposed to be serious companies and paying customers, the e-mails which
	 * contains the special characters "!#$%&'*+/=?^`{|}~"(),:;<>@[\]" are prohibited to use. The only exception is the 
	 * period('.'), underscore ('_') and hyphen ('-') with some restrictions, the same restriction are applied for G-mail
	 * and Windows Live Hot-mail.
	 * @param str - input of the email address as a string.
	 * @return If the email input is valid or not.
	 */
	public static boolean isValidEmailFormat(String str) {
		
		char[] charArr=str.toCharArray();
		int lastCharPos = charArr.length-1;
		boolean isContainAt = false;
		
		// Checking for valid email length.
		if(charArr.length < 3 || charArr.length >45) {
			return false;
		}
		
		// Checking the beginning of the e-mail address is valid.
		if(!isValidEmailEdge(charArr[0])) {
			
			return false;
			
		}
		
		// Checking each character in the email address besides the beginning and the end which we check separately.
		// Only if the at('@') character is in this part the email considered valid.
		for(int i = 1; i < lastCharPos; ++i) {
			
			if(isEmailProhibitedChar(charArr[i])) {
				return false;
			}
			
			if(!isEnglishLetter(charArr[i]) || !isDigit(charArr[i])) {
				
				// Checking there is only one at('@') character and that the Local-part does not end with restricted
				// character.
				if(charArr[i] == '@') {
					if (!isEmailRestrictedChar(charArr[i-1]) && !isContainAt) {
						isContainAt = true;
					}
					else {
						return false;
					}
				}
				
				// Checking that the domain doesn't starts with restricted character or there are two restricted character
				// in a row.
				if(isEmailRestrictedChar(charArr[i]) && (charArr[i-1] == '@' || isEmailRestrictedChar(charArr[i-1]))) {
					return false;
				}
				
				
			}
		}
		
		// Checking the end of the e-mail address is valid.
		if(!isValidEmailEdge(charArr[lastCharPos])) {
			
			return false;
			
		}
		
		// Checking if the email address contain the at('@') character.
		if(!isContainAt) {
			return false;
		}
		
		return true;
		
	}

	/**
	 * This method checks if the provided name is valid.
	 * Besides letters and digits, period('.') hyphen('-') and spaces(' ') are permitted as long as they are not at the 
	 * beginning of the string.
	 * @param str - input of the name as a string.
	 * @return If the name input is valid or not.
	 */
	public static boolean isValidNameFormat(String str) {
		
		char[] charArr=str.toCharArray();
		
		// Checking for valid name length.
		if(charArr.length < 3 || charArr.length >45) {
			return false;
		}
		
		// Checking if valid name start.
		if(!isEnglishLetter(charArr[0]) && !isDigit(charArr[0])) {
			return false;
		}
		
		for(int i = 1; i <charArr.length ;++i) {
			if(!isEnglishLetter(charArr[i]) && !isDigit(charArr[i]) && !isNameRestrictedChar(charArr[i])) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * This method checks if the provided password is valid.
	 * A valid password is at list characters and must contains both digits and letters.
	 * @param str - input of the password as a string.
	 * @return If the password input is valid or not.
	 */
	public static boolean isValidPasswordFormat(String str) {
		
		boolean hasDigit = false;
		boolean hasLetter = false;
		char[] charArr=str.toCharArray();
		
		// Checking for valid password length.
		if(charArr.length < 8 || charArr.length >45) {
			return false;
		}
		
		for(int i = 0; i <charArr.length ;++i) {
			
			// Checking if there is a digit in the password.
			if(isDigit(charArr[i]) && !hasDigit) {
				hasDigit = true;
			}
			
			// Checking if there is a letter in the password.
			if(isEnglishLetter(charArr[i]) && !hasLetter) {
				hasLetter = true;
			}
			
			// Checking if the password contains characters that are not letters or digits.
			if(!isDigit(charArr[i]) && !isEnglishLetter(charArr[i])){
				return false;
			}
			
		}
		
		// Checking if the password has both digits and letters.
		if(!hasLetter || !hasDigit) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * This method checks if the provided price is valid.
	 * @param price - the price as a long.
	 * @return If the price is above 0.5 (minimum price for coupon).
	 */
	public static boolean isValidPrice(double price) {
		
		return price > 0.5;
		
	}
	
	/**
	 * This method checks if the provided amount is valid.
	 * @param amount - the amount as an integer.
	 * @return If the amount is above 0.
	 */
	public static boolean isValidAmount(int amount) {
		
		return amount >= 0;
		
	}
	
	/**
	 * This method checks if the provided message is valid.
	 * @param str - the message as a string.
	 * @return If the message has more then 100 characters.
	 */
	public static boolean isValidMessageFormat(String str) {
		
		return str.length() >= 100;
		
	}
	
	/**
	 * Validating the relation of start date of a coupon and the end date.
	 * @param couponStartDate - String of the start date
	 * @param couponEndDate - String of the end date
	 * @return if the start date of a coupon is after the end date.
	 */
	public static boolean isStartEndDateMiss(String couponStartDate, String couponEndDate) {

		GregorianCalendar startDate = DateUtils.strToDateConverter(couponStartDate);
		GregorianCalendar endDate = DateUtils.strToDateConverter(couponEndDate);
		
		return startDate.after(endDate);
	}
	
	/**
	 * Validating the start date.
	 * @param couponStartDate - String of the start date
	 * @return if the input of the start date already passed.
	 */
	public static boolean isStartDateAlreadyPassed(String couponStartDate) {

		GregorianCalendar startDate = DateUtils.strToDateConverter(couponStartDate);
		
		return startDate.before(new GregorianCalendar());
	}
	
	/**
	 * Validating if the user can request an action.
	 * @param request - the request from the client.
	 * @param requestId - Id of the request.
	 * @throws ApplicationException
	 */
	public static void ValidateUser(HttpServletRequest request, long requestId) throws ApplicationException {
		String userType = (String) request.getAttribute("userType");
		String userIDstr = (String) request.getAttribute("userID");
		Long userID = null;
		if(userIDstr == null || userType == null) {
			throw new ApplicationException(ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime()
					+" System error, problem with cookies.");
		}

		userID = Long.parseLong(userIDstr);

		if ( !userType.equals(UserType.ADMIN.name()) || userID != requestId) {
			throw new ApplicationException(ErrorType.UNAUTHORIZED_ACTION, DateUtils.getCurrentDateAndTime()
					+" Unauthorized action.");
		} 
	}
	


	public static Long validateAndGetetCustomerID(HttpServletRequest request) throws ApplicationException {
		String userType = (String) request.getAttribute("userType");
		String userIDstr = (String) request.getAttribute("userID");
		Long userID = null;

		if(userType.equals(UserType.CUSTOMER.name())) {
			throw new ApplicationException(ErrorType.UNAUTHORIZED_ACTION, DateUtils.getCurrentDateAndTime()
					+" Unauthorized action.");
		}

		if(userIDstr == null) {
			throw new ApplicationException(ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime()
					+" System error, problem with cookies.");
		}

		userID = Long.valueOf(userIDstr);
		return userID;
	}
	
	
	/**
	 * This method checks if the beginning or the end of local part or the domain part of the email are only English letters
	 * or digits.
	 * @param c - tested character.
	 * @return if the character can be at the beginning or the end of local part or the domain part of the email..
	 */
	private static boolean isValidEmailEdge(char c) {
		return isEnglishLetter(c) || isDigit(c) || !isEmailRestrictedChar(c) && !isEmailProhibitedChar(c);
	}
	
	/**
	 * his method checks if the checked character is a prohibited character for the email address.
	 * The prohibited characters are :!#$%&'*+/=?^`{|}~ (),:;<>[\]";
	 * @param c
	 * @return
	 */
	private static boolean isEmailProhibitedChar(char c) {
		
		return c=='!'||c=='#'||c=='$'||c=='%'||c=='&'||c=='\''||c=='*'||c=='+'||c=='/'||
				c=='='||c=='?'||c=='^'||c=='`'||c=='{'||c=='|'||c=='}'||c=='~'||c==' '||
				c=='('||c==')'||c==','||c==':'||c==';'||c=='<'||c=='>'||c=='['||c=='\\'||
				c==']'||c=='"';
		
	}
	
	/**
	 * This method checks if character is a restricted character.
	 * For the email input, the period('.'), underscore ('_') and the hyphen('-') are permitted as long as they are
	 * not at the beginning or the end of local part or the domain part of the email.
	 * @param c - tested character.
	 * @return if the character is a restricted character for the name string.
	 */
	private static boolean isEmailRestrictedChar(char c) {
		
		return c=='.'||c=='_'||c=='-';
		
	}
	
//	private static boolean isNameProhibitedChar(char c) {
//		
//		return c=='!'||c=='#'||c=='$'||c=='%'||c=='&'||c=='\''||c=='*'||c=='+'||c=='/'||
//				c=='='||c=='?'||c=='^'||c=='`'||c=='{'||c=='|'||c=='}'||c=='~'||
//				c=='('||c==')'||c==','||c==':'||c==';'||c=='<'||c=='>'||c=='['||c=='\\'||
//				c==']'||c=='_'||c=='"';
//		
//	}

	/**
	 * This method checks if character is a restricted character.
	 * For the name input, the period('.'), spaces(' ') and the hyphen('-') are permitted as long as they are
	 * not at the beginning of the string.
	 * @param c - tested character
	 * @return if the character is a restricted character for the name string.
	 */
	private static boolean isNameRestrictedChar(char c) {
		
		if(c=='.'||c==' '|| c=='-') {
			return true;
		}
		
		return false;
		
	}

	/**
	 * This method checks if character is a English upper or lower case letter.
	 * @param c - tested character
	 * @return if the character is a English upper or lower case letter.
	 */
	private static boolean isEnglishLetter(char c) {
		
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
		
	}
	
	/**
	 * This method checks if character is a number.
	 * @param c - tested character
	 * @return if the character is a number.
	 */
	private static boolean isDigit(char c) {
		
		return (c >= '0' && c <= '9');
		
	}

	

}
