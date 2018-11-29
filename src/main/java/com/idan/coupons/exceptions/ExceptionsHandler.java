package com.idan.coupons.exceptions;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idan.coupons.beans.ApplicationError;
import com.idan.coupons.enums.ErrorType;

@ResponseBody
@ControllerAdvice
public class ExceptionsHandler{
	
	private static final Logger logger = LogManager.getLogger(ExceptionsHandler.class);
	
	@ExceptionHandler(ApplicationException.class)
	public ApplicationError handleApplicationException(HttpServletResponse response, ApplicationException exception) {
//		String errorMessage = exception.toString();
		ApplicationError error = new ApplicationError(exception.getType().getNumber(),exception.getType().name(), exception.getMessage());
		if (exception.getType() == ErrorType.INVALID_PARAMETER) {
			error.setInputErrorTypes(exception.getTypes()); 
		}
		if(exception.getType() == ErrorType.SYSTEM_ERROR) {
			logger.error(error.getErrorMessage(), exception);
//			exception.printStackTrace();
		}
		response.setStatus(exception.getType().getNumber());
		return error;

	}

	@ExceptionHandler(Exception.class)
	public ApplicationError handleGeberalException(HttpServletResponse response, Exception exception) {
//		String errorMessage = exception.toString();
		int errorCode = 500;
		ApplicationError error = new ApplicationError(errorCode, ErrorType.SYSTEM_ERROR.name(), exception.getMessage());
		exception.printStackTrace();
		logger.error(error.getErrorMessage(), exception);
		response.setStatus(errorCode);
        return error;
	}

}
