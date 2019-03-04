package com.binarskugga.primitiva.conversion;

import com.binarskugga.primitiva.exception.NotPrimitiveException;
import com.binarskugga.primitiva.reflection.PrimitivaReflection;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;

public class PrimitivaArrayConverter<T> {

	private Class inC;
	private Class<T> inUnboxedC;
	@Setter private String separator = ",";

	@SuppressWarnings("unchecked")
	PrimitivaArrayConverter(Class in) {
		this.inC = in;
		if(PrimitivaReflection.isBoxedPrimitiveArray(in))
			this.inUnboxedC = PrimitivaReflection.unbox(in);
		else
			this.inUnboxedC = this.inC;
	}

	@SuppressWarnings("unchecked")
	public <V> V convertTo(Class<V> outC, T in) {
		if(!PrimitivaReflection.isPrimitiveArrayOrBoxed(this.inC) || !PrimitivaReflection.isPrimitiveArrayOrBoxed(outC)
				|| !CharSequence.class.isAssignableFrom(this.inC) || !this.inC.equals(String[].class))
			throw new NotPrimitiveException();

		Object inUnboxed = in;
		if(PrimitivaReflection.isBoxedPrimitiveArray(this.inC)) {
			inUnboxed = unboxArray(this.inC, in);
		}

		Class outUnboxedC = outC;
		boolean wasBoxed = false;
		if(PrimitivaReflection.isBoxedPrimitiveArray(outC)) {
			outUnboxedC = PrimitivaReflection.unbox(outC);
			wasBoxed = true;
		}

		Object result = this.convertPrimitiveArray(outUnboxedC, (T) inUnboxed);
		return (V) (wasBoxed ? boxArray(outUnboxedC, result) : result);
	}

	@SuppressWarnings("unchecked")
	private <V> V convertPrimitiveArray(Class<V> outC, T in) {
		if(!PrimitivaReflection.isPrimitiveArray(this.inUnboxedC) || !PrimitivaReflection.isPrimitiveArray(outC)
				|| !CharSequence.class.isAssignableFrom(this.inUnboxedC) || !this.inUnboxedC.equals(String[].class))
			throw new NotPrimitiveException();
		else if(this.inUnboxedC.equals(outC)) return (V) in;
		else if(outC.equals(boolean[].class)) return (V) this.toBoolean(in);
		else if(outC.equals(char[].class)) return (V) this.toChar(in);
		else if(outC.equals(double[].class)) return (V) this.toDouble(in);
		else if(outC.equals(float[].class)) return (V) this.toFloat(in);
		else if(outC.equals(long[].class)) return (V) this.toLong(in);
		else if(outC.equals(int[].class)) return (V) this.toInt(in);
		else if(outC.equals(short[].class)) return (V) this.toShort(in);
		else if(outC.equals(byte[].class)) return (V) this.toByte(in);
		else throw new NotPrimitiveException();
	}

	//region Array boxing & unboxing
	public <T> Object unboxArray(Class<T> inC, T in) {
		if(!PrimitivaReflection.isBoxedPrimitiveArray(inC)) throw new NotPrimitiveException();
		else if(inC.equals(Boolean[].class))
			return ArrayUtils.toPrimitive((Boolean[]) in);
		else if(inC.equals(Character[].class))
			return ArrayUtils.toPrimitive((Character[]) in);
		else if(inC.equals(Double[].class))
			return ArrayUtils.toPrimitive((Double[]) in);
		else if(inC.equals(Float[].class))
			return ArrayUtils.toPrimitive((Float[]) in);
		else if(inC.equals(Long[].class))
			return ArrayUtils.toPrimitive((Long[]) in);
		else if(inC.equals(Integer[].class))
			return ArrayUtils.toPrimitive((Integer[]) in);
		else if(inC.equals(Short[].class))
			return ArrayUtils.toPrimitive((Short[]) in);
		else if(inC.equals(Byte[].class))
			return ArrayUtils.toPrimitive((Byte[]) in);
		else
			throw new NotPrimitiveException();
	}

	public <T> Object boxArray(Class<T> inC, T in) {
		if(!PrimitivaReflection.isPrimitiveArray(inC)) throw new NotPrimitiveException();
		else if(inC.equals(boolean[].class))
			return ArrayUtils.toObject((boolean[]) in);
		else if(inC.equals(char[].class))
			return ArrayUtils.toObject((char[]) in);
		else if(inC.equals(double[].class))
			return ArrayUtils.toObject((double[]) in);
		else if(inC.equals(float[].class))
			return ArrayUtils.toPrimitive((float[]) in);
		else if(inC.equals(long[].class))
			return ArrayUtils.toObject((long[]) in);
		else if(inC.equals(int[].class))
			return ArrayUtils.toObject((int[]) in);
		else if(inC.equals(short[].class))
			return ArrayUtils.toObject((short[]) in);
		else if(inC.equals(byte[].class))
			return ArrayUtils.toObject((byte[]) in);
		else
			throw new NotPrimitiveException();
	}
	//endregion

	//region Primitive array conversions
	public <T> boolean[] toBoolean(T in) {
		if(!PrimitivaReflection.isPrimitiveArray(this.inUnboxedC) && !CharSequence.class.isAssignableFrom(this.inUnboxedC)
				&& !this.inUnboxedC.equals(String[].class)) throw new NotPrimitiveException();
		else if(this.inUnboxedC.equals(boolean[].class)) return (boolean[]) in;
		else if(this.inUnboxedC.equals(String[].class)) return toBoolean((String[]) in);
		else if(CharSequence.class.isAssignableFrom(this.inUnboxedC)) return toBoolean((CharSequence) in);
		else if(this.inUnboxedC.equals(char[].class)) return toBoolean((char[]) in);
		else if(this.inUnboxedC.equals(double[].class)) return toBoolean((double[]) in);
		else if(this.inUnboxedC.equals(float[].class)) return toBoolean((float[]) in);
		else if(this.inUnboxedC.equals(byte[].class)) return toBoolean((byte[]) in);
		else if(this.inUnboxedC.equals(short[].class)) return toBoolean((short[]) in);
		else if(this.inUnboxedC.equals(int[].class)) return toBoolean((int[]) in);
		else if(this.inUnboxedC.equals(long[].class)) return toBoolean((long[]) in);
		else throw new NotPrimitiveException();
	}
	public boolean[] toBoolean(CharSequence a) {
		String[] splitted = ((String) a).split(this.separator);
		return this.toBoolean(splitted);
	}
	public boolean[] toBoolean(String[] a) {
		boolean[] b = new boolean[a.length];
		for(int i = 0; i < a.length; i++) b[i] = a[i].equalsIgnoreCase("true");
		return b;
	}
	public boolean[] toBoolean(char[] a) {
		boolean[] b = new boolean[a.length];
		for(int i = 0; i < a.length; i++) b[i] = a[i] > 0;
		return b;
	}
	public boolean[] toBoolean(double[] a) {
		boolean[] b = new boolean[a.length];
		for(int i = 0; i < a.length; i++) b[i] = a[i] > 0;
		return b;
	}
	public boolean[] toBoolean(float[] a) {
		boolean[] b = new boolean[a.length];
		for(int i = 0; i < a.length; i++) b[i] = a[i] > 0;
		return b;
	}
	public boolean[] toBoolean(byte[] a) {
		boolean[] b = new boolean[a.length];
		for(int i = 0; i < a.length; i++) b[i] = a[i] > 0;
		return b;
	}
	public boolean[] toBoolean(short[] a) {
		boolean[] b = new boolean[a.length];
		for(int i = 0; i < a.length; i++) b[i] = a[i] > 0;
		return b;
	}
	public boolean[] toBoolean(int[] a) {
		boolean[] b = new boolean[a.length];
		for(int i = 0; i < a.length; i++) b[i] = a[i] > 0;
		return b;
	}
	public boolean[] toBoolean(long[] a) {
		boolean[] b = new boolean[a.length];
		for(int i = 0; i < a.length; i++) b[i] = a[i] > 0;
		return b;
	}

	public <T> char[] toChar(T in) {
		if(!PrimitivaReflection.isPrimitiveArray(this.inUnboxedC) && !CharSequence.class.isAssignableFrom(this.inUnboxedC)
				&& !this.inUnboxedC.equals(String[].class)) throw new NotPrimitiveException();
		else if(this.inUnboxedC.equals(char[].class)) return (char[]) in;
		else if(this.inUnboxedC.equals(String[].class)) return toChar((String[]) in);
		else if(CharSequence.class.isAssignableFrom(this.inUnboxedC)) return toChar((CharSequence) in);
		else if(this.inUnboxedC.equals(boolean[].class)) return toChar((boolean[]) in);
		else if(this.inUnboxedC.equals(double[].class)) return toChar((double[]) in);
		else if(this.inUnboxedC.equals(float[].class)) return toChar((float[]) in);
		else if(this.inUnboxedC.equals(byte[].class)) return toChar((byte[]) in);
		else if(this.inUnboxedC.equals(short[].class)) return toChar((short[]) in);
		else if(this.inUnboxedC.equals(int[].class)) return toChar((int[]) in);
		else if(this.inUnboxedC.equals(long[].class)) return toChar((long[]) in);
		else throw new NotPrimitiveException();
	}
	public char[] toChar(CharSequence a) {
		String[] splitted = ((String) a).split(this.separator);
		return this.toChar(splitted);
	}
	public char[] toChar(String[] a) {
		char[] c = new char[a.length];
		for(int i = 0; i < a.length; i++) c[i] = a[i].charAt(0);
		return c;
	}
	public char[] toChar(boolean[] a) {
		char[] c = new char[a.length];
		for(int i = 0; i < a.length; i++) c[i] = (char) (a[i] ? 1 : 0);
		return c;
	}
	public char[] toChar(double[] a) {
		char[] c = new char[a.length];
		for(int i = 0; i < a.length; i++) c[i] = (char) a[i];
		return c;
	}
	public char[] toChar(float[] a) {
		char[] c = new char[a.length];
		for(int i = 0; i < a.length; i++) c[i] = (char) a[i];
		return c;
	}
	public char[] toChar(byte[] a) {
		char[] c = new char[a.length];
		for(int i = 0; i < a.length; i++) c[i] = (char) a[i];
		return c;
	}
	public char[] toChar(short[] a) {
		char[] c = new char[a.length];
		for(int i = 0; i < a.length; i++) c[i] = (char) a[i];
		return c;
	}
	public char[] toChar(int[] a) {
		char[] c = new char[a.length];
		for(int i = 0; i < a.length; i++) c[i] = (char) a[i];
		return c;
	}
	public char[] toChar(long[] a) {
		char[] c = new char[a.length];
		for(int i = 0; i < a.length; i++) c[i] = (char) a[i];
		return c;
	}

	public <T> double[] toDouble(T in) {
		if(!PrimitivaReflection.isPrimitiveArray(this.inUnboxedC) && !CharSequence.class.isAssignableFrom(this.inUnboxedC)
				&& !this.inUnboxedC.equals(String[].class)) throw new NotPrimitiveException();
		else if(this.inUnboxedC.equals(double[].class)) return (double[]) in;
		else if(this.inUnboxedC.equals(String[].class)) return toDouble((String[]) in);
		else if(CharSequence.class.isAssignableFrom(this.inUnboxedC)) return toDouble((CharSequence) in);
		else if(this.inUnboxedC.equals(boolean[].class)) return toDouble((boolean[]) in);
		else if(this.inUnboxedC.equals(char[].class)) return toDouble((char[]) in);
		else if(this.inUnboxedC.equals(float[].class)) return toDouble((float[]) in);
		else if(this.inUnboxedC.equals(byte[].class)) return toDouble((byte[]) in);
		else if(this.inUnboxedC.equals(short[].class)) return toDouble((short[]) in);
		else if(this.inUnboxedC.equals(int[].class)) return toDouble((int[]) in);
		else if(this.inUnboxedC.equals(long[].class)) return toDouble((long[]) in);
		else throw new NotPrimitiveException();
	}
	public double[] toDouble(CharSequence a) {
		String[] splitted = ((String) a).split(this.separator);
		return this.toDouble(splitted);
	}
	public double[] toDouble(String[] a) {
		double[] d = new double[a.length];
		for(int i = 0; i < a.length; i++) d[i] = Double.parseDouble(a[i]);
		return d;
	}
	public double[] toDouble(boolean[] a) {
		double[] d = new double[a.length];
		for(int i = 0; i < a.length; i++) d[i] = a[i] ? 1.0d : 0.0d;
		return d;
	}
	public double[] toDouble(char[] a) {
		double[] d = new double[a.length];
		for(int i = 0; i < a.length; i++) d[i] = a[i];
		return d;
	}
	public double[] toDouble(float[] a) {
		double[] d = new double[a.length];
		for(int i = 0; i < a.length; i++) d[i] = a[i];
		return d;
	}
	public double[] toDouble(byte[] a) {
		double[] d = new double[a.length];
		for(int i = 0; i < a.length; i++) d[i] = a[i];
		return d;
	}
	public double[] toDouble(short[] a) {
		double[] d = new double[a.length];
		for(int i = 0; i < a.length; i++) d[i] = a[i];
		return d;
	}
	public double[] toDouble(int[] a) {
		double[] d = new double[a.length];
		for(int i = 0; i < a.length; i++) d[i] = a[i];
		return d;
	}
	public double[] toDouble(long[] a) {
		double[] d = new double[a.length];
		for(int i = 0; i < a.length; i++) d[i] = a[i];
		return d;
	}

	public <T> float[] toFloat(T in) {
		if(!PrimitivaReflection.isPrimitiveArray(this.inUnboxedC) && !CharSequence.class.isAssignableFrom(this.inUnboxedC)
				&& !this.inUnboxedC.equals(String[].class)) throw new NotPrimitiveException();
		else if(this.inUnboxedC.equals(float[].class)) return (float[]) in;
		else if(this.inUnboxedC.equals(String[].class)) return toFloat((String[]) in);
		else if(CharSequence.class.isAssignableFrom(this.inUnboxedC)) return toFloat((CharSequence) in);
		else if(this.inUnboxedC.equals(boolean[].class)) return toFloat((boolean[]) in);
		else if(this.inUnboxedC.equals(char[].class)) return toFloat((char[]) in);
		else if(this.inUnboxedC.equals(double[].class)) return toFloat((double[]) in);
		else if(this.inUnboxedC.equals(byte[].class)) return toFloat((byte[]) in);
		else if(this.inUnboxedC.equals(short[].class)) return toFloat((short[]) in);
		else if(this.inUnboxedC.equals(int[].class)) return toFloat((int[]) in);
		else if(this.inUnboxedC.equals(long[].class)) return toFloat((long[]) in);
		else throw new NotPrimitiveException();
	}
	public float[] toFloat(CharSequence a) {
		String[] splitted = ((String) a).split(this.separator);
		return this.toFloat(splitted);
	}
	public float[] toFloat(String[] a) {
		float[] f = new float[a.length];
		for(int i = 0; i < a.length; i++) f[i] = Float.parseFloat(a[i]);
		return f;
	}
	public float[] toFloat(boolean[] a) {
		float[] f = new float[a.length];
		for(int i = 0; i < a.length; i++) f[i] = a[i] ? 1.0f : 0.0f;
		return f;
	}
	public float[] toFloat(char[] a) {
		float[] f = new float[a.length];
		for(int i = 0; i < a.length; i++) f[i] = a[i];
		return f;
	}
	public float[] toFloat(double[] a) {
		float[] f = new float[a.length];
		for(int i = 0; i < a.length; i++) f[i] = (float) a[i];
		return f;
	}
	public float[] toFloat(byte[] a) {
		float[] f = new float[a.length];
		for(int i = 0; i < a.length; i++) f[i] = a[i];
		return f;
	}
	public float[] toFloat(short[] a) {
		float[] f = new float[a.length];
		for(int i = 0; i < a.length; i++) f[i] = a[i];
		return f;
	}
	public float[] toFloat(int[] a) {
		float[] f = new float[a.length];
		for(int i = 0; i < a.length; i++) f[i] = a[i];
		return f;
	}
	public float[] toFloat(long[] a) {
		float[] f = new float[a.length];
		for(int i = 0; i < a.length; i++) f[i] = a[i];
		return f;
	}

	public <T> long[] toLong(T in) {
		if(!PrimitivaReflection.isPrimitiveArray(this.inUnboxedC) && !CharSequence.class.isAssignableFrom(this.inUnboxedC)
				&& !this.inUnboxedC.equals(String[].class)) throw new NotPrimitiveException();
		else if(this.inUnboxedC.equals(long[].class)) return (long[]) in;
		else if(this.inUnboxedC.equals(String[].class)) return toLong((String[]) in);
		else if(CharSequence.class.isAssignableFrom(this.inUnboxedC)) return toLong((CharSequence) in);
		else if(this.inUnboxedC.equals(boolean[].class)) return toLong((boolean[]) in);
		else if(this.inUnboxedC.equals(char[].class)) return toLong((char[]) in);
		else if(this.inUnboxedC.equals(double[].class)) return toLong((double[]) in);
		else if(this.inUnboxedC.equals(float[].class)) return toLong((float[]) in);
		else if(this.inUnboxedC.equals(byte[].class)) return toLong((byte[]) in);
		else if(this.inUnboxedC.equals(short[].class)) return toLong((short[]) in);
		else if(this.inUnboxedC.equals(int[].class)) return toLong((int[]) in);
		else throw new NotPrimitiveException();
	}
	public long[] toLong(CharSequence a) {
		String[] splitted = ((String) a).split(this.separator);
		return this.toLong(splitted);
	}
	public long[] toLong(String[] a) {
		long[] l = new long[a.length];
		for(int i = 0; i < a.length; i++) l[i] = Long.parseLong(a[i]);
		return l;
	}
	public long[] toLong(boolean[] a) {
		long[] l = new long[a.length];
		for(int i = 0; i < a.length; i++) l[i] = a[i] ? 1 : 0;
		return l;
	}
	public long[] toLong(char[] a) {
		long[] l = new long[a.length];
		for(int i = 0; i < a.length; i++) l[i] = a[i];
		return l;
	}
	public long[] toLong(double[] a) {
		long[] l = new long[a.length];
		for(int i = 0; i < a.length; i++) l[i] = (long) a[i];
		return l;
	}
	public long[] toLong(float[] a) {
		long[] l = new long[a.length];
		for(int i = 0; i < a.length; i++) l[i] = (long) a[i];
		return l;
	}
	public long[] toLong(byte[] a) {
		long[] l = new long[a.length];
		for(int i = 0; i < a.length; i++) l[i] = a[i];
		return l;
	}
	public long[] toLong(short[] a) {
		long[] l = new long[a.length];
		for(int i = 0; i < a.length; i++) l[i] = a[i];
		return l;
	}
	public long[] toLong(int[] a) {
		long[] l = new long[a.length];
		for(int i = 0; i < a.length; i++) l[i] = a[i];
		return l;
	}

	public <T> int[] toInt(T in) {
		if(!PrimitivaReflection.isPrimitiveArray(this.inUnboxedC) && !CharSequence.class.isAssignableFrom(this.inUnboxedC)
				&& !this.inUnboxedC.equals(String[].class)) throw new NotPrimitiveException();
		else if(this.inUnboxedC.equals(int[].class)) return (int[]) in;
		else if(this.inUnboxedC.equals(String[].class)) return toInt((String[]) in);
		else if(CharSequence.class.isAssignableFrom(this.inUnboxedC)) return toInt((CharSequence) in);
		else if(this.inUnboxedC.equals(boolean[].class)) return toInt((boolean[]) in);
		else if(this.inUnboxedC.equals(char[].class)) return toInt((char[]) in);
		else if(this.inUnboxedC.equals(double[].class)) return toInt((double[]) in);
		else if(this.inUnboxedC.equals(float[].class)) return toInt((float[]) in);
		else if(this.inUnboxedC.equals(byte[].class)) return toInt((byte[]) in);
		else if(this.inUnboxedC.equals(short[].class)) return toInt((short[]) in);
		else if(this.inUnboxedC.equals(long[].class)) return toInt((long[]) in);
		else throw new NotPrimitiveException();
	}
	public int[] toInt(CharSequence a) {
		CharSequence[] splitted = ((String) a).split(this.separator);
		return this.toInt(splitted);
	}
	public int[] ToInt(String[] a) {
		int[] s = new int[a.length];
		for(int i = 0; i < a.length; i++) s[i] = Integer.parseInt(a[i]);
		return s;
	}
	public int[] toInt(boolean[] a) {
		int[] in = new int[a.length];
		for(int i = 0; i < a.length; i++) in[i] = a[i] ? 1 : 0;
		return in;
	}
	public int[] toInt(char[] a) {
		int[] in = new int[a.length];
		for(int i = 0; i < a.length; i++) in[i] = a[i];
		return in;
	}
	public int[] toInt(double[] a) {
		int[] in = new int[a.length];
		for(int i = 0; i < a.length; i++) in[i] = (int) a[i];
		return in;
	}
	public int[] toInt(float[] a) {
		int[] in = new int[a.length];
		for(int i = 0; i < a.length; i++) in[i] = (int) a[i];
		return in;
	}
	public int[] toInt(byte[] a) {
		int[] in = new int[a.length];
		for(int i = 0; i < a.length; i++) in[i] = a[i];
		return in;
	}
	public int[] toInt(short[] a) {
		int[] in = new int[a.length];
		for(int i = 0; i < a.length; i++) in[i] = a[i];
		return in;
	}
	public int[] toInt(long[] a) {
		int[] in = new int[a.length];
		for(int i = 0; i < a.length; i++) in[i] = (int) a[i];
		return in;
	}

	public <T> short[] toShort(T in) {
		if(!PrimitivaReflection.isPrimitiveArray(this.inUnboxedC) && !CharSequence.class.isAssignableFrom(this.inUnboxedC)
				&& !this.inUnboxedC.equals(String[].class)) throw new NotPrimitiveException();
		else if(this.inUnboxedC.equals(short[].class)) return (short[]) in;
		else if(this.inUnboxedC.equals(String[].class)) return toShort((String[]) in);
		else if(CharSequence.class.isAssignableFrom(this.inUnboxedC)) return toShort((CharSequence) in);
		else if(this.inUnboxedC.equals(boolean[].class)) return toShort((boolean[]) in);
		else if(this.inUnboxedC.equals(char[].class)) return toShort((char[]) in);
		else if(this.inUnboxedC.equals(double[].class)) return toShort((double[]) in);
		else if(this.inUnboxedC.equals(float[].class)) return toShort((float[]) in);
		else if(this.inUnboxedC.equals(byte[].class)) return toShort((byte[]) in);
		else if(this.inUnboxedC.equals(int[].class)) return toShort((int[]) in);
		else if(this.inUnboxedC.equals(long[].class)) return toShort((long[]) in);
		else throw new NotPrimitiveException();
	}
	public short[] toShort(CharSequence a) {
		CharSequence[] splitted = ((String) a).split(this.separator);
		return this.toShort(splitted);
	}
	public short[] toShort(String[] a) {
		short[] s = new short[a.length];
		for(int i = 0; i < a.length; i++) s[i] = Short.parseShort(a[i]);
		return s;
	}
	public short[] toShort(boolean[] a) {
		short[] s = new short[a.length];
		for(int i = 0; i < a.length; i++) s[i] = (short) (a[i] ? 1 : 0);
		return s;
	}
	public short[] toShort(char[] a) {
		short[] s = new short[a.length];
		for(int i = 0; i < a.length; i++) s[i] = (short) a[i];
		return s;
	}
	public short[] toShort(double[] a) {
		short[] s = new short[a.length];
		for(int i = 0; i < a.length; i++) s[i] = (short) a[i];
		return s;
	}
	public short[] toShort(float[] a) {
		short[] s = new short[a.length];
		for(int i = 0; i < a.length; i++) s[i] = (short) a[i];
		return s;
	}
	public short[] toShort(byte[] a) {
		short[] s = new short[a.length];
		for(int i = 0; i < a.length; i++) s[i] = a[i];
		return s;
	}
	public short[] toShort(int[] a) {
		short[] s = new short[a.length];
		for(int i = 0; i < a.length; i++) s[i] = (short) a[i];
		return s;
	}
	public short[] toShort(long[] a) {
		short[] s = new short[a.length];
		for(int i = 0; i < a.length; i++) s[i] = (short) a[i];
		return s;
	}

	public <T> byte[] toByte(T in) {
		if(!PrimitivaReflection.isPrimitiveArray(this.inUnboxedC) && !CharSequence.class.isAssignableFrom(this.inUnboxedC)
				&& !this.inUnboxedC.equals(String[].class)) throw new NotPrimitiveException();
		else if(this.inUnboxedC.equals(byte[].class)) return (byte[]) in;
		else if(this.inUnboxedC.equals(String[].class)) return toByte((String[]) in);
		else if(CharSequence.class.isAssignableFrom(this.inUnboxedC)) return toByte((CharSequence) in);
		else if(this.inUnboxedC.equals(boolean[].class)) return toByte((boolean[]) in);
		else if(this.inUnboxedC.equals(char[].class)) return toByte((char[]) in);
		else if(this.inUnboxedC.equals(double[].class)) return toByte((double[]) in);
		else if(this.inUnboxedC.equals(float[].class)) return toByte((float[]) in);
		else if(this.inUnboxedC.equals(short[].class)) return toByte((short[]) in);
		else if(this.inUnboxedC.equals(int[].class)) return toByte((int[]) in);
		else if(this.inUnboxedC.equals(long[].class)) return toByte((long[]) in);
		else throw new NotPrimitiveException();
	}
	public byte[] toByte(CharSequence a) {
		CharSequence[] splitted = ((String) a).split(this.separator);
		return this.toByte(splitted);
	}
	public byte[] toByte(String[] a) {
		byte[] b = new byte[a.length];
		for(int i = 0; i < a.length; i++) b[i] = Byte.parseByte(a[i]);
		return b;
	}
	public byte[] toByte(boolean[] a) {
		byte[] b = new byte[a.length];
		for(int i = 0; i < a.length; i++) b[i] = (byte) (a[i] ? 1 : 0);
		return b;
	}
	public byte[] toByte(char[] a) {
		byte[] b = new byte[a.length];
		for(int i = 0; i < a.length; i++) b[i] = (byte) a[i];
		return b;
	}
	public byte[] toByte(double[] a) {
		byte[] b = new byte[a.length];
		for(int i = 0; i < a.length; i++) b[i] = (byte) a[i];
		return b;
	}
	public byte[] toByte(float[] a) {
		byte[] b = new byte[a.length];
		for(int i = 0; i < a.length; i++) b[i] = (byte) a[i];
		return b;
	}
	public byte[] toByte(short[] a) {
		byte[] b = new byte[a.length];
		for(int i = 0; i < a.length; i++) b[i] = (byte) a[i];
		return b;
	}
	public byte[] toByte(int[] a) {
		byte[] b = new byte[a.length];
		for(int i = 0; i < a.length; i++) b[i] = (byte) a[i];
		return b;
	}
	public byte[] toByte(long[] a) {
		byte[] b = new byte[a.length];
		for(int i = 0; i < a.length; i++) b[i] = (byte) a[i];
		return b;
	}
	//endregion

}
