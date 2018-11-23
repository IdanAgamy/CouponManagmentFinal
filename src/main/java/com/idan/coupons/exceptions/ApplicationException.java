package com.idan.coupons.exceptions;

import java.util.List;

//import java.sql.SQLException;

import com.idan.coupons.enums.ErrorType;
import com.idan.coupons.enums.InputErrorType;

public class ApplicationException extends Exception {

	private static final long serialVersionUID = -6259848867338873175L;
	private ErrorType type;
	private List<InputErrorType> inputErrorTypes;
	
public ErrorType getType() {
		return type;
	}
	
	/**
	 * Contractor for self thrown exception.
	 * @param type - type of error.
	 * @param massage - the massage of what happened.
	 */
	public ApplicationException(ErrorType type, String massage) {
		super(massage);
		this.type = type;
	}
	
	/**
	 * Contractor for containing other types of exceptions.
	 * @param e - exceptions.
	 * @param type - type of error.
	 * @param massage - the massage of what happened.
	 */
	public ApplicationException(Exception e, ErrorType type, String massage) {
		super(massage, e);
		this.type = type;
	}
	
	/**
	 * Contractor for containing more then one type of input error.
	 * @param type
	 * @param massage
	 * @param types
	 */
	public ApplicationException(ErrorType type, String massage, List<InputErrorType> types) {
		super(massage);
		this.type = type;
		this.inputErrorTypes = types;
	}

	public List<InputErrorType> getTypes() {
		return inputErrorTypes;
	}

	
	
	
	
	
	
}
