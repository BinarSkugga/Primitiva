package com.binarskugga.primitiva.reflection;

import com.binarskugga.primitiva.exception.NotAnnotatedException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class TypeReflector extends Reflector<Type> {

	public TypeReflector(Type o) {
		super(o, o);
	}

	public TypeReflector(Class o) {
		super(o, o);
	}

	@Override public String getName() {
		return this.get().getName();
	}

	@Override
	public int getReflectedModifiers() {
		return this.get().getModifiers();
	}

	@Override
	public <A extends Annotation> A getSafeReflectedAnnotation(Class<A> aClass) throws NotAnnotatedException {
		if(this.get().isAnnotationPresent(aClass))
			return this.get().getAnnotation(aClass);
		else
			throw new NotAnnotatedException();
	}

}
