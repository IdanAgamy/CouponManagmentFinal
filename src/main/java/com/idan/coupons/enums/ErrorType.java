package com.idan.coupons.enums;

public enum ErrorType {
	
	UNAUTHORIZED_ACTION(401),
	GENERAL_ERROR(602),
	INVALID_PARAMETER(603),
	INVALID_LOGIN(604),
	SYSTEM_ERROR(605),	
	SERVER_RESTART(606),
	SESSION_TIMEOUT(607), 
	NAME_IS_ALREADY_EXISTS(608), 
	EMAIL_IS_ALREADY_EXISTS(609),
	NO_RETURN_OBJECT(620), 
	BAD_INPUT(621);
	
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
