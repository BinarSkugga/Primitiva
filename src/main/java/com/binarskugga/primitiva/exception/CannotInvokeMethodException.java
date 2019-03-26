package com.binarskugga.primitiva.exception;

public class CannotInvokeMethodException extends Exception {

	public CannotInvokeMethodException() {
		super();
	}

	public CannotInvokeMethodException(String message) {
		super(message);
	}

	public CannotInvokeMethodException(String message, Throwable cause) {
		super(message, cause);
	}

	public CannotInvokeMethodException(Throwable cause) {
		super(cause);
	}

	protected CannotInvokeMethodException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
