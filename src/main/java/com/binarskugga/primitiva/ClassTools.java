package com.binarskugga.primitiva;

import com.binarskugga.primitiva.exception.CannotBoxException;
import com.binarskugga.primitiva.exception.NotAnnotatedException;
import com.binarskugga.primitiva.exception.ReflectiveConstructFailedException;
import com.binarskugga.primitiva.reflection.ParameterizedTypeImpl;
import org.apache.commons.lang3.NotImplementedException;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ClassTools<T> {

	private Class<T> clazz;
	private Object original;

	public static ClassTools dummy() {
		return new ClassTools(Object.class);
	}

	public static ClassTools of(Object o) {
		return new ClassTools(o);
	}

	@SuppressWarnings("unchecked")
	public static <T> ClassTools<T> of(Class<T> o) {
		return new ClassTools(o);
	}

	protected ClassTools(Object original) {
		this.original = original;
		this.clazz = this.getClassOf(original);
	}

	@SuppressWarnings("unchecked")
	private Class<T> getClassOf(Object o) {
		if(o instanceof String) {
			try {
				return (Class<T>) Class.forName((String) o);
			} catch (ClassNotFoundException e) {
				return null;
			}
		} else if (o instanceof Class) {
			return (Class<T>) o;
		} else if (o instanceof ParameterizedType) {
			Type rawType = ((ParameterizedType) o).getRawType();
			return (Class<T>) rawType;
		} else if (o instanceof GenericArrayType) {
			Type componentType = ((GenericArrayType) o).getGenericComponentType();
			return (Class<T>) Array.newInstance(ClassTools.of(componentType).get(), 0).getClass();
		} else if (o instanceof WildcardType) {
			return ClassTools.of(((WildcardType) o).getUpperBounds()[0]).get();
		} else {
			return null;
		}
	}

	public final Class<T> get() {
		return this.clazz;
	}

	public <A extends Annotation> A getSafeReflectedAnnotation(Class<A> aClass) throws NotAnnotatedException {
		throw new NotImplementedException("");
	}
	public final <A extends Annotation> A getSafeAnnotation(Class<A> aClass) throws NotAnnotatedException {
		if(this.get().isAnnotationPresent(aClass))
			return this.get().getAnnotation(aClass);
		else
			throw new NotAnnotatedException();
	}
	public final <A extends Annotation> A getReflectedAnnotation(Class<A> aClass) {
		try {
			return this.getSafeReflectedAnnotation(aClass);
		} catch (Exception e) {
			return null;
		}
	}
	public final <A extends Annotation> A getAnnotation(Class<A> aClass) {
		try {
			return this.getSafeAnnotation(aClass);
		} catch (Exception e) {
			return null;
		}
	}

	public int getReflectedModifiers() {
		throw new NotImplementedException("");
	}

	public final int getModifiers() {
		return this.get().getModifiers();
	}

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

	public final boolean isReflectedStatic() {
		return Modifier.isStatic(this.getReflectedModifiers());
	}
	public final boolean isReflectedFinal() {
		return Modifier.isFinal(this.getReflectedModifiers());
	}
	public final boolean isReflectedNative() {
		return Modifier.isNative(this.getReflectedModifiers());
	}
	public final boolean isReflectedStrict() {
		return Modifier.isStrict(this.getReflectedModifiers());
	}
	public final boolean isReflectedVolatile() {
		return Modifier.isVolatile(this.getReflectedModifiers());
	}
	public final boolean isReflectedSynchronized() {
		return Modifier.isSynchronized(this.getReflectedModifiers());
	}
	public final boolean isReflectedTransient() {
		return Modifier.isTransient(this.getReflectedModifiers());
	}

	public final boolean isReflectedAbstract() {
		return Modifier.isAbstract(this.getReflectedModifiers());
	}
	public final boolean isReflectedInterface() {
		return Modifier.isInterface(this.getReflectedModifiers());
	}

	public final boolean isReflectedPrivate() {
		return Modifier.isPrivate(this.getReflectedModifiers());
	}
	public final boolean isReflectedProtected() {
		return Modifier.isProtected(this.getReflectedModifiers());
	}
	public final boolean isReflectedPublic() {
		return Modifier.isPublic(this.getReflectedModifiers());
	}

	public final ClassTools box() {
		try {
			return this.safeBox();
		} catch (Exception e) {
			return this;
		}
	}

	public final ClassTools unbox() {
		try {
			return this.safeUnbox();
		} catch (Exception e) {
			return this;
		}
	}

	public final ClassTools safeBox() throws Exception {
		if(this.isPrimitive()) {
			if(this.clazz.equals(boolean.class)) return ClassTools.of(Boolean.class);
			else if(this.clazz.equals(char.class)) return ClassTools.of(Character.class);
			else if(this.clazz.equals(double.class)) return ClassTools.of(Double.class);
			else if(this.clazz.equals(float.class)) return ClassTools.of(Float.class);
			else if(this.clazz.equals(long.class)) return ClassTools.of(Long.class);
			else if(this.clazz.equals(int.class)) return ClassTools.of(Integer.class);
			else if(this.clazz.equals(short.class)) return ClassTools.of(Short.class);
			else if(this.clazz.equals(byte.class)) return ClassTools.of(Byte.class);
			else throw new CannotBoxException();
		} else if(this.isPrimitiveArray()) {
			int depth = this.getArrayDepth();
			Class boxedInner = ClassTools.of(this.getArrayType()).safeBox().get();
			return ClassTools.of(this.getArrayTypeName(boxedInner, depth));
		} else if(this.isBoxedPrimitive() || this.isBoxedPrimitiveArray()) return this;
		else throw new CannotBoxException();
	}

	public final ClassTools safeUnbox() throws Exception {
		if(this.isBoxedPrimitive()) {
			if(this.clazz.equals(Boolean.class)) return ClassTools.of(boolean.class);
			else if(this.clazz.equals(Character.class)) return ClassTools.of(char.class);
			else if(this.clazz.equals(Double.class)) return ClassTools.of(double.class);
			else if(this.clazz.equals(Float.class)) return ClassTools.of(float.class);
			else if(this.clazz.equals(Long.class)) return ClassTools.of(long.class);
			else if(this.clazz.equals(Integer.class)) return ClassTools.of(int.class);
			else if(this.clazz.equals(Short.class)) return ClassTools.of(short.class);
			else if(this.clazz.equals(Byte.class)) return ClassTools.of(byte.class);
			else throw new CannotBoxException();
		} else if(this.isBoxedPrimitiveArray()) {
			int depth = this.getArrayDepth();
			Class unboxedInner = this.getArrayType().safeUnbox().get();
			return this.getArrayTypeName(unboxedInner, depth);
		} else if(this.isPrimitive() || this.isPrimitiveArray()) return this;
		else throw new CannotBoxException();
	}

	public final List<Field> getAllFields() {
		return this.getFields(null);
	}

	public final List<Field> getFields(Predicate<Field> predicate) {
		List<Field> fields = new ArrayList<>();
		Class c = this.clazz;
		while (c.getSuperclass() != null) {
			if(predicate == null) fields.addAll(Arrays.asList(c.getDeclaredFields()));
			else {
				for(Field f : c.getDeclaredFields())
					if(predicate.test(f)) fields.add(f);
			}
			c = c.getSuperclass();
		}
		return fields;
	}

	public final List<Method> getAllMethods() {
		return this.getMethods(null);
	}

	public final List<Method> getMethods(Predicate<Method> predicate) {
		List<Method> methods = new ArrayList<>();
		Class c = this.clazz;
		while (c.getSuperclass() != null) {
			if(predicate == null) methods.addAll(Arrays.asList(c.getDeclaredMethods()));
			else {
				for(Method m : c.getDeclaredMethods())
					if(predicate.test(m)) methods.add(m);
			}
			c = c.getSuperclass();
		}
		return methods;
	}

	public final boolean isParameterizedType() {
		return this.original instanceof ParameterizedType;
	}

	public final Type[] getTypeArguments() {
		if(this.isParameterizedType())
			return ((ParameterizedType) original).getActualTypeArguments();
		return null;
	}

	public final Type getFirstTypeArgument() {
		return getTypeArgument(0);
	}

	public final Type getTypeArgument(int i) {
		if(this.isParameterizedType())
			return ((ParameterizedType) original).getActualTypeArguments()[i];
		return null;
	}

	public final boolean hasEmptyConstructor() {
		try {
			Constructor c = this.clazz.getDeclaredConstructor();
			return c != null;
		} catch(Exception e) {
			return false;
		}
	}

	public final boolean isSuperOf(Object o) {
		Class c = ClassTools.of(o).get();
		return this.clazz.isAssignableFrom(c);
	}

	public final boolean isSuperOf(Object... os) {
		for(Object o : os)
			if(this.isSuperOf(o)) return true;
		return false;
	}

	public final boolean isSubOf(Object o) {
		Class<?> c = ClassTools.of(o).get();
		return c.isAssignableFrom(this.clazz);
	}

	public final boolean isSubOf(Object... os) {
		for(Object o : os)
			if(this.isSubOf(o)) return true;
		return false;
	}

	public final boolean isOneOf(Object... os) {
		for(Object o : os)
			if(this.clazz.equals(ClassTools.of(o).get())) return true;
		return false;
	}

	public final boolean isPrimitive() {
		return this.clazz.isPrimitive();
	}

	public final boolean isBoxedPrimitive() {
		return this.isSubOf(Number.class) || this.isOneOf(Character.class, Boolean.class);
	}

	public final boolean isPrimitiveOrBoxed() {
		return this.isPrimitive() || this.isBoxedPrimitive();
	}

	public final boolean isArray() {
		return this.clazz.isArray();
	}

	public final boolean isPrimitiveArray() {
		if(!this.isArray()) return false;
		return this.getArrayType().isPrimitive();
	}

	public final boolean isBoxedPrimitiveArray() {
		if(!this.isArray()) return false;
		return this.getArrayType().isBoxedPrimitive();
	}

	public final boolean isPrimitiveOrBoxedArray() {
		if(!this.isArray()) return true;
		return this.getArrayType().isPrimitiveOrBoxed();
	}

	public final boolean isNonNumericPrimitive() {
		return this.isOneOf(boolean.class, char.class);
	}

	public final boolean isNonNumericPrimitiveArray() {
		if(!this.isArray()) return false;
		return this.getArrayType().isNonNumericPrimitive();
	}

	public final boolean isNumericPrimitive() {
		return this.isOneOf(double.class, float.class, long.class, int.class, short.class, byte.class);
	}

	public final boolean isNumericPrimitiveArray() {
		if(!this.isArray()) return false;
		return this.getArrayType().isNumericPrimitive();
	}

	public final boolean isIntegerPrimitive() {
		return this.isOneOf(long.class, int.class, short.class, byte.class);
	}

	public final boolean isIntegerPrimitiveArray() {
		if(!this.isArray()) return false;
		return this.getArrayType().isIntegerPrimitive();
	}

	public final boolean isFloatingPrimitive() {
		return this.isOneOf(double.class, float.class);
	}

	public final boolean isFloatingPrimitiveArray() {
		if(!this.isArray()) return false;
		return this.getArrayType().isFloatingPrimitive();
	}

	public final boolean isNonNumericPrimitiveOrBoxed() {
		return this.isNonNumericPrimitive() || this.isOneOf(Boolean.class, Character.class);
	}

	public final boolean isNonNumericPrimitiveOrBoxedArray() {
		if(!this.isArray()) return false;
		return this.getArrayType().isNonNumericPrimitiveOrBoxed();
	}

	public final boolean isNumericPrimitiveOrBoxed() {
		return this.isNumericPrimitive() || this.isSubOf(Number.class);
	}

	public final boolean isNumericPrimitiveOrBoxedArray() {
		if(!this.isArray()) return false;
		return this.getArrayType().isNumericPrimitiveOrBoxed();
	}

	public final boolean isIntegerPrimitiveOrBoxed() {
		return this.isIntegerPrimitive() || this.isOneOf(Long.class, Integer.class, Short.class, Byte.class);
	}

	public final boolean isIntegerPrimitiveOrBoxedArray() {
		if(!this.isArray()) return false;
		return this.getArrayType().isIntegerPrimitiveOrBoxed();
	}

	public final boolean isFloatingPrimitiveOrBoxed() {
		return this.isFloatingPrimitive() || this.isOneOf(Double.class, Float.class);
	}

	public final boolean isFloatingPrimitiveOrBoxedArray() {
		if(!this.isArray()) return false;
		return this.getArrayType().isFloatingPrimitiveOrBoxed();
	}

	public final boolean isCollection() {
		return this.isSubOf(Collection.class);
	}

	public final boolean isMap() {
		return this.isSubOf(Map.class);
	}

	public final int getCollectionDepth() {
		if(!this.isParameterizedType() || !this.isCollection()) return 0;

		ParameterizedType pType = (ParameterizedType) this.original;
		ClassTools tools = this;
		int depth = 0;
		while(tools.isParameterizedType() && tools.isCollection()) {
			depth += 1;

			tools = ClassTools.of(pType.getActualTypeArguments()[0]);
			if(tools.isParameterizedType()) pType = (ParameterizedType) pType.getActualTypeArguments()[0];
		}

		return depth;
	}

	public final ClassTools getCollectionType() {
		if(!this.isParameterizedType() || !this.isCollection()) return this;

		ParameterizedType pType = (ParameterizedType) this.original;
		ClassTools tools = this;
		while(tools.isParameterizedType() && tools.isCollection()) {
			tools = ClassTools.of(pType.getActualTypeArguments()[0]);
			if(tools.isParameterizedType()) pType = (ParameterizedType) pType.getActualTypeArguments()[0];
		}

		return tools;
	}

	public final ClassTools getArrayTypeFromCollection() {
		if(!this.isParameterizedType() || !this.isCollection()) return this;
		return this.getArrayTypeName(this.getCollectionType(), this.getCollectionDepth());
	}

	public final ClassTools getCollectionTypeFromArray() {
		if(!this.isArray()) return this;

		ParameterizedType pType = new ParameterizedTypeImpl(Collection.class, this.getArrayType().get());
		for(int i = 0; i < this.getArrayDepth() - 1; i++) {
			pType = new ParameterizedTypeImpl(Collection.class,pType);
		}

		return ClassTools.of(pType);
	}

	public final int getArrayDepth() {
		if(!this.isArray()) return 0;

		Class inner = this.clazz;
		int depth = 0;
		while(inner.isArray()) {
			depth += 1;
			inner = inner.getComponentType();
		}

		return depth;
	}

	public final ClassTools getArrayType() {
		if(!this.isArray()) return this;

		Class<?> inner = this.clazz;
		while(inner.isArray())
			inner = inner.getComponentType();

		return ClassTools.of(inner);
	}

	public final int getArrayLength(T array) {
		if(!this.isArray()) return 0;
		return ((Object[]) array).length;
	}

	public final int getArrayLength(T array, int depth, int index) {
		if(!this.isArray()) return 0;
		if(depth > this.getArrayDepth()) return 0;

		Object[] converted = ((Object[]) array);
		for(int i = 1; i < depth; i++)
			converted = (Object[]) converted[index];

		return converted.length;
	}

	private String getArrayInnerTypeName(Class<?> c) {
		if(ClassTools.of(c).isPrimitive()) {
			if(c.equals(boolean.class)) return "Z";
			else if(c.equals(char.class)) return "C";
			else if(c.equals(double.class)) return "D";
			else if(c.equals(float.class)) return "F";
			else if(c.equals(long.class)) return "J";
			else if(c.equals(int.class)) return "I";
			else if(c.equals(short.class)) return "S";
			else if(c.equals(byte.class)) return "B";
			else return null;
		}
		return c.getName();
	}

	public final ClassTools getArrayTypeName(ClassTools ct, int depth) {
		return this.getArrayTypeName(ct.get(), depth);
	}

	public final ClassTools getArrayTypeName(Class<?> inner, int depth) {
		try {
			ClassTools tools = ClassTools.of(inner);
			if (tools.isPrimitive()) {
				char[] charArray = new char[depth];
				Arrays.fill(charArray, '[');
				return ClassTools.of(new String(charArray) + this.getArrayInnerTypeName(inner));
			} else {
				char[] charArray = new char[depth];
				Arrays.fill(charArray, '[');
				return ClassTools.of(new String(charArray) + "L" + inner.getName() + ";");
			}
		} catch (Exception e) {
			return this;
		}
	}

	public final Object createArray(int length) {
		try {
			return this.safeCreateArray(length);
		} catch (Exception e) {
			return null;
		}
	}

	public final Object safeCreateArray(int length) throws ReflectiveConstructFailedException {
		try {
			return Array.newInstance(this.get(), length);
		} catch (Exception e) {
			throw new ReflectiveConstructFailedException();
		}
	}

	public final T create(Object... arguments) {
		try {
			return this.construct(arguments);
		} catch (ReflectiveConstructFailedException e) {
			return null;
		}
	}

	public final T safeCreate(Object... arguments) throws ReflectiveConstructFailedException {
		return this.construct(arguments);
	}

	private T construct(Object... arguments) throws ReflectiveConstructFailedException {
		try {
			if(!this.isPrimitiveOrBoxed() && !this.isPrimitiveOrBoxedArray()) {
				Constructor<T> constructor;
				if (arguments == null || arguments.length == 0)
					constructor = this.clazz.getConstructor();
				else {
					constructor = this.clazz.getConstructor(Arrays.stream(arguments)
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
