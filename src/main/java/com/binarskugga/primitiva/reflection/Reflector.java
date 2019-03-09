package com.binarskugga.primitiva.reflection;

import com.binarskugga.primitiva.ClassTools;
import com.binarskugga.primitiva.Primitiva;
import com.binarskugga.primitiva.exception.NotAnnotatedException;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;

public abstract class Reflector<T> {

	@Getter private T reflected;
	@Getter private ClassTools tools;

	protected Reflector(T r, ClassTools tools) {
		this.reflected = r;
		this.tools = tools;
	}

	public abstract <A extends Annotation> A getSafeAnnotation(Class<A> aClass) throws NotAnnotatedException;

	public <A extends Annotation> A getAnnotation(Class<A> aClass) {
		try {
			return this.getSafeAnnotation(aClass);
		} catch (Exception e) {
			return null;
		}
	}

	public boolean isPrimitive() {
		return this.tools.isPrimitive();
	}

	public boolean isPrimitiveArray() {
		return this.tools.isPrimitiveArray();
	}

	public boolean isNonNumericPrimitive() {
		return this.tools.isOneOf(boolean.class, char.class);
	}

	@SuppressWarnings("unchecked")
	public boolean isNonNumericPrimitiveArray() {
		if(!this.isArray()) return false;
		return Primitiva.Reflection.ofClass(this.tools.getArrayType()).isNonNumericPrimitive();
	}

	public boolean isNumericPrimitive() {
		return this.tools.isOneOf(double.class, float.class, long.class, int.class, short.class, byte.class);
	}

	@SuppressWarnings("unchecked")
	public boolean isNumericPrimitiveArray() {
		if(!this.isArray()) return false;
		return Primitiva.Reflection.ofClass(this.tools.getArrayType()).isNumericPrimitive();
	}

	public boolean isIntegerPrimitive() {
		return this.tools.isOneOf(long.class, int.class, short.class, byte.class);
	}

	@SuppressWarnings("unchecked")
	public boolean isIntegerPrimitiveArray() {
		if(!this.isArray()) return false;
		return Primitiva.Reflection.ofClass(this.tools.getArrayType()).isIntegerPrimitive();
	}

	public boolean isFloatingPrimitive() {
		return this.tools.isOneOf(double.class, float.class);
	}

	@SuppressWarnings("unchecked")
	public boolean isFloatingPrimitiveArray() {
		if(!this.isArray()) return false;
		return Primitiva.Reflection.ofClass(this.tools.getArrayType()).isFloatingPrimitive();
	}

	public boolean isNonNumericPrimitiveOrBoxed() {
		return this.isNonNumericPrimitive() || this.tools.isOneOf(Boolean.class, Character.class);
	}

	@SuppressWarnings("unchecked")
	public boolean isNonNumericPrimitiveOrBoxedArray() {
		if(!this.isArray()) return false;
		return Primitiva.Reflection.ofClass(this.tools.getArrayType()).isNonNumericPrimitiveOrBoxed();
	}

	public boolean isPrimitiveOrBoxed() {
		return this.tools.isPrimitiveOrBoxed();
	}

	public boolean isPrimitiveOrBoxedArray() {
		return this.tools.isPrimitiveOrBoxedArray();
	}

	public boolean isNumericPrimitiveOrBoxed() {
		return this.isNumericPrimitive() || this.tools.isSubOf(Number.class);
	}

	@SuppressWarnings("unchecked")
	public boolean isNumericPrimitiveOrBoxedArray() {
		if(!this.isArray()) return false;
		return Primitiva.Reflection.ofClass(this.tools.getArrayType()).isNumericPrimitiveOrBoxed();
	}

	public boolean isIntegerPrimitiveOrBoxed() {
		return this.isIntegerPrimitive() || this.tools.isOneOf(Long.class, Integer.class, Short.class, Byte.class);
	}

	@SuppressWarnings("unchecked")
	public boolean isIntegerPrimitiveOrBoxedArray() {
		if(!this.isArray()) return false;
		return Primitiva.Reflection.ofClass(this.tools.getArrayType()).isIntegerPrimitiveOrBoxed();
	}

	public boolean isFloatingPrimitiveOrBoxed() {
		return this.isFloatingPrimitive() || this.tools.isOneOf(Double.class, Float.class);
	}

	@SuppressWarnings("unchecked")
	public boolean isFloatingPrimitiveOrBoxedArray() {
		if(!this.isArray()) return false;
		return Primitiva.Reflection.ofClass(this.tools.getArrayType()).isFloatingPrimitiveOrBoxed();
	}

	public boolean isPrimitiveBoxed() {
		return this.isPrimitiveOrBoxed() && !this.isPrimitive();
	}

	public boolean isPrimitiveBoxedArray() {
		return this.isPrimitiveOrBoxedArray() && !this.isPrimitiveArray();
	}

	public boolean isNonNumericPrimitiveBoxed() {
		return this.isNonNumericPrimitiveOrBoxed() && !this.isNonNumericPrimitive();
	}

	@SuppressWarnings("unchecked")
	public boolean isNonNumericPrimitiveBoxedArray() {
		if(!this.isArray()) return false;
		return Primitiva.Reflection.ofClass(this.tools.getArrayType()).isNonNumericPrimitiveBoxed();
	}

	public boolean isNumericPrimitiveBoxed() {
		return this.isNumericPrimitiveOrBoxed() && !this.isNumericPrimitive();
	}

	@SuppressWarnings("unchecked")
	public boolean isNumericPrimitiveBoxedArray() {
		if(!this.isArray()) return false;
		return Primitiva.Reflection.ofClass(this.tools.getArrayType()).isNumericPrimitiveBoxed();
	}

	public boolean isIntegerPrimitiveBoxed() {
		return this.isIntegerPrimitiveOrBoxed() && !this.isIntegerPrimitive();
	}

	@SuppressWarnings("unchecked")
	public boolean isIntegerPrimitiveBoxedArray() {
		if(!this.isArray()) return false;
		return Primitiva.Reflection.ofClass(this.tools.getArrayType()).isIntegerPrimitiveBoxed();
	}

	public boolean isFloatingPrimitiveBoxed() {
		return this.isFloatingPrimitiveOrBoxed() && !this.isFloatingPrimitive();
	}

	@SuppressWarnings("unchecked")
	public boolean isFloatingPrimitiveBoxedArray() {
		if(!this.isArray()) return false;
		return Primitiva.Reflection.ofClass(this.tools.getArrayType()).isFloatingPrimitiveBoxed();
	}

	public boolean isArray() {
		return this.tools.isArray();
	}

	public boolean isCollection() {
		return this.tools.isSubOf(Collection.class);
	}

	public boolean isMap() {
		return this.tools.isSubOf(Map.class);
	}

}
