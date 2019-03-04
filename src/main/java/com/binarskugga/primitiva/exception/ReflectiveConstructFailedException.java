package com.binarskugga.primitiva.exception;

public class ReflectiveConstructFailedException extends Exception {

	public ReflectiveConstructFailedException() {
	}

	public ReflectiveConstructFailedException(String message) {
		super(message);
	}

	public ReflectiveConstructFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReflectiveConstructFailedException(Throwable cause) {
		super(cause);
	}

	public ReflectiveConstructFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
