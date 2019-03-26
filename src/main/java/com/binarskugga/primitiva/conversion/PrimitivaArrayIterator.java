package com.binarskugga.primitiva.conversion;

import lombok.Getter;

import java.util.Iterator;

public class PrimitivaArrayIterator<E> implements Iterator<E> {

	@Getter private int currentIndex;
	private Object value;

	@SuppressWarnings("unchecked")
	public PrimitivaArrayIterator(Object value) {
		this.value = value;
		this.currentIndex = 0;
	}

	@SuppressWarnings("unchecked")
	public int getLength() {
		return ((E[]) this.value).length;
	}

	@SuppressWarnings("unchecked")
	@Override public boolean hasNext() {
		return this.currentIndex < ((E[]) this.value).length;
	}

	@SuppressWarnings("unchecked")
	@Override public E next() {
		return ((E[]) this.value)[this.currentIndex++];
	}

}
