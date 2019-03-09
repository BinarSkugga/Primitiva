package com.binarskugga.primitiva;

import com.binarskugga.primitiva.reflection.FieldReflector;
import com.binarskugga.primitiva.reflection.TypeReflector;

import java.lang.reflect.Field;

public class Primitiva {

	private Primitiva() {}

	public static class Conversion {

	}

	public static class Reflection {

		public static <T> TypeReflector<T> ofClass(Class<T> clazz) {
			return new TypeReflector<>(clazz);
		}

		public static FieldReflector ofField(Field field) {
			return new FieldReflector(field);
		}

	}

	public static class Type {

	}

}
