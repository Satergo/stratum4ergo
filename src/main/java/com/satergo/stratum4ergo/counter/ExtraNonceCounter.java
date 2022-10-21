package com.satergo.stratum4ergo.counter;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.HexFormat;

public class ExtraNonceCounter {

	public final int size;

	private int counter;

	public ExtraNonceCounter(int size) {
		if (size < 1 || size > 4)
			throw new IllegalArgumentException("size must be in range [1-4)");
		this.size = size;
		counter = new SecureRandom().nextInt() << 27;
	}

	public ExtraNonceCounter() {
		this(4);
	}

	public String next() {
		return HexFormat.of().formatHex(ByteBuffer.allocate(4).putInt(Math.abs(counter++)).array()).substring(8 - 2 * size);
	}

}
