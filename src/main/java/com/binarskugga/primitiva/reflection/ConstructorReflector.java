package com.binarskugga.primitiva.reflection;

import com.binarskugga.primitiva.ClassTools;
import com.binarskugga.primitiva.exception.NotAnnotatedException;
import com.binarskugga.primitiva.exception.ReflectiveConstructFailedException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

public class ConstructorReflector extends Reflector<Constructor> {

	public ConstructorReflector(Constructor r) {
		super(r, ClassTools.of(r.getDeclaringClass()));
	}

	public void setAccessible(boolean accessible) {
		this.getReflected().setAccessible(accessible);
	}

	public boolean isAccessible(boolean accessible) {
		return this.getReflected().isAccessible();
	}

	@Override public int getModifiers() {
		return this.getReflected().getModifiers();
	}

	@SuppressWarnings("unchecked")
	@Override public <A extends Annotation> A getSafeAnnotation(Class<A> aClass) throws NotAnnotatedException {
		if(this.getReflected().isAnnotationPresent(aClass))
			return (A) this.getReflected().getAnnotation(aClass);
		else
			throw new NotAnnotatedException();
	}

	public Object invoke(Object... arguments) {
		return this.invoke(Object.class, arguments);
	}

	public <T> T invoke(Class<T> returnType, Object... arguments) {
		try {
			return this.safeInvoke(returnType, arguments);
		} catch(Exception e) {
			return null;
		}
	}

	public Object safeInvoke(Object... arguments) throws ReflectiveConstructFailedException {
		return this.safeInvoke(Object.class, arguments);
	}

	public <T> T safeInvoke(Class<T> returnType, Object... arguments) throws ReflectiveConstructFailedException {
		try {
			return returnType.cast(this.getReflected().newInstance(arguments));
		} catch(Exception e) {
			throw new ReflectiveConstructFailedException();
		}
	}

}
