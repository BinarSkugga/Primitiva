package com.binarskugga.primitiva.exception;

public class NotAnnotatedException extends Exception {

	public NotAnnotatedException() {
	}

	public NotAnnotatedException(String message) {
		super(message);
	}

	public NotAnnotatedException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotAnnotatedException(Throwable cause) {
		super(cause);
	}

	public NotAnnotatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
