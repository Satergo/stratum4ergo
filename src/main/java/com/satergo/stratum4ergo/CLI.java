package com.satergo.stratum4ergo;

import com.satergo.stratum4ergo.data.InitStats;
import com.satergo.stratum4ergo.data.Options;

import java.io.IOException;
import java.math.BigInteger;

public class CLI {

	public static final Options options = new Options(
			4,
			true, new BigInteger("8"), new BigInteger("16431986528747520"), 15000, 10000, 0.3, (long) Math.pow(2, 8),
			60000, 1000,
			"http://213.239.193.208:9053/",
			new InitStats());

	public static void main(String[] args) throws IOException {
		ErgoStratumServer server = new ErgoStratumServer(options);
		server.startListening(9999);
	}
}
