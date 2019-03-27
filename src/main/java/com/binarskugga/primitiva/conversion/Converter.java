package com.binarskugga.primitiva.conversion;

import com.binarskugga.primitiva.ClassTools;

public abstract class Converter<T> extends ClassTools<T> {

	public Converter(Class<T> inClass) {
		super(inClass);
	}

	public abstract <R> R convertTo(T value, Class<R> returnClass);

}
