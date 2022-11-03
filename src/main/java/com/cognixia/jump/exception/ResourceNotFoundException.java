package com.cognixia.jump.exception;

public class ResourceNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
	
	public ResourceNotFoundException(String resource, String id) {
		super(resource + " was not found with id = " + id);
	}
}
