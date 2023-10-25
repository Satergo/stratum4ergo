package com.satergo.stratum4ergo.data;

import java.util.Properties;

public record Options(int extraNonce1Size,
					  long difficultyMultiplier,
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
				Long.parseLong(properties.getProperty("difficultyMultiplier")),
				Long.parseLong(properties.getProperty("connectionTimeout")),
				Long.parseLong(properties.getProperty("blockRefreshInterval")),
				properties.getProperty("nodeApiUrl"),
				new Data()
		);
	}
}
