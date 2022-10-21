package com.satergo.stratum4ergo.data;

import java.math.BigInteger;
import java.util.Properties;

public record Options(int extraNonce1Size,
					  boolean multiplyDifficulty, BigInteger minDiff, BigInteger maxDiff, long targetTime, long retargetTime, double variance, long difficultyMultiplier,
					  long connectionTimeout, long blockRefreshInterval, // ms
					  String nodeApiUrl,
					  Data data) {

	public Options {
		if (!nodeApiUrl.endsWith("/"))
			throw new IllegalArgumentException("nodeApiUrl must end with a slash");
	}

	public static Options fromProperties(Properties properties) {
		return new Options(
				Integer.parseInt(properties.getProperty("extraNonce1Size")),
				Boolean.parseBoolean(properties.getProperty("multiplyDifficulty")),
				new BigInteger(properties.getProperty("minDiff")),
				new BigInteger(properties.getProperty("maxDiff")),
				Long.parseLong(properties.getProperty("targetTime")),
				Long.parseLong(properties.getProperty("retargetTime")),
				Double.parseDouble(properties.getProperty("variance")),
				Long.parseLong(properties.getProperty("difficultyMultiplier")),
				Long.parseLong(properties.getProperty("connectionTimeout")),
				Long.parseLong(properties.getProperty("blockRefreshInterval")),
				properties.getProperty("nodeApiUrl"),
				new Data()
		);
	}
}
