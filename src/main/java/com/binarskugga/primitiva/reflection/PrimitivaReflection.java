package com.binarskugga.primitiva.reflection;

import com.binarskugga.primitiva.conversion.PrimitivaArrayConverter;
import com.binarskugga.primitiva.conversion.PrimitivaConversion;
import com.binarskugga.primitiva.exception.ReflectiveConstructFailedException;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Charles Smith (BinarSkugga)
 * PrimitivaReflection is a static class that provides multiple utilities when using the reflection API.
 * This class has no value when instantiated.
 */
public class PrimitivaReflection {

	private PrimitivaReflection() { }

	/**
	 * Create a class object from its fully qualified name using Class.forName. If the inner operation
	 * throws an exception, the method silence it and returns null.
	 * @param str the fully qualified class name.
	 * @return a class object or null if failure.
	 */
	public static Class forNameOrNull(String str) {
		try {
			return Class.forName(str);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	/**
	 * Gets all fields of a class, including private and superclass fields.
	 * @param clazz the target class to get fields froms.
	 * @return all fields from the target class.
	 */
	public static List<Field> getAllFields(Class clazz) {
		List<Field> fields = new ArrayList<>();
		while (clazz.getSuperclass() != null) {
			fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
			clazz = clazz.getSuperclass();
		}
		return fields;
	}

	/**
	 * Gets all methods of a class, including private and superclass methods.
	 * @param clazz the target class to get methods froms.
	 * @return all methods from the target class.
	 */
	public static List<Method> getAllMethods(Class clazz) {
		List<Method> methods = new ArrayList<>();
		while (clazz.getSuperclass() != null) {
			methods.addAll(Arrays.asList(clazz.getDeclaredMethods()));
			clazz = clazz.getSuperclass();
		}
		return methods;
	}

	/**
	 * Get an annotation on a class or null in case the class is not annotated.
	 * @param clazz the target class.
	 * @param annotation the annotation to find.
	 * @param <T> is the type of the annotation.
	 * @return the annotation object or null on failure.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Annotation> T getClassAnnotationOrNull(Class clazz, Class<T> annotation) {
		if (!clazz.isAnnotationPresent(annotation)) return null;
		else return (T) clazz.getAnnotation(annotation);
	}

	/**
	 * Get an annotation on a method or null in case the method is not annotated.
	 * @param method the target method.
	 * @param annotation the annotation to find.
	 * @param <T> is the type of the annotation.
	 * @return the annotation object or null on failure.
	 */
	public static <T extends Annotation> T getMethodAnnotationOrNull(Method method, Class<T> annotation) {
		if (!method.isAnnotationPresent(annotation)) return null;
		else return method.getAnnotation(annotation);
	}

	/**
	 * Get an annotation on a field or null in case the field is not annotated.
	 * @param field the target field.
	 * @param annotation the annotation to find.
	 * @param <T> is the type of the annotation.
	 * @return the annotation object or null on failure.
	 */
	public static <T extends Annotation> T getFieldAnnotationOrNull(Field field, Class<T> annotation) {
		if (!field.isAnnotationPresent(annotation)) return null;
		else return field.getAnnotation(annotation);
	}

	/**
	 * Get an annotation on a parameter or null in case the parameter is not annotated.
	 * @param param the target parameter.
	 * @param annotation the annotation to find.
	 * @param <T> is the type of the annotation.
	 * @return the annotation object or null on failure.
	 */
	public static <T extends Annotation> T getParamAnnotationOrNull(Parameter param, Class<T> annotation) {
		if (!param.isAnnotationPresent(annotation)) return null;
		else return param.getAnnotation(annotation);
	}

	/**
	 * Get an annotation on a class, method, field or parameter or null in case the object is not annotated.
	 * @param reflect the target which need to be of type Class, Method, Field or Parameter.
	 * @param annotation the annotation to find.
	 * @param <T> is the type of the annotation.
	 * @return the annotation object or null if the annotation is not present or the reflect object is of an invalid type.
	 */
	public static <T extends Annotation> T getAnnotationOrNull(Object reflect, Class<T> annotation) {
		if (Class.class.isAssignableFrom(reflect.getClass()))
			return PrimitivaReflection.getClassAnnotationOrNull((Class) reflect, annotation);
		else if (Method.class.isAssignableFrom(reflect.getClass()))
			return PrimitivaReflection.getMethodAnnotationOrNull((Method) reflect, annotation);
		else if (Field.class.isAssignableFrom(reflect.getClass()))
			return PrimitivaReflection.getFieldAnnotationOrNull((Field) reflect, annotation);
		else if (Parameter.class.isAssignableFrom(reflect.getClass()))
			return PrimitivaReflection.getParamAnnotationOrNull((Parameter) reflect, annotation);
		return null;
	}

	/**
	 * Try to call the constructor that uses the arguments specified. To call the empty constructor,
	 * simply omit the arguments parameter.
	 * @param declaring the class to be instantiated.
	 * @param arguments the arguments of the constructor.
	 * @param <T> the type that will be returned on success.
	 * @return the constructed object or null if the constructor cannot be called properly.
	 */
	public static <T> T constructOrNull(Class<T> declaring, Object... arguments) {
		try {
			return safeConstruct(declaring, arguments);
		} catch (ReflectiveConstructFailedException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Try to call the constructor that uses the arguments specified. To call the empty constructor,
	 * simply omit the arguments parameter.
	 * @param declaring the class to be instantiated.
	 * @param arguments the arguments of the constructor.
	 * @param <T> the type that will be returned on success.
	 * @throws ReflectiveConstructFailedException when the reflection API fails to call a constructor with the specified arguments.
	 * @return the constructed object.
	 */
	public static <T> T safeConstruct(Class<T> declaring, Object... arguments) throws ReflectiveConstructFailedException {
		try {
			Constructor<T> constructor;
			if (arguments == null || arguments.length == 0)
				constructor = declaring.getConstructor();
			else {
				constructor = declaring.getConstructor(Arrays.asList(arguments).stream()
						.map(Object::getClass).collect(Collectors.toList()).toArray(new Class[arguments.length]));
			}

			constructor.setAccessible(true);
			return constructor.newInstance(arguments);
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
			throw new ReflectiveConstructFailedException(e);
		}
	}

	/**
	 * Set the value of a field.
	 * @param field the field to set.
	 * @param instance the object instance that holds the previously provided field.
	 * @param value the value to set the field with.
	 * @param force whether or not to force the value, will fail silently if this is false and the field is private / not accessible.
	 */
	public static void setField(Field field, Object instance, Object value, boolean force) {
		try {
			if (force) field.setAccessible(true);
			field.set(instance, value);
		} catch (Exception ignored) {
		}
	}

	/**
	 * Set the value of a field and assume a forced set.
	 * @param field the field to set.
	 * @param instance the object instance that holds the previously provided field.
	 * @param value the value to set the field with.
	 */
	public static void setField(Field field, Object instance, Object value) {
		setField(field, instance, value, true);
	}

	/**
	 * Get de value of a field
	 * @param field the field to get the value from.
	 * @param instance the object instance that holds the previously provided field.
	 * @param force whether or not to force the retrieval, will fail silently if this is false and the field is private / not accessible.
	 * @return the value of the field for an instance.
	 */
	public static Object getField(Field field, Object instance, boolean force) {
		try {
			if (force) field.setAccessible(true);
			return field.get(instance);
		} catch (Exception ignored) {
			return null;
		}
	}

	/**
	 * Get de value of a field and assumes a forced retrieval.
	 * @param field the field to get the value from.
	 * @param instance the object instance that holds the previously provided field.
	 * @return the value of the field for an instance.
	 */
	public static Object getField(Field field, Object instance) {
		return getField(field, instance, true);
	}

	/**
	 * Invoke a method and returns its value or null if the invocation fails.
	 * @param returnClass the type that is supposed to be returned from the method.
	 * @param action the method to invoke.
	 * @param instance is the method is not static (specify null if it is static), the instance the method needs to be called on.
	 * @param arguments the arguments of the method to invoke.
	 * @return the method result or null it fails to be invoked.
	 */
	public static <T> T invokeOrNull(Class<T> returnClass, Method action, Object instance, Object[] arguments) {
		try {
			return (T) action.invoke(instance, arguments);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Compare a class to 2 other classes and returns true if one or both of the two classes are equals the initial class.
	 * @param clazz the initial class to compare.
	 * @param boxed the first comparator.
	 * @param unboxed the second comparator.
	 * @return true if one or both of the two classes are equals the initial class.
	 */
	public static boolean typeEqualsIgnoreBoxing(Class clazz, Class boxed, Class unboxed) {
		return clazz.equals(boxed) || clazz.equals(unboxed);
	}

	/**
	 * Compare a 2 classes to 2 other classes and returns true if one or both of the two classes are equals to both of the initial classes.
	 * @param clazz the fist initial class to compare.
	 * @param clazz2 the second initial class to compare.
	 * @param boxed the first comparator.
	 * @param unboxed the second comparator.
	 * @return true if one or both of the two classes are equals to both of the initial classes.
	 */
	public static boolean typeEqualsIgnoreBoxing(Class clazz, Class clazz2, Class boxed, Class unboxed) {
		return (clazz.equals(boxed) || clazz.equals(unboxed)) && (clazz2.equals(boxed) || clazz2.equals(unboxed));
	}

	/**
	 * Returns the component type of the class specified or itself in case it is not an array type.
	 * @param arrayClass the class to get the component type from.
	 * @return the component type of the class specified or itself in case it is not an array type.
	 */
	public static Class getInnerArrayType(Class arrayClass) {
		if (arrayClass.isArray())
			return arrayClass.getComponentType();
		return arrayClass;
	}

	/**
	 * Returns whether or not the class specified is a primitive type. This method returns false for the void class.
	 * @param clazz the class to check.
	 * @return whether or not the class specified is a primitive type.
	 */
	public static boolean isPrimitive(Class clazz) {
		return clazz.equals(byte.class) || clazz.equals(short.class) || clazz.equals(int.class)
				|| clazz.equals(long.class) || clazz.equals(double.class) || clazz.equals(float.class)
				|| clazz.equals(char.class) || clazz.equals(boolean.class);
	}

	/**
	 * Returns whether or not the class specified is a boxed primitive type. Inheriting the Number class will expand the
	 * behavior of this method. This method returns false for the Void class.
	 * @param clazz the class to check.
	 * @return whether or not the class specified is a boxed primitive type.
	 */
	public static boolean isBoxedPrimitive(Class clazz) {
		return Number.class.isAssignableFrom(clazz) || clazz.equals(Character.class) || clazz.equals(Boolean.class);
	}

	public static boolean isPrimitiveOrBoxed(Class clazz) {
		return isPrimitive(clazz) || isBoxedPrimitive(clazz);
	}

	public static boolean isPrimitiveArray(Class clazz) {
		return clazz.equals(byte[].class) || clazz.equals(short[].class) || clazz.equals(int[].class)
				|| clazz.equals(long[].class) || clazz.equals(double[].class) || clazz.equals(float[].class)
				|| clazz.equals(char[].class) || clazz.equals(boolean[].class);
	}

	public static boolean isBoxedPrimitiveArray(Class clazz) {
		return clazz.equals(Byte[].class) || clazz.equals(Short[].class) || clazz.equals(Integer[].class)
				|| clazz.equals(Long[].class) || clazz.equals(Double[].class) || clazz.equals(Float[].class)
				|| clazz.equals(Character[].class) || clazz.equals(Boolean[].class);
	}

	public static boolean isPrimitiveArrayOrBoxed(Class clazz) {
		return isPrimitiveArray(clazz) || isBoxedPrimitiveArray(clazz);
	}

	public static boolean isNumericPrimitive(Class clazz) {
		return clazz.equals(byte.class) || clazz.equals(short.class) || clazz.equals(int.class)
				|| clazz.equals(long.class) || clazz.equals(double.class) || clazz.equals(float.class);
	}

	public static boolean isNumericPrimitiveOrBoxed(Class clazz) {
		return isNumericPrimitive(clazz) || Number.class.isAssignableFrom(clazz);
	}

	public static boolean isFloatingNumericPrimitive(Class clazz) {
		return clazz.equals(double.class) || clazz.equals(float.class);
	}

	public static boolean isIntegerNumericPrimitive(Class clazz) {
		return clazz.equals(byte.class) || clazz.equals(short.class) || clazz.equals(int.class)
				|| clazz.equals(long.class);
	}

	public static Class findPrimitiveCollectionType(Collection collection) {
		Object zero = collection.stream().findFirst().orElse(null);
		if(zero == null) return null;
		return zero.getClass();
	}

	@SuppressWarnings("unchecked")
	public static Object primitiveCollectionToArray(Collection collection) {
		Class inner = findPrimitiveCollectionType(collection);
		if(inner.equals(Boolean.class))
			return collection.toArray(new Boolean[collection.size()]);
		else if(inner.equals(Character.class))
			return collection.toArray(new Character[collection.size()]);
		else if(inner.equals(Double.class))
			return collection.toArray(new Double[collection.size()]);
		else if(inner.equals(Float.class))
			return collection.toArray(new Float[collection.size()]);
		else if(inner.equals(Long.class))
			return collection.toArray(new Long[collection.size()]);
		else if(inner.equals(Integer.class))
			return collection.toArray(new Integer[collection.size()]);
		else if(inner.equals(Short.class))
			return collection.toArray(new Short[collection.size()]);
		else if(inner.equals(Byte.class))
			return collection.toArray(new Byte[collection.size()]);
		else return null;
	}

	public static Class unbox(Class clazz) {
		if(!isBoxedPrimitive(clazz) && !isBoxedPrimitiveArray(clazz)) return null;
		else if(clazz.equals(Boolean.class)) return boolean.class;
		else if(clazz.equals(Character.class)) return char.class;
		else if(clazz.equals(Double.class)) return double.class;
		else if(clazz.equals(Float.class)) return float.class;
		else if(clazz.equals(Long.class)) return long.class;
		else if(clazz.equals(Integer.class)) return int.class;
		else if(clazz.equals(Short.class)) return short.class;
		else if(clazz.equals(Byte.class)) return byte.class;
		else if(clazz.equals(Boolean[].class)) return boolean[].class;
		else if(clazz.equals(Character[].class)) return char[].class;
		else if(clazz.equals(Double[].class)) return double[].class;
		else if(clazz.equals(Float[].class)) return float[].class;
		else if(clazz.equals(Long[].class)) return long[].class;
		else if(clazz.equals(Integer[].class)) return int[].class;
		else if(clazz.equals(Short[].class)) return short[].class;
		else if(clazz.equals(Byte[].class)) return byte[].class;
		else return null;
	}

	public static Class box(Class clazz) {
		if(!isPrimitive(clazz) && !isPrimitiveArray(clazz)) return null;
		if(clazz.equals(boolean.class)) return Boolean.class;
		else if(clazz.equals(char.class)) return Character.class;
		else if(clazz.equals(double.class)) return Double.class;
		else if(clazz.equals(float.class)) return Float.class;
		else if(clazz.equals(long.class)) return Long.class;
		else if(clazz.equals(int.class)) return Integer.class;
		else if(clazz.equals(short.class)) return Short.class;
		else if(clazz.equals(byte.class)) return Byte.class;
		else if(clazz.equals(boolean[].class)) return Boolean[].class;
		else if(clazz.equals(char[].class)) return Character[].class;
		else if(clazz.equals(double[].class)) return Double[].class;
		else if(clazz.equals(float[].class)) return Float[].class;
		else if(clazz.equals(long[].class)) return Long[].class;
		else if(clazz.equals(int[].class)) return Integer[].class;
		else if(clazz.equals(short[].class)) return Short[].class;
		else if(clazz.equals(byte[].class)) return Byte[].class;
		else return null;
	}

	public static Object stringToPrimitive(String str, Class c) {
		if (isPrimitiveOrBoxed(c)) {
			if (PrimitivaReflection.typeEqualsIgnoreBoxing(c, Byte.class, byte.class))
				return Byte.parseByte(str);
			else if (PrimitivaReflection.typeEqualsIgnoreBoxing(c, Short.class, short.class))
				return Short.parseShort(str);
			else if (PrimitivaReflection.typeEqualsIgnoreBoxing(c, Integer.class, int.class))
				return Integer.parseInt(str);
			else if (PrimitivaReflection.typeEqualsIgnoreBoxing(c, Long.class, long.class))
				return Long.parseLong(str);
			else if (PrimitivaReflection.typeEqualsIgnoreBoxing(c, Float.class, float.class))
				return Float.parseFloat(str);
			else if (PrimitivaReflection.typeEqualsIgnoreBoxing(c, Double.class, double.class))
				return Double.parseDouble(str);
			else if (PrimitivaReflection.typeEqualsIgnoreBoxing(c, Boolean.class, boolean.class)) {
				return str.equalsIgnoreCase("true");
			} else if (PrimitivaReflection.typeEqualsIgnoreBoxing(c, Character.class, char.class)) {
				return str.charAt(0);
			} else
				return str;
		} else return str;
	}

	public static Object stringToPrimitiveArray(String str, String separator, Class c) {
		if (c.equals(byte[].class))
			return ArrayUtils.toPrimitive(Stream.of(str.split(separator)).map(Byte::parseByte).toArray(Byte[]::new));
		else if (c.equals(short[].class))
			return ArrayUtils.toPrimitive(Stream.of(str.split(separator)).map(Short::parseShort).toArray(Short[]::new));
		else if (c.equals(int[].class))
			return ArrayUtils.toPrimitive(Stream.of(str.split(separator)).map(Integer::parseInt).toArray(Integer[]::new));
		else if (c.equals(long[].class))
			return ArrayUtils.toPrimitive(Stream.of(str.split(separator)).map(Long::parseLong).toArray(Long[]::new));
		else if (c.equals(float[].class))
			return ArrayUtils.toPrimitive(Stream.of(str.split(separator)).map(Float::parseFloat).toArray(Float[]::new));
		else if (c.equals(double[].class))
			return ArrayUtils.toPrimitive(Stream.of(str.split(separator)).map(Double::parseDouble).toArray(Double[]::new));
		else if (c.equals(boolean[].class))
			return ArrayUtils.toPrimitive(Stream.of(str.split(separator)).map(b -> b.equalsIgnoreCase("true")).toArray(Boolean[]::new));
		else if (c.equals(char[].class))
			return ArrayUtils.toPrimitive(Stream.of(str.split(separator)).map(ch -> ch.charAt(0)).toArray(Character[]::new));
		else if (c.equals(Byte[].class))
			return Stream.of(str.split(separator)).map(Byte::parseByte).toArray(Byte[]::new);
		else if (c.equals(Short[].class))
			return Stream.of(str.split(separator)).map(Short::parseShort).toArray(Short[]::new);
		else if (c.equals(Integer[].class))
			return Stream.of(str.split(separator)).map(Integer::parseInt).toArray(Integer[]::new);
		else if (c.equals(Long[].class))
			return Stream.of(str.split(separator)).map(Long::parseLong).toArray(Long[]::new);
		else if (c.equals(Float[].class))
			return Stream.of(str.split(separator)).map(Float::parseFloat).toArray(Float[]::new);
		else if (c.equals(Double[].class))
			return Stream.of(str.split(separator)).map(Double::parseDouble).toArray(Double[]::new);
		else if (c.equals(Boolean[].class))
			return Stream.of(str.split(separator)).map(b -> b.equalsIgnoreCase("true")).toArray(Boolean[]::new);
		else if (c.equals(Character[].class))
			return Stream.of(str.split(separator)).map(ch -> ch.charAt(0)).toArray(Character[]::new);
		else
			return str;
	}

	@SuppressWarnings("unchecked")
	public static <T> Collection<T> stringToPrimitiveCollection(String str, String separator, Class<T> inner) {
		if (inner.equals(Byte.class))
			return (Collection<T>) Stream.of(str.split(separator)).map(Byte::parseByte).collect(Collectors.toList());
		else if (inner.equals(Short.class))
			return (Collection<T>) Stream.of(str.split(separator)).map(Short::parseShort).collect(Collectors.toList());
		else if (inner.equals(Integer.class))
			return (Collection<T>) Stream.of(str.split(separator)).map(Integer::parseInt).collect(Collectors.toList());
		else if (inner.equals(Long.class))
			return (Collection<T>) Stream.of(str.split(separator)).map(Long::parseLong).collect(Collectors.toList());
		else if (inner.equals(Float.class))
			return (Collection<T>) Stream.of(str.split(separator)).map(Float::parseFloat).collect(Collectors.toList());
		else if (inner.equals(Double.class))
			return (Collection<T>) Stream.of(str.split(separator)).map(Double::parseDouble).collect(Collectors.toList());
		else if (inner.equals(Boolean.class))
			return (Collection<T>) Stream.of(str.split(separator)).map(b -> b.equalsIgnoreCase("true")).collect(Collectors.toList());
		else if (inner.equals(Character.class))
			return (Collection<T>) Stream.of(str.split(separator)).map(ch -> ch.charAt(0)).collect(Collectors.toList());
		else
			return null;
	}

}