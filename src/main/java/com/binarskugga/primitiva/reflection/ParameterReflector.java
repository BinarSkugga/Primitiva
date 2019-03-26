package com.binarskugga.primitiva.reflection;

import com.binarskugga.primitiva.*;
import com.binarskugga.primitiva.exception.*;

import java.lang.annotation.*;
import java.lang.reflect.*;

public class ParameterReflector extends Reflector<Parameter> {

	public ParameterReflector(Parameter r) {
		super(r, ClassTools.of(r.getParameterizedType()));
	}

	@Override
	public int getModifiers() {
		return this.getReflected().getModifiers();
	}

	@Override
	public <A extends Annotation> A getSafeAnnotation(Class<A> aClass) throws NotAnnotatedException {
		if(this.getReflected().isAnnotationPresent(aClass))
			return this.getReflected().getAnnotation(aClass);
		else
			throw new NotAnnotatedException();
	}
}
