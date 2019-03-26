package com.binarskugga.primitiva.conversion;

@FunctionalInterface
public interface ElementParser<A, B> {

	B parser(A element);

}
