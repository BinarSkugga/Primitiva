package com.binarskugga.primitiva.exception;

public class CannotGetFieldException extends Exception {

	public CannotGetFieldException() {
		super();
	}

	public CannotGetFieldException(String message) {
		super(message);
	}

	public CannotGetFieldException(String message, Throwable cause) {
		super(message, cause);
	}

	public CannotGetFieldException(Throwable cause) {
		super(cause);
	}

	protected CannotGetFieldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
