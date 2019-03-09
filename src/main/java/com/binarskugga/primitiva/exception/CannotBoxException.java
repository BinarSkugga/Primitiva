package com.binarskugga.primitiva.exception;

public class CannotBoxException extends Exception {

	public CannotBoxException() {
	}

	public CannotBoxException(String message) {
		super(message);
	}

	public CannotBoxException(String message, Throwable cause) {
		super(message, cause);
	}

	public CannotBoxException(Throwable cause) {
		super(cause);
	}

	public CannotBoxException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
