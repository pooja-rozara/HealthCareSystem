package com.cg.hcs.appointmentservice.exceptionHandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cg.hcs.appointmentservice.exception.NoValueFoundException;
import com.cg.hcs.appointmentservice.exception.NotPossibleException;

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
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
}
