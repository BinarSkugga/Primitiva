package com.binarskugga.primitiva.exception;

/**
 * @author Charles Smith (BinarSkugga)
 * NonConversibleTypeException is thrown when a non primitive (boxed or not, array or not), non string (array or not)
 * is given to any of the converters.
 */
public class NonPrimitiveTypeException extends RuntimeException {

	public NonPrimitiveTypeException() {
	}

	public NonPrimitiveTypeException(String message) {
		super(message);
	}

	public NonPrimitiveTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public NonPrimitiveTypeException(Throwable cause) {
		super(cause);
	}

	public NonPrimitiveTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
