package com.binarskugga.primitiva;

public class App {

	public static void main(String[] args) {
		System.out.println(ClassTools.of(Boolean[][][].class).unbox().isPrimitiveArray());
	}

}
