package com.binarskugga.primitiva.conversion;

import com.binarskugga.primitiva.ClassTools;
import com.binarskugga.primitiva.exception.NonConversibleTypeException;
import com.binarskugga.primitiva.exception.NonPrimitiveTypeException;

import java.lang.reflect.Array;
import java.util.Iterator;

public class PrimitiveConverter<T> extends Converter<T> {

	public PrimitiveConverter(Class<T> inClass) throws NonPrimitiveTypeException {
		super(inClass);
		if(!this.getInClass().isPrimitiveOrBoxed() && !this.getInClass().isPrimitiveOrBoxedArray()
			&& !this.getInClass().isSubOf(CharSequence.class)
			&& !(this.getInClass().isArray() && CharSequence.class.isAssignableFrom(this.getInClass().getArrayType())))
			throw new NonPrimitiveTypeException();
	}

	@SuppressWarnings("unchecked")
	@Override public <R> R convertTo(T value, Class<R> returnClass) {
		ClassTools tools = ClassTools.of(returnClass);
		if(!tools.isPrimitiveOrBoxed() && !tools.isPrimitiveOrBoxedArray())
			throw new NonPrimitiveTypeException();

		ClassTools outUnboxed = tools.unbox();
		ClassTools inUnboxed = this.getInClass().unbox();

		if(tools.isArray()) {
			if (inUnboxed.get().equals(returnClass)) return (R) value;
			else if (outUnboxed.getArrayType().equals(boolean.class)) return (R) this.toBooleanArray(returnClass, value);
			else if (outUnboxed.getArrayType().equals(char.class)) return (R) this.toCharArray(returnClass, value);
			else if (outUnboxed.getArrayType().equals(double.class)) return (R) this.toDoubleArray(returnClass, value);
			else if (outUnboxed.getArrayType().equals(float.class)) return (R) this.toFloatArray(returnClass, value);
			else if (outUnboxed.getArrayType().equals(long.class)) return (R) this.toLongArray(returnClass, value);
			else if (outUnboxed.getArrayType().equals(int.class)) return (R) this.toIntArray(returnClass, value);
			else if (outUnboxed.getArrayType().equals(short.class)) return (R) this.toShortArray(returnClass, value);
			else if (outUnboxed.getArrayType().equals(byte.class)) return (R) this.toByteArray(returnClass, value);
			else throw new NonConversibleTypeException();
		} else {
			if (inUnboxed.get().equals(returnClass)) return (R) value;
			else if (outUnboxed.get().equals(boolean.class)) return (R) this.toBoolean(value);
			else if (outUnboxed.get().equals(char.class)) return (R) this.toChar(value);
			else if (outUnboxed.get().equals(double.class)) return (R) this.toDouble(value);
			else if (outUnboxed.get().equals(float.class)) return (R) this.toFloat(value);
			else if (outUnboxed.get().equals(long.class)) return (R) this.toLong(value);
			else if (outUnboxed.get().equals(int.class)) return (R) this.toInt(value);
			else if (outUnboxed.get().equals(short.class)) return (R) this.toShort(value);
			else if (outUnboxed.get().equals(byte.class)) return (R) this.toByte(value);
			else throw new NonConversibleTypeException();
		}
	}

	@SuppressWarnings("unchecked")
	private <B> B toBooleanArray(Class<B> clazz, T array) {
		ClassTools inTools = ClassTools.of(array.getClass());
		if(!inTools.isPrimitiveOrBoxedArray() && !(inTools.isArray() && CharSequence.class.isAssignableFrom(inTools.getArrayType())))
			throw new NonConversibleTypeException();

		int depth = inTools.getArrayDepth();
		PrimitivaArrayIterator iterator = new PrimitivaArrayIterator(array);

		ClassTools outTools = ClassTools.of(clazz);
		if(depth < 1) throw new NonConversibleTypeException();
		if(depth == 1) {
			Object result = Array.newInstance(outTools.getArrayType(), iterator.getLength());
			while(iterator.hasNext()) {
				int index = iterator.getCurrentIndex();
				Object o = iterator.next();
				((Object[])result)[index] = new PrimitiveConverter<>(inTools.getArrayType()).toBoolean(o);
			}
			return (B) result;
		} else {
			Object result = Array.newInstance(outTools.getArrayType(), iterator.getLength());
			for(int i = 1; i < depth; i++)
				result = Array.newInstance(result.getClass(), iterator.getLength());

			while(iterator.hasNext()) {
				int index = iterator.getCurrentIndex();
				Object o = iterator.next();
				((Object[])result)[index] = new PrimitiveConverter<>(inTools.getArrayType())
						.toBooleanArray(ClassTools.of(Object.class).getArrayTypeName(outTools.getArrayType(), depth - 1), o);
			}
			return (B) result;
		}
	}
	private Boolean toBoolean(T val) {
		ClassTools unboxed = this.getInClass().unbox();
		if(!unboxed.isPrimitive() && !unboxed.isSubOf(CharSequence.class))
			throw new NonConversibleTypeException();
		else if(unboxed.get().equals(boolean.class)) return (Boolean) val;
		else if(unboxed.isSubOf(CharSequence.class)) return toBoolean((CharSequence) val);
		else if(unboxed.get().equals(char.class)) return toBoolean((Character) val);
		else if(unboxed.get().equals(double.class)) return toBoolean((Double) val);
		else if(unboxed.get().equals(float.class)) return toBoolean((Float) val);
		else if(unboxed.get().equals(byte.class)) return toBoolean((Byte) val);
		else if(unboxed.get().equals(short.class)) return toBoolean((Short) val);
		else if(unboxed.get().equals(int.class)) return toBoolean((Integer) val);
		else if(unboxed.get().equals(long.class)) return toBoolean((Long) val);
		else throw new NonConversibleTypeException();
	}
	private boolean toBoolean(CharSequence a) {
		return ((String) a).equalsIgnoreCase("true");
	}
	private boolean toBoolean(char a) {
		return a > 0;
	}
	private boolean toBoolean(double a) {
		return a > 0;
	}
	private boolean toBoolean(float a) {
		return a > 0;
	}
	private boolean toBoolean(byte a) {
		return a > 0;
	}
	private boolean toBoolean(short a) {
		return a > 0;
	}
	private boolean toBoolean(int a) {
		return a > 0;
	}
	private boolean toBoolean(long a) {
		return a > 0;
	}

	@SuppressWarnings("unchecked")
	private <B> B toCharArray(Class<B> clazz, T array) {
		ClassTools inTools = ClassTools.of(array.getClass());
		if(!inTools.isPrimitiveOrBoxedArray() && !(inTools.isArray() && CharSequence.class.isAssignableFrom(inTools.getArrayType())))
			throw new NonConversibleTypeException();

		int depth = inTools.getArrayDepth();
		PrimitivaArrayIterator iterator = new PrimitivaArrayIterator(array);

		ClassTools outTools = ClassTools.of(clazz);
		if(depth < 1) throw new NonConversibleTypeException();
		if(depth == 1) {
			Object result = Array.newInstance(outTools.getArrayType(), iterator.getLength());
			while(iterator.hasNext()) {
				int index = iterator.getCurrentIndex();
				Object o = iterator.next();
				((Object[])result)[index] = new PrimitiveConverter<>(inTools.getArrayType()).toChar(o);
			}
			return (B) result;
		} else {
			Object result = Array.newInstance(outTools.getArrayType(), iterator.getLength());
			for(int i = 1; i < depth; i++)
				result = Array.newInstance(result.getClass(), iterator.getLength());

			while(iterator.hasNext()) {
				int index = iterator.getCurrentIndex();
				Object o = iterator.next();
				((Object[])result)[index] = new PrimitiveConverter<>(inTools.getArrayType())
						.toCharArray(ClassTools.of(Object.class).getArrayTypeName(outTools.getArrayType(), depth - 1), o);
			}
			return (B) result;
		}
	}
	private Character toChar(T in) {
		ClassTools unboxed = this.getInClass().unbox();
		if(!unboxed.isPrimitive() && !unboxed.isSubOf(CharSequence.class))
			throw new NonConversibleTypeException();
		else if(unboxed.get().equals(char.class)) return (Character) in;
		else if(unboxed.isSubOf(CharSequence.class)) return toChar((CharSequence) in);
		else if(unboxed.get().equals(boolean.class)) return toChar((Boolean) in);
		else if(unboxed.get().equals(double.class)) return toChar((Double) in);
		else if(unboxed.get().equals(float.class)) return toChar((Float) in);
		else if(unboxed.get().equals(byte.class)) return toChar((Byte) in);
		else if(unboxed.get().equals(short.class)) return toChar((Short) in);
		else if(unboxed.get().equals(int.class)) return toChar((Integer) in);
		else if(unboxed.get().equals(long.class)) return toChar((Long) in);
		else throw new NonConversibleTypeException();
	}
	private char toChar(CharSequence a) {
		return a.charAt(0);
	}
	private char toChar(boolean a) {
		return (char) (a ? 1 : 0);
	}
	private char toChar(double a) {
		return (char) a;
	}
	private char toChar(float a) {
		return (char) a;
	}
	private char toChar(byte a) {
		return (char) a;
	}
	private char toChar(short a) {
		return (char) a;
	}
	private char toChar(int a) {
		return (char) a;
	}
	private char toChar(long a) {
		return (char) a;
	}

	@SuppressWarnings("unchecked")
	private <B> B toDoubleArray(Class<B> clazz, T array) {
		ClassTools inTools = ClassTools.of(array.getClass());
		if(!inTools.isPrimitiveOrBoxedArray() && !(inTools.isArray() && CharSequence.class.isAssignableFrom(inTools.getArrayType())))
			throw new NonConversibleTypeException();

		int depth = inTools.getArrayDepth();
		PrimitivaArrayIterator iterator = new PrimitivaArrayIterator(array);

		ClassTools outTools = ClassTools.of(clazz);
		if(depth < 1) throw new NonConversibleTypeException();
		if(depth == 1) {
			Object result = Array.newInstance(outTools.getArrayType(), iterator.getLength());
			while(iterator.hasNext()) {
				int index = iterator.getCurrentIndex();
				Object o = iterator.next();
				((Object[])result)[index] = new PrimitiveConverter<>(inTools.getArrayType()).toDouble(o);
			}
			return (B) result;
		} else {
			Object result = Array.newInstance(outTools.getArrayType(), iterator.getLength());
			for(int i = 1; i < depth; i++)
				result = Array.newInstance(result.getClass(), iterator.getLength());

			while(iterator.hasNext()) {
				int index = iterator.getCurrentIndex();
				Object o = iterator.next();
				((Object[])result)[index] = new PrimitiveConverter<>(inTools.getArrayType())
						.toDoubleArray(ClassTools.of(Object.class).getArrayTypeName(outTools.getArrayType(), depth - 1), o);
			}
			return (B) result;
		}
	}
	private Double toDouble(T in) {
		ClassTools unboxed = this.getInClass().unbox();
		if(!unboxed.isPrimitive() && !unboxed.isSubOf(CharSequence.class))
			throw new NonConversibleTypeException();
		else if(unboxed.get().equals(double.class)) return (Double) in;
		else if(unboxed.isSubOf(CharSequence.class)) return toDouble((CharSequence) in);
		else if(unboxed.get().equals(boolean.class)) return toDouble((Boolean) in);
		else if(unboxed.get().equals(char.class)) return toDouble((Character) in);
		else if(unboxed.get().equals(float.class)) return toDouble((Float) in);
		else if(unboxed.get().equals(byte.class)) return toDouble((Byte) in);
		else if(unboxed.get().equals(short.class)) return toDouble((Short) in);
		else if(unboxed.get().equals(int.class)) return toDouble((Integer) in);
		else if(unboxed.get().equals(long.class)) return toDouble((Long) in);
		else throw new NonConversibleTypeException();
	}
	private double toDouble(CharSequence a) {
		return Double.parseDouble((String) a);
	}
	private double toDouble(boolean a) {
		return a ? 1 : 0;
	}
	private double toDouble(char a) {
		return a;
	}
	private double toDouble(float a) {
		return a;
	}
	private double toDouble(byte a) {
		return a;
	}
	private double toDouble(short a) {
		return a;
	}
	private double toDouble(int a) {
		return a;
	}
	private double toDouble(long a) {
		return a;
	}

	@SuppressWarnings("unchecked")
	private <B> B toFloatArray(Class<B> clazz, T array) {
		ClassTools inTools = ClassTools.of(array.getClass());
		if(!inTools.isPrimitiveOrBoxedArray() && !(inTools.isArray() && CharSequence.class.isAssignableFrom(inTools.getArrayType())))
			throw new NonConversibleTypeException();

		int depth = inTools.getArrayDepth();
		PrimitivaArrayIterator iterator = new PrimitivaArrayIterator(array);

		ClassTools outTools = ClassTools.of(clazz);
		if(depth < 1) throw new NonConversibleTypeException();
		if(depth == 1) {
			Object result = Array.newInstance(outTools.getArrayType(), iterator.getLength());
			while(iterator.hasNext()) {
				int index = iterator.getCurrentIndex();
				Object o = iterator.next();
				((Object[])result)[index] = new PrimitiveConverter<>(inTools.getArrayType()).toFloat(o);
			}
			return (B) result;
		} else {
			Object result = Array.newInstance(outTools.getArrayType(), iterator.getLength());
			for(int i = 1; i < depth; i++)
				result = Array.newInstance(result.getClass(), iterator.getLength());

			while(iterator.hasNext()) {
				int index = iterator.getCurrentIndex();
				Object o = iterator.next();
				((Object[])result)[index] = new PrimitiveConverter<>(inTools.getArrayType())
						.toFloatArray(ClassTools.of(Object.class).getArrayTypeName(outTools.getArrayType(), depth - 1), o);
			}
			return (B) result;
		}
	}
	private Float toFloat(T in) {
		ClassTools unboxed = this.getInClass().unbox();
		if(!unboxed.isPrimitive() && !unboxed.isSubOf(CharSequence.class))
			throw new NonConversibleTypeException();
		else if(unboxed.get().equals(float.class)) return (Float) in;
		else if(unboxed.isSubOf(CharSequence.class)) return toFloat((CharSequence) in);
		else if(unboxed.get().equals(boolean.class)) return toFloat((Boolean) in);
		else if(unboxed.get().equals(char.class)) return toFloat((Character) in);
		else if(unboxed.get().equals(double.class)) return toFloat((Double) in);
		else if(unboxed.get().equals(byte.class)) return toFloat((Byte) in);
		else if(unboxed.get().equals(short.class)) return toFloat((Short) in);
		else if(unboxed.get().equals(int.class)) return toFloat((Integer) in);
		else if(unboxed.get().equals(long.class)) return toFloat((Long) in);
		else throw new NonConversibleTypeException();
	}
	private float toFloat(CharSequence a) {
		return Float.parseFloat((String) a);
	}
	private float toFloat(boolean a) {
		return a ? 1 : 0;
	}
	private float toFloat(char a) {
		return a;
	}
	private float toFloat(double a) {
		return (float) a;
	}
	private float toFloat(byte a) {
		return a;
	}
	private float toFloat(short a) {
		return a;
	}
	private float toFloat(int a) {
		return a;
	}
	private float toFloat(long a) {
		return a;
	}

	@SuppressWarnings("unchecked")
	private <B> B toLongArray(Class<B> clazz, T array) {
		ClassTools inTools = ClassTools.of(array.getClass());
		if(!inTools.isPrimitiveOrBoxedArray() && !(inTools.isArray() && CharSequence.class.isAssignableFrom(inTools.getArrayType())))
			throw new NonConversibleTypeException();

		int depth = inTools.getArrayDepth();
		PrimitivaArrayIterator iterator = new PrimitivaArrayIterator(array);

		ClassTools outTools = ClassTools.of(clazz);
		if(depth < 1) throw new NonConversibleTypeException();
		if(depth == 1) {
			Object result = Array.newInstance(outTools.getArrayType(), iterator.getLength());
			while(iterator.hasNext()) {
				int index = iterator.getCurrentIndex();
				Object o = iterator.next();
				((Object[])result)[index] = new PrimitiveConverter<>(inTools.getArrayType()).toLong(o);
			}
			return (B) result;
		} else {
			Object result = Array.newInstance(outTools.getArrayType(), iterator.getLength());
			for(int i = 1; i < depth; i++)
				result = Array.newInstance(result.getClass(), iterator.getLength());

			while(iterator.hasNext()) {
				int index = iterator.getCurrentIndex();
				Object o = iterator.next();
				((Object[])result)[index] = new PrimitiveConverter<>(inTools.getArrayType())
						.toLongArray(ClassTools.of(Object.class).getArrayTypeName(outTools.getArrayType(), depth - 1), o);
			}
			return (B) result;
		}
	}
	private Long toLong(T in) {
		ClassTools unboxed = this.getInClass().unbox();
		if(!unboxed.isPrimitive() && !unboxed.isSubOf(CharSequence.class))
			throw new NonConversibleTypeException();
		else if(unboxed.get().equals(long.class)) return (Long) in;
		else if(unboxed.isSubOf(CharSequence.class)) return toLong((CharSequence) in);
		else if(unboxed.get().equals(boolean.class)) return toLong((Boolean) in);
		else if(unboxed.get().equals(char.class)) return toLong((Character) in);
		else if(unboxed.get().equals(double.class)) return toLong((Double) in);
		else if(unboxed.get().equals(float.class)) return toLong((Float) in);
		else if(unboxed.get().equals(byte.class)) return toLong((Byte) in);
		else if(unboxed.get().equals(short.class)) return toLong((Short) in);
		else if(unboxed.get().equals(int.class)) return toLong((Integer) in);
		else throw new NonConversibleTypeException();
	}
	private long toLong(CharSequence a) {
		return Long.parseLong((String) a);
	}
	private long toLong(boolean a) {
		return a ? 1 : 0;
	}
	private long toLong(char a) {
		return a;
	}
	private long toLong(double a) {
		return (long) a;
	}
	private long toLong(float a) {
		return (long) a;
	}
	private long toLong(byte a) {
		return a;
	}
	private long toLong(short a) {
		return a;
	}
	private long toLong(int a) {
		return a;
	}

	@SuppressWarnings("unchecked")
	private <B> B toIntArray(Class<B> clazz, T array) {
		ClassTools inTools = ClassTools.of(array.getClass());
		if(!inTools.isPrimitiveOrBoxedArray() && !(inTools.isArray() && CharSequence.class.isAssignableFrom(inTools.getArrayType())))
			throw new NonConversibleTypeException();

		int depth = inTools.getArrayDepth();
		PrimitivaArrayIterator iterator = new PrimitivaArrayIterator(array);

		ClassTools outTools = ClassTools.of(clazz);
		if(depth < 1) throw new NonConversibleTypeException();
		if(depth == 1) {
			Object result = Array.newInstance(outTools.getArrayType(), iterator.getLength());
			while(iterator.hasNext()) {
				int index = iterator.getCurrentIndex();
				Object o = iterator.next();
				((Object[])result)[index] = new PrimitiveConverter<>(inTools.getArrayType()).toInt(o);
			}
			return (B) result;
		} else {
			Object result = Array.newInstance(outTools.getArrayType(), iterator.getLength());
			for(int i = 1; i < depth; i++)
				result = Array.newInstance(result.getClass(), iterator.getLength());

			while(iterator.hasNext()) {
				int index = iterator.getCurrentIndex();
				Object o = iterator.next();
				((Object[])result)[index] = new PrimitiveConverter<>(inTools.getArrayType())
						.toIntArray(ClassTools.of(Object.class).getArrayTypeName(outTools.getArrayType(), depth - 1), o);
			}
			return (B) result;
		}
	}
	private Integer toInt(T in) {
		ClassTools unboxed = this.getInClass().unbox();
		if(!unboxed.isPrimitive() && !unboxed.isSubOf(CharSequence.class))
			throw new NonConversibleTypeException();
		else if(unboxed.get().equals(int.class)) return (Integer) in;
		else if(unboxed.isSubOf(CharSequence.class)) return toInt((CharSequence) in);
		else if(unboxed.get().equals(boolean.class)) return toInt((Boolean) in);
		else if(unboxed.get().equals(char.class)) return toInt((Character) in);
		else if(unboxed.get().equals(double.class)) return toInt((Double) in);
		else if(unboxed.get().equals(float.class)) return toInt((Float) in);
		else if(unboxed.get().equals(byte.class)) return toInt((Byte) in);
		else if(unboxed.get().equals(short.class)) return toInt((Short) in);
		else if(unboxed.get().equals(long.class)) return toInt((Long) in);
		else throw new NonConversibleTypeException();
	}
	private int toInt(CharSequence a) {
		return Integer.parseInt((String) a);
	}
	private int toInt(boolean a) {
		return a ? 1 : 0;
	}
	private int toInt(char a) {
		return (int) a;
	}
	private int toInt(double a) {
		return (int) a;
	}
	private int toInt(float a) {
		return (int) a;
	}
	private int toInt(byte a) {
		return a;
	}
	private int toInt(short a) {
		return a;
	}
	private int toInt(long a) {
		return (int) a;
	}

	@SuppressWarnings("unchecked")
	private <B> B toShortArray(Class<B> clazz, T array) {
		ClassTools inTools = ClassTools.of(array.getClass());
		if(!inTools.isPrimitiveOrBoxedArray() && !(inTools.isArray() && CharSequence.class.isAssignableFrom(inTools.getArrayType())))
			throw new NonConversibleTypeException();

		int depth = inTools.getArrayDepth();
		PrimitivaArrayIterator iterator = new PrimitivaArrayIterator(array);

		ClassTools outTools = ClassTools.of(clazz);
		if(depth < 1) throw new NonConversibleTypeException();
		if(depth == 1) {
			Object result = Array.newInstance(outTools.getArrayType(), iterator.getLength());
			while(iterator.hasNext()) {
				int index = iterator.getCurrentIndex();
				Object o = iterator.next();
				((Object[])result)[index] = new PrimitiveConverter<>(inTools.getArrayType()).toShort(o);
			}
			return (B) result;
		} else {
			Object result = Array.newInstance(outTools.getArrayType(), iterator.getLength());
			for(int i = 1; i < depth; i++)
				result = Array.newInstance(result.getClass(), iterator.getLength());

			while(iterator.hasNext()) {
				int index = iterator.getCurrentIndex();
				Object o = iterator.next();
				((Object[])result)[index] = new PrimitiveConverter<>(inTools.getArrayType())
						.toShortArray(ClassTools.of(Object.class).getArrayTypeName(outTools.getArrayType(), depth - 1), o);
			}
			return (B) result;
		}
	}
	private Short toShort(T in) {
		ClassTools unboxed = this.getInClass().unbox();
		if(!unboxed.isPrimitive() && !unboxed.isSubOf(CharSequence.class))
			throw new NonConversibleTypeException();
		else if(unboxed.get().equals(short.class)) return (Short) in;
		else if(unboxed.isSubOf(CharSequence.class)) return toShort((CharSequence) in);
		else if(unboxed.get().equals(boolean.class)) return toShort((Boolean) in);
		else if(unboxed.get().equals(char.class)) return toShort((Character) in);
		else if(unboxed.get().equals(double.class)) return toShort((Double) in);
		else if(unboxed.get().equals(float.class)) return toShort((Float) in);
		else if(unboxed.get().equals(byte.class)) return toShort((Byte) in);
		else if(unboxed.get().equals(int.class)) return toShort((Integer) in);
		else if(unboxed.get().equals(long.class)) return toShort((Long) in);
		else throw new NonConversibleTypeException();
	}
	private short toShort(CharSequence a) {
		return Short.parseShort((String) a);
	}
	private short toShort(boolean a) {
		return (short) (a ? 1 : 0);
	}
	private short toShort(char a) {
		return (short) a;
	}
	private short toShort(double a) {
		return (short) a;
	}
	private short toShort(float a) {
		return (short) a;
	}
	private short toShort(byte a) {
		return a;
	}
	private short toShort(int a) {
		return (short) a;
	}
	private short toShort(long a) {
		return (short) a;
	}

	@SuppressWarnings("unchecked")
	private <B> B toByteArray(Class<B> clazz, T array) {
		ClassTools inTools = ClassTools.of(array.getClass());
		if(!inTools.isPrimitiveOrBoxedArray() && !(inTools.isArray() && CharSequence.class.isAssignableFrom(inTools.getArrayType())))
			throw new NonConversibleTypeException();

		int depth = inTools.getArrayDepth();
		PrimitivaArrayIterator iterator = new PrimitivaArrayIterator(array);

		ClassTools outTools = ClassTools.of(clazz);
		if(depth < 1) throw new NonConversibleTypeException();
		if(depth == 1) {
			Object result = Array.newInstance(outTools.getArrayType(), iterator.getLength());
			while(iterator.hasNext()) {
				int index = iterator.getCurrentIndex();
				Object o = iterator.next();
				((Object[])result)[index] = new PrimitiveConverter<>(inTools.getArrayType()).toByte(o);
			}
			return (B) result;
		} else {
			Object result = Array.newInstance(outTools.getArrayType(), iterator.getLength());
			for(int i = 1; i < depth; i++)
				result = Array.newInstance(result.getClass(), iterator.getLength());

			while(iterator.hasNext()) {
				int index = iterator.getCurrentIndex();
				Object o = iterator.next();
				((Object[])result)[index] = new PrimitiveConverter<>(inTools.getArrayType())
						.toByteArray(ClassTools.of(Object.class).getArrayTypeName(outTools.getArrayType(), depth - 1), o);
			}
			return (B) result;
		}
	}
	private Byte toByte(T in) {
		ClassTools unboxed = this.getInClass().unbox();
		if(!unboxed.isPrimitive() && !unboxed.isSubOf(CharSequence.class))
			throw new NonConversibleTypeException();
		else if(unboxed.get().equals(byte.class)) return (Byte) in;
		else if(unboxed.isSubOf(CharSequence.class)) return toByte((CharSequence) in);
		else if(unboxed.get().equals(boolean.class)) return toByte((Boolean) in);
		else if(unboxed.get().equals(char.class)) return toByte((Character) in);
		else if(unboxed.get().equals(double.class)) return toByte((Double) in);
		else if(unboxed.get().equals(float.class)) return toByte((Float) in);
		else if(unboxed.get().equals(short.class)) return toByte((Short) in);
		else if(unboxed.get().equals(int.class)) return toByte((Integer) in);
		else if(unboxed.get().equals(long.class)) return toByte((Long) in);
		else throw new NonConversibleTypeException();
	}
	private byte toByte(CharSequence a) {
		return Byte.parseByte((String) a);
	}
	private byte toByte(boolean a) {
		return (byte) (a ? 1 : 0);
	}
	private byte toByte(char a) {
		return (byte) a;
	}
	private byte toByte(double a) {
		return (byte) a;
	}
	private byte toByte(float a) {
		return (byte) a;
	}
	private byte toByte(short a) {
		return (byte) a;
	}
	private byte toByte(int a) {
		return (byte) a;
	}
	private byte toByte(long a) {
		return (byte) a;
	}

}
