package com.binarskugga.primitiva.exception;

public class CannotSetFieldException extends Exception {

	public CannotSetFieldException() {
		super();
	}

	public CannotSetFieldException(String message) {
		super(message);
	}

	public CannotSetFieldException(String message, Throwable cause) {
		super(message, cause);
	}

	public CannotSetFieldException(Throwable cause) {
		super(cause);
	}

	protected CannotSetFieldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
