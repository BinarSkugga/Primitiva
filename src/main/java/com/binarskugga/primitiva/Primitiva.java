package com.binarskugga.primitiva;

import com.binarskugga.primitiva.conversion.PrimitiveConverter;
import com.binarskugga.primitiva.reflection.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class Primitiva {

	private Primitiva() {}


	public static class Conversion {

		public static <T> PrimitiveConverter<T> ofPrimitive(Class<T> clazz) {
			return new PrimitiveConverter<>(clazz);
		}

	}

	public static class Reflection {

		public static TypeReflector ofType(Object type) {
			return new TypeReflector<>(type);
		}

		public static <T> TypeReflector<T> ofType(Class<T> type) {
			return new TypeReflector<>(type);
		}

		public static MethodReflector ofMethod(Method method) {
			return new MethodReflector(method);
		}

		public static ConstructorReflector ofConstructor(Constructor constructor) {
			return new ConstructorReflector(constructor);
		}

		public static ParameterReflector ofParameter(Parameter parameter) {
			return new ParameterReflector(parameter);
		}

		public static FieldReflector ofField(Field field) {
			return new FieldReflector(field);
		}

	}

}
