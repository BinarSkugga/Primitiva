package com.binarskugga.primitiva.conversion;

/**
 * @author Charles Smith (BinarSkugga)
 * Factory class that creates converters based on whether the user want to convert arrays of values or single values.
 * This class has no value when instantiated.
 */
public class PrimitivaConversion {

	private PrimitivaConversion() {}

	/**
	 * Returns an primitive array converter that takes in an array of the type specified in the parameter.
	 * This array converter will ask for the output on every conversion request but will reuse the input type
	 * specified here every time. So PrimitivaConversion.array(Byte[].class) will always takes in a Byte[] as
	 * parameter and convert it to whatever types is specified during the convertTo(...) call.
	 *
	 * @param inC an input type for every conversion.
	 * @return the array converter that takes a inC type array.
	 */
	public static <T> PrimitivaArrayConverter<T> array(Class<T> inC) {
		return new PrimitivaArrayConverter<>(inC);
	}

	/**
	 * Returns an primitive converter that takes in a primitive of the type specified in the parameter.
	 * This converter will ask for the output on every conversion request but will reuse the input type
	 * specified here every time. So PrimitivaConversion.single(Byte.class) will always takes in a Byte as
	 * parameter and convert it to whatever types is specified during the convertTo(...) call.
	 *
	 * @param inC an input type for every conversion.
	 * @return the array converter that takes a inC type primitive.
	 */
	public static <T> PrimitivaConverter<T> single(Class<T> inC) {
		return new PrimitivaConverter<>(inC);
	}

}
