package com.satergo.stratum4ergo;

import org.bouncycastle.jcajce.provider.digest.Blake2b;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HexFormat;

public class Utils {

	public static JSONArray jsonArray(Object... content) {
		JSONArray array = new JSONArray();
		for (Object o : content) {
			array.put(o);
		}
		return array;
	}

	public static byte[] sha256(byte[] data) {
		try {
			return MessageDigest.getInstance("SHA-256").digest(data);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] sha256d(byte[] data) {
		return sha256(sha256(data));
	}

	public static byte[] concat(byte[]... arrays) {
		byte[] merged = new byte[Arrays.stream(arrays).mapToInt(a -> a.length).sum()];
		int pos = 0;
		for (byte[] array : arrays) {
			System.arraycopy(array, 0, merged, pos, array.length);
			pos += array.length;
		}
		return merged;
	}

	public static byte[] hexPart(String s, int bytes) {
		return HexFormat.of().parseHex(s, 0, bytes * 2);
	}

	public static void reverse(ByteBuffer buffer) {
		if (buffer.capacity() % 2 != 0) throw new IllegalArgumentException();

		for (int i = 0; i < buffer.capacity() / 2; i++) {
			byte b = buffer.get(i);
			buffer.put(i, buffer.get(buffer.capacity() - 1 - i));
			buffer.put(buffer.capacity() - 1 - i, b);
		}
	}

	public static ByteBuffer varInt(long n) {
		if (n < 0xfdL)
			return ByteBuffer.allocate(1).order(ByteOrder.LITTLE_ENDIAN)
					.put((byte) n);
		else if (n <= 0xffffL)
			return ByteBuffer.allocate(3).order(ByteOrder.LITTLE_ENDIAN)
					.put((byte) 0xfd).putShort((short) n);
		else if (n <= 0xffffffffL)
			return ByteBuffer.allocate(5).order(ByteOrder.LITTLE_ENDIAN)
					.put((byte) 0xfe).putInt((int) n);
		else
			return ByteBuffer.allocate(9).order(ByteOrder.LITTLE_ENDIAN)
					.put((byte) 0xff).putShort((short) n);
	}

	public static byte[] uint256BufferFromHash(String hex) {
		byte[] fromHex = HexFormat.of().parseHex(hex);

		if (fromHex.length != 32){
			fromHex = Arrays.copyOf(fromHex, 32);
		}

		reverse(ByteBuffer.wrap(fromHex));

		return fromHex;
	}

	/** @apiNote big-endian */
	public static byte[] bytes(int value) {
		return new byte[] {
				(byte)(value >> 24),
				(byte)(value >> 16),
				(byte)(value >> 8),
				(byte)value};
	}

	/** @apiNote big-endian */
	public static byte[] longBytes(long value) {
		return new byte[] {
				(byte)(value >> 56),
				(byte)(value >> 48),
				(byte)(value >> 40),
				(byte)(value >> 32),
				(byte)(value >> 24),
				(byte)(value >> 16),
				(byte)(value >> 8),
				(byte)value
		};
	}

	/** @apiNote little-endian */
	public static byte[] longBytesLittle(long value) {
		return new byte[] {
				(byte)value,
				(byte)(value >> 8),
				(byte)(value >> 16),
				(byte)(value >> 24),
				(byte)(value >> 32),
				(byte)(value >> 40),
				(byte)(value >> 48),
				(byte)(value >> 56)
		};
	}

	/** @apiNote big-endian */
	public static long longValue(byte[] bytes) {
		long value = 0L;

		for (byte b : bytes) {
			value = (value << 8) + (b & 255);
		}

		return value;
	}

	public static byte[] blake2b256(byte[] input) {
		return new Blake2b.Blake2b256().digest(input);
	}

	public static byte[] padStart(byte[] bytes, int targetLength) {
		int needed = targetLength - bytes.length;
		if (needed == 0) return bytes;
		if (needed < 0) throw new IllegalArgumentException("byte array is larger than target");
		byte[] b = new byte[targetLength];
		System.arraycopy(bytes, 0, b, targetLength - bytes.length, bytes.length);
		return b;
	}

	public static BigInteger getBigInteger(JSONObject obj, String key) throws NumberFormatException {
		Object val = obj.get(key);
		if (val instanceof Long l) return BigInteger.valueOf(l);
		else if (val instanceof Integer i) return BigInteger.valueOf(i);
		else if (val instanceof String s) return new BigInteger(s);
		throw new IllegalArgumentException("invalid type");
	}
}
