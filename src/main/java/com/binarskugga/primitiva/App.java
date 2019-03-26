package com.binarskugga.primitiva;

import java.util.ArrayList;

public class App {

	public static void main(String[] args) {
		System.out.println(ClassTools.of(new ArrayList<ArrayList<String>>(){}.getClass().getGenericSuperclass()).getArrayTypeFromCollection().get());
		System.out.println(ClassTools.of(Boolean[][].class).getCollectionTypeFromArray().getCollectionType());

		Double[][][] test = Primitiva.Conversion.ofPrimitive(Integer[][][].class).convertTo(new Integer[][][]{
				new Integer[][]{
						new Integer[]{ 56, 0 },
						new Integer[]{ 43, -50 }
				},
				new Integer[][]{
						new Integer[]{ 6, 32 },
						new Integer[]{ -1, -1 }
				},
				new Integer[][]{
						new Integer[]{ 56, 0 },
						new Integer[]{ 43, -50 }
				}
		}, Double[][][].class);
		return;
	}

}
