package com.binarskugga.primitiva.reflection;

import com.binarskugga.primitiva.exception.CannotInvokeMethodException;
import com.binarskugga.primitiva.exception.NonStaticMethodException;
import com.binarskugga.primitiva.exception.NotAnnotatedException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MethodReflector extends Reflector<Method> {

	public MethodReflector(Method r) {
		super(r, r.getGenericReturnType());
	}

	public void setAccessible(boolean accessible) {
		this.getReflected().setAccessible(accessible);
	}

	public boolean isAccessible(boolean accessible) {
		return this.getReflected().isAccessible();
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

	public Object staticInvoke(Object... arguments) {
		if(!Modifier.isStatic(this.getReflected().getModifiers())) return null;
		try {
			return this.safeStaticInvoke(arguments);
		} catch(Exception e) {
			return null;
		}
	}

	public <T> T staticInvoke(Class<T> returnType, Object... arguments) {
		if(!Modifier.isStatic(this.getReflected().getModifiers())) return null;
		try {
			return this.safeStaticInvoke(returnType, arguments);
		} catch(Exception e) {
			return null;
		}
	}

	public Object invoke(Object owner, Object... arguments) {
		try {
			return this.safeInvoke(owner, arguments);
		} catch(Exception e) {
			return null;
		}
	}

	public <T> T invoke(Class<T> returnType, Object owner, Object... arguments) {
		try {
			return this.safeInvoke(returnType, owner, arguments);
		} catch(Exception e) {
			return null;
		}
	}

	public Object safeStaticInvoke(Object... arguments) throws CannotInvokeMethodException, NonStaticMethodException {
		if(!Modifier.isStatic(this.getReflected().getModifiers())) throw new NonStaticMethodException();
		return this.safeInvoke(null, arguments);
	}

	public <T> T safeStaticInvoke(Class<T> returnType, Object... arguments) throws CannotInvokeMethodException, NonStaticMethodException {
		if(!Modifier.isStatic(this.getReflected().getModifiers())) throw new NonStaticMethodException();
		return this.safeInvoke(returnType, null, arguments);
	}

	public Object safeInvoke(Object owner, Object... arguments) throws CannotInvokeMethodException {
		return this.safeInvoke(Object.class, owner, arguments);
	}

	public <T> T safeInvoke(Class<T> returnType, Object owner, Object... arguments) throws CannotInvokeMethodException {
		try {
			return returnType.cast(this.getReflected().invoke(owner, arguments));
		} catch(Exception e) {
			throw new CannotInvokeMethodException();
		}
	}

}
