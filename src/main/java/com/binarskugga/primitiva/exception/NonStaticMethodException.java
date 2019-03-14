package com.binarskugga.primitiva.exception;

public class NonStaticMethodException extends Exception {

	public NonStaticMethodException() {
		super();
	}

	public NonStaticMethodException(String message) {
		super(message);
	}

	public NonStaticMethodException(String message, Throwable cause) {
		super(message, cause);
	}

	public NonStaticMethodException(Throwable cause) {
		super(cause);
	}

	protected NonStaticMethodException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
