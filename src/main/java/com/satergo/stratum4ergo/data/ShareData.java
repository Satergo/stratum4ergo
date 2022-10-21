package com.satergo.stratum4ergo.data;

import java.math.BigDecimal;
import java.math.BigInteger;

public sealed interface ShareData permits ShareData.Fail, ShareData.Success {
	String jobId();
	String ipAddress();
	String workerName();
	BigInteger difficulty();

	record Fail(String jobId, String ipAddress, String workerName, BigInteger difficulty, String error) implements ShareData {}

	record Success(String jobId, String ipAddress, String workerName, BigInteger difficulty, long height, long blockReward, String msg,
				   long shareDiff, boolean blockDiff, BigDecimal blockDiffActual, byte[] blockHash, boolean blockHashInvalid) implements ShareData {}
}