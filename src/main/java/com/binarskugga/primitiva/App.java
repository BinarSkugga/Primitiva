package com.binarskugga.primitiva;

import java.util.*;

public class App {

	public static void main(String[] args) {
		System.out.println(ClassTools.of(new ArrayList<ArrayList<String>>(){}.getClass().getGenericSuperclass()).getArrayTypeFromCollection().get());
		System.out.println(ClassTools.of(Boolean[][].class).getCollectionTypeFromArray().getCollectionType());
	}

}
