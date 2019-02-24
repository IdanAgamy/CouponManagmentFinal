package com.idan.coupons.enums;

public enum ErrorType {
	
	UNAUTHORIZED_ACTION(401),
	GENERAL_ERROR(602),
	INVALID_PARAMETER(603),
	SYSTEM_ERROR(605),	
	NAME_IS_ALREADY_EXISTS(608), 
	EMAIL_IS_ALREADY_EXISTS(609),
	BAD_INPUT(621),
	COOKIES_LOST(622);
	
	private int number;
	
	private ErrorType(int number) {
		this.number = number;
	}
	
	public int getNumber() {
		return number;
	}
	
	public static ErrorType fromString(final String s) {
		return ErrorType.valueOf(s);
	}
	
}
