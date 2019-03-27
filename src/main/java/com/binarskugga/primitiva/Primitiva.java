package com.binarskugga.primitiva;

import com.binarskugga.primitiva.conversion.PrimitiveConverter;
import com.binarskugga.primitiva.reflection.FieldReflector;
import com.binarskugga.primitiva.reflection.MethodReflector;
import com.binarskugga.primitiva.reflection.ParameterReflector;
import com.binarskugga.primitiva.reflection.TypeReflector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

public class Primitiva {

	private Primitiva() {}

	public static class Convert {

		public static <T> PrimitiveConverter<T> primitive(Class<T> clazz) {
			return new PrimitiveConverter<>(clazz);
		}

		public static <T> PrimitiveConverter<T> primitive(ClassTools<T> ct) {
			return new PrimitiveConverter<>(ct.get());
		}

	}

	public static class Reflect {

		public static TypeReflector type(Type type) {
			return new TypeReflector(type);
		}

		public static TypeReflector type(Class type) {
			return new TypeReflector(type);
		}

		public static MethodReflector method(Method method) {
			return new MethodReflector(method);
		}

		public static ParameterReflector param(Parameter parameter) {
			return new ParameterReflector(parameter);
		}

		public static FieldReflector field(Field field) {
			return new FieldReflector(field);
		}

	}

}
