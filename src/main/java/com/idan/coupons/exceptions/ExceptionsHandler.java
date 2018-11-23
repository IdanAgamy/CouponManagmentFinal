package com.idan.coupons.exceptions;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.idan.coupons.enums.ErrorType;

@ControllerAdvice
public class ExceptionsHandler{

	@ExceptionHandler(ApplicationException.class)
	public void handleApplicationException(HttpServletResponse response, ApplicationException exception) {
		String errorMessage = exception.toString();
		if(exception.getType() == ErrorType.SYSTEM_ERROR) {
			//TODO implement logger.
			exception.printStackTrace();
		}
		response.setStatus(exception.getType().getNumber());
		response.setHeader("errorMessage", errorMessage);


	}

	@ExceptionHandler(Exception.class)
	public void handleGeberalException(HttpServletResponse response, Exception exception) {
		String errorMessage = exception.toString();
		exception.printStackTrace();
		//TODO implement logger.
		response.setStatus(500);
        response.setHeader("errorMessage", errorMessage);
	}

}
