package com.cognixia.jump.exception;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

// Before the controller gets a response, this gives advice on how to construct that response
@ControllerAdvice
public class GlobalExceptionHandler {

	// which exception to look for
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFound(ResourceNotFoundException ex, WebRequest request) {

		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));

		return ResponseEntity.status(404).body(errorDetails);
	}

	// Validation exception thrown when data doesn't fit model-variable validation annotations
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> methodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {

		StringBuilder errors = new StringBuilder("");
		for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
			errors.append("[" + fe.getField() + " : " + fe.getDefaultMessage() + "]; ");
		}

		ErrorDetails errorDetails = new ErrorDetails(new Date(), errors.toString(), request.getDescription(false));

		return ResponseEntity.status(400).body(errorDetails);

	}

}
