package com.binarskugga.primitiva.reflection;

import com.binarskugga.primitiva.ClassTools;
import com.binarskugga.primitiva.exception.NotAnnotatedException;
import com.binarskugga.primitiva.exception.ReflectiveConstructFailedException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TypeReflector<T> extends Reflector<Class<T>> {

	@SuppressWarnings("unchecked")
	public TypeReflector(Object o) {
		super(ClassTools.of(o).get(), ClassTools.of(o));
	}

	@Override public <A extends Annotation> A getSafeAnnotation(Class<A> aClass) throws NotAnnotatedException {
		if(this.getReflected().isAnnotationPresent(aClass))
			return this.getReflected().getAnnotation(aClass);
		else
			throw new NotAnnotatedException();
	}

	public T create(Object... arguments) {
		try {
			return this.construct(arguments);
		} catch (ReflectiveConstructFailedException e) {
			return null;
		}
	}

	public T safeCreate(Object... arguments) throws ReflectiveConstructFailedException {
		return this.construct(arguments);
	}

	private T construct(Object... arguments) throws ReflectiveConstructFailedException {
		try {
			if(!this.isPrimitiveOrBoxed() && !this.isPrimitiveOrBoxedArray()) {
				Constructor<T> constructor;
				if (arguments == null || arguments.length == 0)
					constructor = this.getReflected().getConstructor();
				else {
					constructor = this.getReflected().getConstructor(Arrays.asList(arguments).stream()
							.map(Object::getClass).collect(Collectors.toList()).toArray(new Class[arguments.length]));
				}

				constructor.setAccessible(true);
				return constructor.newInstance(arguments);
			} else {
				throw new ReflectiveConstructFailedException();
			}
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
			throw new ReflectiveConstructFailedException(e);
		}
	}

}
