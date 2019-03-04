package com.binarskugga.primitiva.conversion;


public class PrimitivaConversion {

	private PrimitivaConversion() {}

	public static PrimitivaArrayConverter array(Class inC) {
		return new PrimitivaArrayConverter(inC);
	}

	public static PrimitivaConverter single(Class inC) {
		return new PrimitivaConverter(inC);
	}

}
