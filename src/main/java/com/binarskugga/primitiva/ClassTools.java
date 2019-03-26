package com.binarskugga.primitiva;

import com.binarskugga.primitiva.exception.CannotBoxException;
import com.binarskugga.primitiva.reflection.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;

public class ClassTools<T> {

	private Class<T> clazz;
	private Object original;

	@SuppressWarnings("unchecked")
	private ClassTools(Object original) {
		this.original = original;
		this.clazz = this.getClassOf(original);
	}

	private Class getClassOf(Object o) {
		if(o instanceof String) {
			try {
				return Class.forName((String) o);
			} catch (ClassNotFoundException e) {
				return null;
			}
		} else if (o instanceof Class) {
			return (Class) o;
		} else if (o instanceof ParameterizedType) {
			Type rawType = ((ParameterizedType) o).getRawType();
			return (Class) rawType;
		} else if (o instanceof GenericArrayType) {
			Type componentType = ((GenericArrayType) o).getGenericComponentType();
			return Array.newInstance(ClassTools.of(componentType).get(), 0).getClass();
		} else if (o instanceof WildcardType) {
			return ClassTools.of(((WildcardType) o).getUpperBounds()[0]).get();
		} else {
			return null;
		}
	}

	public static ClassTools of(Object o) {
		return new ClassTools(o);
	}

	public Class<T> get() {
		return this.clazz;
	}

	public ClassTools box() {
		try {
			return this.safeBox();
		} catch (Exception e) {
			return null;
		}
	}

	public ClassTools unbox() {
		try {
			return this.safeUnbox();
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public ClassTools safeBox() throws Exception {
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

	@SuppressWarnings("unchecked")
	public ClassTools safeUnbox() throws Exception {
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
			Class unboxedInner = ClassTools.of(this.getArrayType()).safeUnbox().get();
			return ClassTools.of(this.getArrayTypeName(unboxedInner, depth));
		} else if(this.isPrimitive() || this.isPrimitiveArray()) return this;
		else throw new CannotBoxException();
	}

	public List<Field> getAllFields() {
		return this.getFields(null);
	}

	public List<Field> getFields(Predicate<Field> predicate) {
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

	public List<Method> getAllMethods() {
		return this.getMethods(null);
	}

	public List<Method> getMethods(Predicate<Method> predicate) {
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

	public boolean isParameterizedType() {
		return this.original instanceof ParameterizedType;
	}

	public Type[] getTypeArguments() {
		if(this.isParameterizedType())
			return ((ParameterizedType) original).getActualTypeArguments();
		return null;
	}

	public Type getTypeArgument() {
		return getTypeArgument(0);
	}

	public Type getTypeArgument(int i) {
		if(this.isParameterizedType())
			return ((ParameterizedType) original).getActualTypeArguments()[i];
		return null;
	}

	public boolean hasEmptyConstructor() {
		try {
			Constructor c = this.clazz.getDeclaredConstructor();
			return c != null;
		} catch(Exception e) {
			return false;
		}
	}

	public boolean isSuperOf(Object o) {
		Class c = ClassTools.of(o).get();
		return this.clazz.isAssignableFrom(c);
	}

	public boolean isSuperOf(Object... os) {
		for(Object o : os)
			if(this.isSuperOf(o)) return true;
		return false;
	}

	@SuppressWarnings("unchecked")
	public boolean isSubOf(Object o) {
		Class c = ClassTools.of(o).get();
		return c.isAssignableFrom(this.clazz);
	}

	public boolean isSubOf(Object... os) {
		for(Object o : os)
			if(this.isSubOf(o)) return true;
		return false;
	}

	public boolean isOneOf(Object... os) {
		for(Object o : os)
			if(this.clazz.equals(ClassTools.of(o).get())) return true;
		return false;
	}

	public boolean isPrimitive() {
		return this.clazz.isPrimitive();
	}

	public boolean isBoxedPrimitive() {
		return this.isSubOf(Number.class) || this.isOneOf(Character.class, Boolean.class);
	}

	public boolean isPrimitiveOrBoxed() {
		return this.isPrimitive() || this.isBoxedPrimitive();
	}

	public boolean isArray() {
		return this.clazz.isArray();
	}

	public boolean isCollection() {
		return this.isSubOf(Collection.class);
	}

	public int getCollectionDepth() {
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

	public Class getCollectionType() {
		if(!this.isParameterizedType() || !this.isCollection()) return this.clazz;

		ParameterizedType pType = (ParameterizedType) this.original;
		ClassTools tools = this;
		while(tools.isParameterizedType() && tools.isCollection()) {
			tools = ClassTools.of(pType.getActualTypeArguments()[0]);
			if(tools.isParameterizedType()) pType = (ParameterizedType) pType.getActualTypeArguments()[0];
		}

		return tools.get();
	}

	public ClassTools getArrayTypeFromCollection() {
		if(!this.isParameterizedType() || !this.isCollection()) return this;
		return ClassTools.of(this.getArrayTypeName(this.getCollectionType(), this.getCollectionDepth()));
	}

	public ClassTools getCollectionTypeFromArray() {
		if(!this.isArray()) return this;

		ParameterizedType pType = new ParameterizedTypeImpl(Collection.class, this.getArrayType());
		for(int i = 0; i < this.getArrayDepth() - 1; i++) {
			pType = new ParameterizedTypeImpl(Collection.class,pType);
		}

		return ClassTools.of(pType);
	}

	public int getArrayDepth() {
		if(!this.isArray()) return 0;

		Class inner = this.clazz;
		int depth = 0;
		while(inner.isArray()) {
			depth += 1;
			inner = inner.getComponentType();
		}

		return depth;
	}

	public Class getArrayType() {
		if(!this.isArray()) return this.clazz;

		Class inner = this.clazz;
		while(inner.isArray())
			inner = inner.getComponentType();

		return inner;
	}

	public int getArrayLength(T array) {
		if(!this.isArray()) return 0;
		return ((Object[]) array).length;
	}

	public int getArrayLength(T array, int depth, int index) {
		if(!this.isArray()) return 0;
		if(depth > this.getArrayDepth()) return 0;

		Object[] converted = ((Object[]) array);
		for(int i = 1; i < depth; i++)
			converted = (Object[]) converted[index];

		return converted.length;
	}

	@SuppressWarnings("unchecked")
	public boolean isPrimitiveArray() {
		if(!this.isArray()) return false;
		return ClassTools.of(this.getArrayType()).isPrimitive();
	}

	@SuppressWarnings("unchecked")
	public boolean isBoxedPrimitiveArray() {
		if(!this.isArray()) return false;
		return ClassTools.of(this.getArrayType()).isBoxedPrimitive();
	}

	@SuppressWarnings("unchecked")
	public boolean isPrimitiveOrBoxedArray() {
		if(!this.isArray()) return false;
		return ClassTools.of(this.getArrayType()).isPrimitiveOrBoxed();
	}

	private String getArrayInnerTypeName(Class c) {
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

	public Class getArrayTypeName(Class inner, int depth) {
		try {
			ClassTools tools = ClassTools.of(inner);
			if (tools.isPrimitive()) {
				char[] charArray = new char[depth];
				Arrays.fill(charArray, '[');
				return Class.forName(new String(charArray) + this.getArrayInnerTypeName(inner));
			} else {
				char[] charArray = new char[depth];
				Arrays.fill(charArray, '[');
				return Class.forName(new String(charArray) + "L" + inner.getName() + ";");
			}
		} catch (Exception e) {
			return null;
		}
	}

}
