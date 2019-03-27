package com.binarskugga.primitiva.reflection;

import com.binarskugga.primitiva.ClassTools;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

public class ParameterizedTypeImpl implements ParameterizedType {

	private final Type owner;
	private final Type[] arguments;

	public ParameterizedTypeImpl(Type owner, Type... arguments) {
		this.owner = owner;
		this.arguments = Arrays.copyOf(arguments, arguments.length, Type[].class);
	}

	public Type getRawType() {
		return ClassTools.of(this.owner).get();
	}

	public Type getOwnerType() {
		return this.owner;
	}

	public Type[] getActualTypeArguments() {
		return this.arguments.clone();
	}

}
