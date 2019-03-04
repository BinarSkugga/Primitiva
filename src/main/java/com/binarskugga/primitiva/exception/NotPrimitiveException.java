package com.binarskugga.primitiva.exception;

public class NotPrimitiveException extends RuntimeException {

	public NotPrimitiveException() {
	}

	public NotPrimitiveException(String message) {
		super(message);
	}

	public NotPrimitiveException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotPrimitiveException(Throwable cause) {
		super(cause);
	}

	public NotPrimitiveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
