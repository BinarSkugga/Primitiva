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
		else if(this.inUnboxedC.equals(boolean.class)) return (Boolean) in;
		else if(CharSequence.class.isAssignableFrom(this.inUnboxedC)) return toBoolean((CharSequence) in);
		else if(this.inUnboxedC.equals(char.class)) return toBoolean((Character) in);
		else if(this.inUnboxedC.equals(double.class)) return toBoolean((Double) in);
		else if(this.inUnboxedC.equals(float.class)) return toBoolean((Float) in);
		else if(this.inUnboxedC.equals(byte.class)) return toBoolean((Byte) in);
		else if(this.inUnboxedC.equals(short.class)) return toBoolean((Short) in);
		else if(this.inUnboxedC.equals(int.class)) return toBoolean((Integer) in);
		else if(this.inUnboxedC.equals(long.class)) return toBoolean((Long) in);
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
		else if(this.inUnboxedC.equals(char.class)) return (Character) in;
		else if(CharSequence.class.isAssignableFrom(this.inUnboxedC)) return toChar((CharSequence) in);
		else if(this.inUnboxedC.equals(boolean.class)) return toChar((Boolean) in);
		else if(this.inUnboxedC.equals(double.class)) return toChar((Double) in);
		else if(this.inUnboxedC.equals(float.class)) return toChar((Float) in);
		else if(this.inUnboxedC.equals(byte.class)) return toChar((Byte) in);
		else if(this.inUnboxedC.equals(short.class)) return toChar((Short) in);
		else if(this.inUnboxedC.equals(int.class)) return toChar((Integer) in);
		else if(this.inUnboxedC.equals(long.class)) return toChar((Long) in);
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
		else if(this.inUnboxedC.equals(double.class)) return (Double) in;
		else if(CharSequence.class.isAssignableFrom(this.inUnboxedC)) return toDouble((CharSequence) in);
		else if(this.inUnboxedC.equals(boolean.class)) return toDouble((Boolean) in);
		else if(this.inUnboxedC.equals(char.class)) return toDouble((Character) in);
		else if(this.inUnboxedC.equals(float.class)) return toDouble((Float) in);
		else if(this.inUnboxedC.equals(byte.class)) return toDouble((Byte) in);
		else if(this.inUnboxedC.equals(short.class)) return toDouble((Short) in);
		else if(this.inUnboxedC.equals(int.class)) return toDouble((Integer) in);
		else if(this.inUnboxedC.equals(long.class)) return toDouble((Long) in);
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
		else if(this.inUnboxedC.equals(float.class)) return (Float) in;
		else if(CharSequence.class.isAssignableFrom(this.inUnboxedC)) return toFloat((CharSequence) in);
		else if(this.inUnboxedC.equals(boolean.class)) return toFloat((Boolean) in);
		else if(this.inUnboxedC.equals(char.class)) return toFloat((Character) in);
		else if(this.inUnboxedC.equals(double.class)) return toFloat((Double) in);
		else if(this.inUnboxedC.equals(byte.class)) return toFloat((Byte) in);
		else if(this.inUnboxedC.equals(short.class)) return toFloat((Short) in);
		else if(this.inUnboxedC.equals(int.class)) return toFloat((Integer) in);
		else if(this.inUnboxedC.equals(long.class)) return toFloat((Long) in);
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
		else if(this.inUnboxedC.equals(long.class)) return (Long) in;
		else if(CharSequence.class.isAssignableFrom(this.inUnboxedC)) return toLong((CharSequence) in);
		else if(this.inUnboxedC.equals(boolean.class)) return toLong((Boolean) in);
		else if(this.inUnboxedC.equals(char.class)) return toLong((Character) in);
		else if(this.inUnboxedC.equals(double.class)) return toLong((Double) in);
		else if(this.inUnboxedC.equals(float.class)) return toLong((Float) in);
		else if(this.inUnboxedC.equals(byte.class)) return toLong((Byte) in);
		else if(this.inUnboxedC.equals(short.class)) return toLong((Short) in);
		else if(this.inUnboxedC.equals(int.class)) return toLong((Integer) in);
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
		else if(this.inUnboxedC.equals(int.class)) return (Integer) in;
		else if(CharSequence.class.isAssignableFrom(this.inUnboxedC)) return toInt((CharSequence) in);
		else if(this.inUnboxedC.equals(boolean.class)) return toInt((Boolean) in);
		else if(this.inUnboxedC.equals(char.class)) return toInt((Character) in);
		else if(this.inUnboxedC.equals(double.class)) return toInt((Double) in);
		else if(this.inUnboxedC.equals(float.class)) return toInt((Float) in);
		else if(this.inUnboxedC.equals(byte.class)) return toInt((Byte) in);
		else if(this.inUnboxedC.equals(short.class)) return toInt((Short) in);
		else if(this.inUnboxedC.equals(long.class)) return toInt((Long) in);
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
		else if(this.inUnboxedC.equals(short.class)) return (Short) in;
		else if(CharSequence.class.isAssignableFrom(this.inUnboxedC)) return toShort((CharSequence) in);
		else if(this.inUnboxedC.equals(boolean.class)) return toShort((Boolean) in);
		else if(this.inUnboxedC.equals(char.class)) return toShort((Character) in);
		else if(this.inUnboxedC.equals(double.class)) return toShort((Double) in);
		else if(this.inUnboxedC.equals(float.class)) return toShort((Float) in);
		else if(this.inUnboxedC.equals(byte.class)) return toShort((Byte) in);
		else if(this.inUnboxedC.equals(int.class)) return toShort((Integer) in);
		else if(this.inUnboxedC.equals(long.class)) return toShort((Long) in);
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
		else if(this.inUnboxedC.equals(byte.class)) return (Byte) in;
		else if(CharSequence.class.isAssignableFrom(this.inUnboxedC)) return toByte((CharSequence) in);
		else if(this.inUnboxedC.equals(boolean.class)) return toByte((Boolean) in);
		else if(this.inUnboxedC.equals(char.class)) return toByte((Character) in);
		else if(this.inUnboxedC.equals(double.class)) return toByte((Double) in);
		else if(this.inUnboxedC.equals(float.class)) return toByte((Float) in);
		else if(this.inUnboxedC.equals(short.class)) return toByte((Short) in);
		else if(this.inUnboxedC.equals(int.class)) return toByte((Integer) in);
		else if(this.inUnboxedC.equals(long.class)) return toByte((Long) in);
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
