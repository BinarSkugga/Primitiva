package com.binarskugga.primitiva.reflection;

import com.binarskugga.primitiva.ClassTools;
import com.binarskugga.primitiva.Primitiva;
import com.binarskugga.primitiva.exception.CannotGetFieldException;
import com.binarskugga.primitiva.exception.CannotSetFieldException;
import com.binarskugga.primitiva.exception.NotAnnotatedException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

public class FieldReflector extends Reflector<Field> {

	public FieldReflector(Field f) {
		super(f, f.getGenericType());
	}

	@Override public String getName() {
		return this.getReflected().getName();
	}

	public void setAccessible(boolean accessible) {
		this.getReflected().setAccessible(accessible);
	}

	public boolean isAccessible(boolean accessible) {
		return this.getReflected().isAccessible();
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

	public final Field getEquivalentIn(Class<?> clazz) {
		ClassTools<?> tools = ClassTools.of(clazz);
		List<Field> fs = tools.getFields(f -> {
			FieldReflector reflector = Primitiva.Reflect.field(f);
			return reflector.isSubOf(this.get()) && reflector.getName().equals(this.getName());
		});
		return fs.size() > 0 ? fs.get(0) : null;
	}

}
