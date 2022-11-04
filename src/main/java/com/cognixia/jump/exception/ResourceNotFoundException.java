package com.cognixia.jump.exception;

public class ResourceNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
	
	public ResourceNotFoundException(String expectedResourceType, Object id) {
		super(expectedResourceType + " was not found with id = " + id);
	}
}
