package com.binarskugga.primitiva.reflection;

import com.binarskugga.primitiva.ClassTools;
import com.binarskugga.primitiva.exception.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class FieldReflector extends Reflector<Field> {

	public FieldReflector(Field f) {
		super(f, ClassTools.of(f.getGenericType()));
	}

	@Override
	public <A extends Annotation> A getSafeAnnotation(Class<A> aClass) throws NotAnnotatedException {
		if(this.getReflected().isAnnotationPresent(aClass))
			return this.getReflected().getAnnotation(aClass);
		else
			throw new NotAnnotatedException();
	}

	public boolean set(Object instance, Object value) {
		return this.set(instance, value, true);
	}

	public boolean set(Object instance, Object value, boolean force) {
		try {
			this.safeSet(instance, value, force);
			return true;
		} catch(Exception ignored) {
			return false;
		}
	}

	public void safeSet(Object instance, Object value) throws CannotSetFieldException {
		this.safeSet(instance, value, true);
	}

	public void safeSet(Object instance, Object value, boolean force) throws CannotSetFieldException {
		try {
			if(force) this.getReflected().setAccessible(true);
			this.getReflected().set(instance, value);
		} catch(Exception e) {
			throw new CannotSetFieldException();
		}
	}

	public Object get(Object instance) {
		return this.get(instance, true);
	}

	public Object get(Object instance, boolean force) {
		return this.get(instance, Object.class, force);
	}

	public <T> T get(Object instance, Class<T> clazz) {
		return this.get(instance, clazz, true);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Object instance, Class<T> clazz, boolean force) {
		try {
			return this.safeGet(instance, clazz, force);
		} catch(Exception ignored) {
			return null;
		}
	}

	public Object safeGet(Object instance) throws CannotGetFieldException {
		return this.safeGet(instance, Object.class, true);
	}

	public Object safeGet(Object instance, boolean force) throws CannotGetFieldException {
		return this.safeGet(instance, Object.class, force);
	}

	public <T> T safeGet(Object instance, Class<T> clazz) throws CannotGetFieldException {
		return this.safeGet(instance, clazz, true);
	}

	@SuppressWarnings("unchecked")
	public <T> T safeGet(Object instance, Class<T> clazz, boolean force) throws CannotGetFieldException {
		try {
			if(force) this.getReflected().setAccessible(true);
			return clazz.cast(this.getReflected().get(instance));
		} catch(Exception e) {
			throw new CannotGetFieldException();
		}
	}

}
