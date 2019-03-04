package com.binarskugga.primitiva.conversion;

import com.binarskugga.primitiva.exception.NotPrimitiveException;
import com.binarskugga.primitiva.reflection.PrimitivaReflection;

public class PrimitivaConverter<T> {

	private Class<T> inC;
	private Class inUnboxedC;

	PrimitivaConverter(Class<T> in) {
		this.inC = in;
		if(PrimitivaReflection.isBoxedPrimitive(in))
			this.inUnboxedC = PrimitivaReflection.unbox(in);
		else
			this.inUnboxedC = this.inC;
	}

	@SuppressWarnings("unchecked")
	public <V> V convertTo(Class<V> outC, T in) {
		if((!PrimitivaReflection.isPrimitiveOrBoxed(this.inC) && !CharSequence.class.isAssignableFrom(this.inUnboxedC))
				|| !PrimitivaReflection.isPrimitiveOrBoxed(outC)) throw new NotPrimitiveException();

		Class outUnboxedC = outC;
		if(PrimitivaReflection.isBoxedPrimitiveArray(outC))
			outUnboxedC = PrimitivaReflection.unbox(outC);

		Object result = this.convertPrimitive(outUnboxedC, in);
		return (V) result;
	}

	@SuppressWarnings("unchecked")
	private <V> V convertPrimitive(Class<V> outC, T in) {
		if((!PrimitivaReflection.isPrimitive(this.inUnboxedC) && !CharSequence.class.isAssignableFrom(this.inUnboxedC))
				|| !PrimitivaReflection.isPrimitive(outC)) throw new NotPrimitiveException();
		else if(this.inUnboxedC.equals(outC)) return (V) in;
		else if(outC.equals(boolean.class)) return (V) this.toBoolean(in);
		else if(outC.equals(char.class)) return (V) this.toChar(in);
		else if(outC.equals(double.class)) return (V) this.toDouble(in);
		else if(outC.equals(float.class)) return (V) this.toFloat(in);
		else if(outC.equals(long.class)) return (V) this.toLong(in);
		else if(outC.equals(int.class)) return (V) this.toInt(in);
		else if(outC.equals(short.class)) return (V) this.toShort(in);
		else if(outC.equals(byte.class)) return (V) this.toByte(in);
		else throw new NotPrimitiveException();
	}

	//region Primitive array conversions
	public Boolean toBoolean(T in) {
		if(!PrimitivaReflection.isPrimitive(this.inUnboxedC) && !CharSequence.class.isAssignableFrom(this.inUnboxedC))
			throw new NotPrimitiveException();
		else if(this.inUnboxedC.equals(boolean.class)) return (boolean) in;
		else if(CharSequence.class.isAssignableFrom(this.inUnboxedC)) return toBoolean((CharSequence) in);
		else if(this.inUnboxedC.equals(char.class)) return toBoolean((char) in);
		else if(this.inUnboxedC.equals(double.class)) return toBoolean((double) in);
		else if(this.inUnboxedC.equals(float.class)) return toBoolean((float) in);
		else if(this.inUnboxedC.equals(byte.class)) return toBoolean((byte) in);
		else if(this.inUnboxedC.equals(short.class)) return toBoolean((short) in);
		else if(this.inUnboxedC.equals(int.class)) return toBoolean((int) in);
		else if(this.inUnboxedC.equals(long.class)) return toBoolean((long) in);
		else throw new NotPrimitiveException();
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

	public Character toChar(T in) {
		if(!PrimitivaReflection.isPrimitive(this.inUnboxedC) && !CharSequence.class.isAssignableFrom(this.inUnboxedC))
			throw new NotPrimitiveException();
		else if(this.inUnboxedC.equals(char.class)) return (char) in;
		else if(CharSequence.class.isAssignableFrom(this.inUnboxedC)) return toChar((CharSequence) in);
		else if(this.inUnboxedC.equals(boolean.class)) return toChar((boolean) in);
		else if(this.inUnboxedC.equals(double.class)) return toChar((double) in);
		else if(this.inUnboxedC.equals(float.class)) return toChar((float) in);
		else if(this.inUnboxedC.equals(byte.class)) return toChar((byte) in);
		else if(this.inUnboxedC.equals(short.class)) return toChar((short) in);
		else if(this.inUnboxedC.equals(int.class)) return toChar((int) in);
		else if(this.inUnboxedC.equals(long.class)) return toChar((long) in);
		else throw new NotPrimitiveException();
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

	public Double toDouble(T in) {
		if(!PrimitivaReflection.isPrimitive(this.inUnboxedC) && !CharSequence.class.isAssignableFrom(this.inUnboxedC))
			throw new NotPrimitiveException();
		else if(this.inUnboxedC.equals(double.class)) return (double) in;
		else if(CharSequence.class.isAssignableFrom(this.inUnboxedC)) return toDouble((CharSequence) in);
		else if(this.inUnboxedC.equals(boolean.class)) return toDouble((boolean) in);
		else if(this.inUnboxedC.equals(char.class)) return toDouble((char) in);
		else if(this.inUnboxedC.equals(float.class)) return toDouble((float) in);
		else if(this.inUnboxedC.equals(byte.class)) return toDouble((byte) in);
		else if(this.inUnboxedC.equals(short.class)) return toDouble((short) in);
		else if(this.inUnboxedC.equals(int.class)) return toDouble((int) in);
		else if(this.inUnboxedC.equals(long.class)) return toDouble((long) in);
		else throw new NotPrimitiveException();
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

	public Float toFloat(T in) {
		if(!PrimitivaReflection.isPrimitive(this.inUnboxedC) && !CharSequence.class.isAssignableFrom(this.inUnboxedC))
			throw new NotPrimitiveException();
		else if(this.inUnboxedC.equals(float.class)) return (float) in;
		else if(CharSequence.class.isAssignableFrom(this.inUnboxedC)) return toFloat((CharSequence) in);
		else if(this.inUnboxedC.equals(boolean.class)) return toFloat((boolean) in);
		else if(this.inUnboxedC.equals(char.class)) return toFloat((char) in);
		else if(this.inUnboxedC.equals(double.class)) return toFloat((double) in);
		else if(this.inUnboxedC.equals(byte.class)) return toFloat((byte) in);
		else if(this.inUnboxedC.equals(short.class)) return toFloat((short) in);
		else if(this.inUnboxedC.equals(int.class)) return toFloat((int) in);
		else if(this.inUnboxedC.equals(long.class)) return toFloat((long) in);
		else throw new NotPrimitiveException();
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

	public Long toLong(T in) {
		if(!PrimitivaReflection.isPrimitive(this.inUnboxedC) && !CharSequence.class.isAssignableFrom(this.inUnboxedC))
			throw new NotPrimitiveException();
		else if(this.inUnboxedC.equals(long.class)) return (long) in;
		else if(CharSequence.class.isAssignableFrom(this.inUnboxedC)) return toLong((CharSequence) in);
		else if(this.inUnboxedC.equals(boolean.class)) return toLong((boolean) in);
		else if(this.inUnboxedC.equals(char.class)) return toLong((char) in);
		else if(this.inUnboxedC.equals(double.class)) return toLong((double) in);
		else if(this.inUnboxedC.equals(float.class)) return toLong((float) in);
		else if(this.inUnboxedC.equals(byte.class)) return toLong((byte) in);
		else if(this.inUnboxedC.equals(short.class)) return toLong((short) in);
		else if(this.inUnboxedC.equals(int.class)) return toLong((int) in);
		else throw new NotPrimitiveException();
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

	public Integer toInt(T in) {
		if(!PrimitivaReflection.isPrimitive(this.inUnboxedC) && !CharSequence.class.isAssignableFrom(this.inUnboxedC))
			throw new NotPrimitiveException();
		else if(this.inUnboxedC.equals(int.class)) return (int) in;
		else if(CharSequence.class.isAssignableFrom(this.inUnboxedC)) return toInt((CharSequence) in);
		else if(this.inUnboxedC.equals(boolean.class)) return toInt((boolean) in);
		else if(this.inUnboxedC.equals(char.class)) return toInt((char) in);
		else if(this.inUnboxedC.equals(double.class)) return toInt((double) in);
		else if(this.inUnboxedC.equals(float.class)) return toInt((float) in);
		else if(this.inUnboxedC.equals(byte.class)) return toInt((byte) in);
		else if(this.inUnboxedC.equals(short.class)) return toInt((short) in);
		else if(this.inUnboxedC.equals(long.class)) return toInt((long) in);
		else throw new NotPrimitiveException();
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

	public Short toShort(T in) {
		if(!PrimitivaReflection.isPrimitive(this.inUnboxedC) && !CharSequence.class.isAssignableFrom(this.inUnboxedC))
			throw new NotPrimitiveException();
		else if(this.inUnboxedC.equals(short.class)) return (short) in;
		else if(CharSequence.class.isAssignableFrom(this.inUnboxedC)) return toShort((CharSequence) in);
		else if(this.inUnboxedC.equals(boolean.class)) return toShort((boolean) in);
		else if(this.inUnboxedC.equals(char.class)) return toShort((char) in);
		else if(this.inUnboxedC.equals(double.class)) return toShort((double) in);
		else if(this.inUnboxedC.equals(float.class)) return toShort((float) in);
		else if(this.inUnboxedC.equals(byte.class)) return toShort((byte) in);
		else if(this.inUnboxedC.equals(int.class)) return toShort((int) in);
		else if(this.inUnboxedC.equals(long.class)) return toShort((long) in);
		else throw new NotPrimitiveException();
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

	public Byte toByte(T in) {
		if(!PrimitivaReflection.isPrimitive(this.inUnboxedC) && !CharSequence.class.isAssignableFrom(this.inUnboxedC))
			throw new NotPrimitiveException();
		else if(this.inUnboxedC.equals(byte.class)) return (byte) in;
		else if(CharSequence.class.isAssignableFrom(this.inUnboxedC)) return toByte((CharSequence) in);
		else if(this.inUnboxedC.equals(boolean.class)) return toByte((boolean) in);
		else if(this.inUnboxedC.equals(char.class)) return toByte((char) in);
		else if(this.inUnboxedC.equals(double.class)) return toByte((double) in);
		else if(this.inUnboxedC.equals(float.class)) return toByte((float) in);
		else if(this.inUnboxedC.equals(short.class)) return toByte((short) in);
		else if(this.inUnboxedC.equals(int.class)) return toByte((int) in);
		else if(this.inUnboxedC.equals(long.class)) return toByte((long) in);
		else throw new NotPrimitiveException();
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
	//endregion

}
