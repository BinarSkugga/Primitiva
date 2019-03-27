package com.binarskugga.primitiva.reflection;

import com.binarskugga.primitiva.exception.NotAnnotatedException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

public class ParameterReflector extends Reflector<Parameter> {

	public ParameterReflector(Parameter r) {
		super(r, r.getParameterizedType());
	}

	@Override public String getName() {
		return this.getReflected().getName();
	}

	@Override
	public int getReflectedModifiers() {
		return this.getReflected().getModifiers();
	}

	@Override
	public <A extends Annotation> A getSafeReflectedAnnotation(Class<A> aClass) throws NotAnnotatedException {
		if(this.getReflected().isAnnotationPresent(aClass))
			return this.getReflected().getAnnotation(aClass);
		else
			throw new NotAnnotatedException();
	}
}
