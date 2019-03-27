package com.binarskugga.primitiva.reflection;

import com.binarskugga.primitiva.ClassTools;
import lombok.Getter;

public abstract class Reflector<T> extends ClassTools<T> {

	@Getter private T reflected;

	protected Reflector(T r, Object type) {
		super(type);
		this.reflected = r;
	}

	public abstract String getName();

}
