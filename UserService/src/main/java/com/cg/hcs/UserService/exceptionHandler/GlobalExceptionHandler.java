package com.cg.hcs.UserService.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cg.hcs.UserService.exception.NoValueFoundException;
import com.cg.hcs.UserService.exception.NotPossibleException;


@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value= NoValueFoundException.class)
	public ResponseEntity<ApiError> handlingNoValueFoundException(NoValueFoundException e)
	{
		ApiError error=new ApiError();
		error.setException(" "+ e.getMessage());
		HttpStatus status=HttpStatus.BAD_REQUEST;
		return new ResponseEntity<ApiError>(error, status);
	}
	
	@ExceptionHandler(value= NotPossibleException.class)
	public ResponseEntity<ApiError> handlingNotPossibleException(NotPossibleException e)
	{
		ApiError error=new ApiError();
		error.setException(" "+ e.getMessage());
		HttpStatus status=HttpStatus.BAD_REQUEST;
		return new ResponseEntity<ApiError>(error, status);
	}
}
