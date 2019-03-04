package com.binarskugga.primitiva.exception;

/**
 * @author Charles Smith (BinarSkugga)
 * NonConversibleTypeException is thrown when a non primitive (boxed or not, array or not), non string (array or not)
 * is given to any of the converters.
 */
public class NonConversibleTypeException extends RuntimeException {

	public NonConversibleTypeException() {
	}

	public NonConversibleTypeException(String message) {
		super(message);
	}

	public NonConversibleTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public NonConversibleTypeException(Throwable cause) {
		super(cause);
	}

	public NonConversibleTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
