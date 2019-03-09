package com.binarskugga.primitiva;

public class App {

	public static void main(String[] args) {
		System.out.print(ClassTools.of(Boolean[][][].class).unbox().get());
	}

}
