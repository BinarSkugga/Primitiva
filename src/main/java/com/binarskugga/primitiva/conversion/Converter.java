package com.binarskugga.primitiva.conversion;

import com.binarskugga.primitiva.ClassTools;
import lombok.Getter;

public abstract class Converter<T> {

	@Getter private ClassTools inClass;

	public Converter(Class<T> inClass) {
		this.inClass = ClassTools.of(inClass);
	}

	public abstract <R> R convertTo(T value, Class<R> returnClass);

}
