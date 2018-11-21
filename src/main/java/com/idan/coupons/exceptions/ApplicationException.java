package com.idan.coupons.exceptions;

import java.util.List;

//import java.sql.SQLException;

import com.idan.coupons.enums.ErrorType;
import com.idan.coupons.enums.InputErrorType;

public class ApplicationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6259848867338873175L;
//	private SQLException e;
	private ErrorType type;
	private List<InputErrorType> inputErrorTypes;
	
	
	// a way to later extract the reason for input error
//			System.out.println(num & 2);
//			System.out.println(num & 16);
	
	
	
	public ErrorType getType() {
		return type;
	}
	
	
	
	public ApplicationException(ErrorType type, String massage) {
		super(massage);
		this.type = type;
	}
	public ApplicationException(Exception e, ErrorType type, String massage) {
		super(massage, e);
		this.type = type;
	}
	
	public ApplicationException(ErrorType type, String massage, List<InputErrorType> types) {
		super(massage);
		this.type = type;
		this.inputErrorTypes = types;
	}

	public List<InputErrorType> getTypes() {
		return inputErrorTypes;
	}

	
	
	
	
	
	
}
