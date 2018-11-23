package com.idan.coupons.beans;

import java.util.List;
import com.idan.coupons.enums.InputErrorType;



public class ApplicationError {

	private Integer errorCode;
	private String errorType;
	private String errorMessage;
	private List<InputErrorType> inputErrorTypes;

	public ApplicationError() {
		super();
	}

	public ApplicationError(Integer errorCode, String errorType, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorType = errorType;
		this.errorMessage = errorMessage;
	}

	public ApplicationError(Integer errorCode, String errorMessage, List<InputErrorType> inputErrorTypes) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.inputErrorTypes = inputErrorTypes;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public List<InputErrorType> getInputErrorTypes() {
		return inputErrorTypes;
	}

	public void setInputErrorTypes(List<InputErrorType> inputErrorTypes) {
		this.inputErrorTypes = inputErrorTypes;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}



	@Override
	public String toString() {
		return "ApplicationError [errorCode=" + errorCode + ", errorMessage=" + errorMessage + ", inputErrorTypes="
				+ inputErrorTypes + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((errorCode == null) ? 0 : errorCode.hashCode());
		result = prime * result + ((errorMessage == null) ? 0 : errorMessage.hashCode());
		result = prime * result + ((errorType == null) ? 0 : errorType.hashCode());
		result = prime * result + ((inputErrorTypes == null) ? 0 : inputErrorTypes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicationError other = (ApplicationError) obj;
		if (errorCode == null) {
			if (other.errorCode != null)
				return false;
		} else if (!errorCode.equals(other.errorCode))
			return false;
		if (errorMessage == null) {
			if (other.errorMessage != null)
				return false;
		} else if (!errorMessage.equals(other.errorMessage))
			return false;
		if (errorType == null) {
			if (other.errorType != null)
				return false;
		} else if (!errorType.equals(other.errorType))
			return false;
		if (inputErrorTypes == null) {
			if (other.inputErrorTypes != null)
				return false;
		} else if (!inputErrorTypes.equals(other.inputErrorTypes))
			return false;
		return true;
	}
	
	



}
