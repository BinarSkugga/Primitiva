[![Build Status](https://travis-ci.com/BinarSkugga/Primitiva.svg?branch=master)](https://travis-ci.com/BinarSkugga/Primitiva)
# Primitiva
Primitiva is a utils library to alleviate boilerplate code when using the Java reflection
API and the Java primitives. It can go from a collection of primitive to an array and unbox
it in one line or tell you if a type is a primitive array type and much more.

## Usage
### Convert single primitives
```java
public class Dummy {
    public static void main(String[] args) {
    	// Creates a converter that will convert Byte and byte values to any other primitives.
        PrimitivaConverter<Byte> byteConverter = PrimitivaConversion.single(Byte.class);
        
        // Will convert a byte of 45 to a boolean with the value of true.
        // See PrimitivaConverter source code for toBoolean for insight on how this is done.
        Byte b = (byte) 45;
        Boolean bool = byteConverter.convertTo(Boolean.class, b);
    }
}
```

### Convert primitives array
```java
public class Dummy {
    public static void main(String[] args) {
    	// Creates a converter that will convert int[] values to any other array of primitives.
        PrimitivaArrayConverter<int[]> intArrayConverter = PrimitivaConversion.array(int[].class);
        
        // Will convert a int array to a Byte array using standard implicit casting.
        int[] ia = new int[]{ 45, 63, 156, 3 };
        Byte[] ba = intArrayConverter.convertTo(Byte[].class, ia);
    }
}
```

## Installation
### Maven
```xml
<repository>
	<id>primitiva.repo</id>
	<url>https://rawgit.com/binarskugga/primitiva/maven</url>
</repository>
```
```xml
<dependency>
	<groupId>com.binarskugga</groupId>
	<artifactId>primitiva</artifactId>
	<version>1.0</version>
</dependency>
```