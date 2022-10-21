package com.satergo.stratum4ergo.counter;

import com.satergo.stratum4ergo.Utils;

import java.util.HexFormat;

public class SubscriptionIdCounter {

	private long count;

	public String next() {
		count++;
		if (count == Long.MAX_VALUE)
			count = 1;
		return "deadbeefcafebabe" + HexFormat.of().formatHex(Utils.longBytesLittle(count));
	}
}
