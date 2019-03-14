package com.binarskugga.primitiva.reflection;

import com.binarskugga.primitiva.ClassTools;
import com.binarskugga.primitiva.Primitiva;
import com.binarskugga.primitiva.exception.NotAnnotatedException;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Collection;
import java.util.Map;

public abstract class Reflector<T> {

	@Getter private T reflected;
	@Getter private ClassTools tools;

	protected Reflector(T r, ClassTools tools) {
		this.reflected = r;
		this.tools = tools;
	}

	public abstract int getModifiers();

	public final boolean isStatic() {
		return Modifier.isStatic(this.getModifiers());
	}
	public final boolean isFinal() {
		return Modifier.isFinal(this.getModifiers());
	}
	public final boolean isNative() {
		return Modifier.isNative(this.getModifiers());
	}
	public final boolean isStrict() {
		return Modifier.isStrict(this.getModifiers());
	}
	public final boolean isVolatile() {
		return Modifier.isVolatile(this.getModifiers());
	}
	public final boolean isSynchronized() {
		return Modifier.isSynchronized(this.getModifiers());
	}
	public final boolean isTransient() {
		return Modifier.isTransient(this.getModifiers());
	}

	public final boolean isAbstract() {
		return Modifier.isAbstract(this.getModifiers());
	}
	public final boolean isInterface() {
		return Modifier.isInterface(this.getModifiers());
	}

	public final boolean isPrivate() {
		return Modifier.isPrivate(this.getModifiers());
	}
	public final boolean isProtected() {
		return Modifier.isProtected(this.getModifiers());
	}
	public final boolean isPublic() {
		return Modifier.isPublic(this.getModifiers());
	}

	public abstract <A extends Annotation> A getSafeAnnotation(Class<A> aClass) throws NotAnnotatedException;

	public final <A extends Annotation> A getAnnotation(Class<A> aClass) {
		try {
			return this.getSafeAnnotation(aClass);
		} catch (Exception e) {
			return null;
		}
	}

	public final boolean isPrimitive() {
		return this.tools.isPrimitive();
	}

	public final boolean isPrimitiveArray() {
		return this.tools.isPrimitiveArray();
	}

	public final boolean isNonNumericPrimitive() {
		return this.tools.isOneOf(boolean.class, char.class);
	}

	@SuppressWarnings("unchecked")
	public final boolean isNonNumericPrimitiveArray() {
		if(!this.isArray()) return false;
		return Primitiva.Reflection.ofType(this.tools.getArrayType()).isNonNumericPrimitive();
	}

	public final boolean isNumericPrimitive() {
		return this.tools.isOneOf(double.class, float.class, long.class, int.class, short.class, byte.class);
	}

	@SuppressWarnings("unchecked")
	public final boolean isNumericPrimitiveArray() {
		if(!this.isArray()) return false;
		return Primitiva.Reflection.ofType(this.tools.getArrayType()).isNumericPrimitive();
	}

	public final boolean isIntegerPrimitive() {
		return this.tools.isOneOf(long.class, int.class, short.class, byte.class);
	}

	@SuppressWarnings("unchecked")
	public final boolean isIntegerPrimitiveArray() {
		if(!this.isArray()) return false;
		return Primitiva.Reflection.ofType(this.tools.getArrayType()).isIntegerPrimitive();
	}

	public final boolean isFloatingPrimitive() {
		return this.tools.isOneOf(double.class, float.class);
	}

	@SuppressWarnings("unchecked")
	public final boolean isFloatingPrimitiveArray() {
		if(!this.isArray()) return false;
		return Primitiva.Reflection.ofType(this.tools.getArrayType()).isFloatingPrimitive();
	}

	public final boolean isNonNumericPrimitiveOrBoxed() {
		return this.isNonNumericPrimitive() || this.tools.isOneOf(Boolean.class, Character.class);
	}

	@SuppressWarnings("unchecked")
	public final boolean isNonNumericPrimitiveOrBoxedArray() {
		if(!this.isArray()) return false;
		return Primitiva.Reflection.ofType(this.tools.getArrayType()).isNonNumericPrimitiveOrBoxed();
	}

	public final boolean isPrimitiveOrBoxed() {
		return this.tools.isPrimitiveOrBoxed();
	}

	public final boolean isPrimitiveOrBoxedArray() {
		return this.tools.isPrimitiveOrBoxedArray();
	}

	public final boolean isNumericPrimitiveOrBoxed() {
		return this.isNumericPrimitive() || this.tools.isSubOf(Number.class);
	}

	@SuppressWarnings("unchecked")
	public final boolean isNumericPrimitiveOrBoxedArray() {
		if(!this.isArray()) return false;
		return Primitiva.Reflection.ofType(this.tools.getArrayType()).isNumericPrimitiveOrBoxed();
	}

	public final boolean isIntegerPrimitiveOrBoxed() {
		return this.isIntegerPrimitive() || this.tools.isOneOf(Long.class, Integer.class, Short.class, Byte.class);
	}

	@SuppressWarnings("unchecked")
	public final boolean isIntegerPrimitiveOrBoxedArray() {
		if(!this.isArray()) return false;
		return Primitiva.Reflection.ofType(this.tools.getArrayType()).isIntegerPrimitiveOrBoxed();
	}

	public final boolean isFloatingPrimitiveOrBoxed() {
		return this.isFloatingPrimitive() || this.tools.isOneOf(Double.class, Float.class);
	}

	@SuppressWarnings("unchecked")
	public final boolean isFloatingPrimitiveOrBoxedArray() {
		if(!this.isArray()) return false;
		return Primitiva.Reflection.ofType(this.tools.getArrayType()).isFloatingPrimitiveOrBoxed();
	}

	public final boolean isPrimitiveBoxed() {
		return this.isPrimitiveOrBoxed() && !this.isPrimitive();
	}

	public final boolean isPrimitiveBoxedArray() {
		return this.isPrimitiveOrBoxedArray() && !this.isPrimitiveArray();
	}

	public final boolean isNonNumericPrimitiveBoxed() {
		return this.isNonNumericPrimitiveOrBoxed() && !this.isNonNumericPrimitive();
	}

	@SuppressWarnings("unchecked")
	public final boolean isNonNumericPrimitiveBoxedArray() {
		if(!this.isArray()) return false;
		return Primitiva.Reflection.ofType(this.tools.getArrayType()).isNonNumericPrimitiveBoxed();
	}

	public final boolean isNumericPrimitiveBoxed() {
		return this.isNumericPrimitiveOrBoxed() && !this.isNumericPrimitive();
	}

	@SuppressWarnings("unchecked")
	public final boolean isNumericPrimitiveBoxedArray() {
		if(!this.isArray()) return false;
		return Primitiva.Reflection.ofType(this.tools.getArrayType()).isNumericPrimitiveBoxed();
	}

	public final boolean isIntegerPrimitiveBoxed() {
		return this.isIntegerPrimitiveOrBoxed() && !this.isIntegerPrimitive();
	}

	@SuppressWarnings("unchecked")
	public final boolean isIntegerPrimitiveBoxedArray() {
		if(!this.isArray()) return false;
		return Primitiva.Reflection.ofType(this.tools.getArrayType()).isIntegerPrimitiveBoxed();
	}

	public final boolean isFloatingPrimitiveBoxed() {
		return this.isFloatingPrimitiveOrBoxed() && !this.isFloatingPrimitive();
	}

	@SuppressWarnings("unchecked")
	public final boolean isFloatingPrimitiveBoxedArray() {
		if(!this.isArray()) return false;
		return Primitiva.Reflection.ofType(this.tools.getArrayType()).isFloatingPrimitiveBoxed();
	}

	public final boolean isArray() {
		return this.tools.isArray();
	}

	public final boolean isCollection() {
		return this.tools.isSubOf(Collection.class);
	}

	public final boolean isMap() {
		return this.tools.isSubOf(Map.class);
	}

}
