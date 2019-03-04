package com.binarskugga.primitiva.conversion;


public class PrimitivaConversion {

	private PrimitivaConversion() {}

	public static <T> PrimitivaArrayConverter<T> array(Class<T> inC) {
		return new PrimitivaArrayConverter<>(inC);
	}

	public static <T> PrimitivaConverter<T> single(Class<T> inC) {
		return new PrimitivaConverter<>(inC);
	}

}
