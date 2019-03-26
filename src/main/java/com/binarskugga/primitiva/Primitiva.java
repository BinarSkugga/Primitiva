package com.binarskugga.primitiva;

import com.binarskugga.primitiva.reflection.*;

import java.lang.reflect.*;

public class Primitiva {

	private Primitiva() {}

	public static class Conversion {

	}

	public static class Reflection {

		public static TypeReflector ofType(Object type) {
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
