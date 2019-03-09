package com.binarskugga.primitiva.reflection;

import com.binarskugga.primitiva.ClassTools;
import com.binarskugga.primitiva.exception.NotAnnotatedException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class FieldReflector extends Reflector<Field> {

	public FieldReflector(Field f) {
		super(f, ClassTools.of(f.getType()));
	}

	@Override public <A extends Annotation> A getSafeAnnotation(Class<A> aClass) throws NotAnnotatedException {
		if(this.getReflected().isAnnotationPresent(aClass))
			return this.getReflected().getAnnotation(aClass);
		else
			throw new NotAnnotatedException();
	}

}
