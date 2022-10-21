package com.satergo.stratum4ergo.data;

import java.math.BigInteger;

public record Options(int extraNonce1Size,
					  boolean multiplyDifficulty, BigInteger minDiff, BigInteger maxDiff, long targetTime, long retargetTime, double variance, long difficultyMultiplier,
					  long connectionTimeout, long blockRefreshInterval, // ms
					  String nodeApiUrl,
					  InitStats data) {

	public Options {
		if (!nodeApiUrl.endsWith("/"))
			throw new IllegalArgumentException("nodeApiUrl must end with a slash");
	}
}
