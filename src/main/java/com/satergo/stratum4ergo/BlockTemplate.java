package com.satergo.stratum4ergo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;

import static com.satergo.stratum4ergo.Utils.jsonArray;

public class BlockTemplate {

	private static final BigInteger DIFF_1 = new BigInteger("00000000ffff0000000000000000000000000000000000000000000000000000", 16);

	private record Submission(byte[] extraNonce1, byte[] extraNonce2, String nTime, byte[] nonce) {
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof Submission that)) return false;
			return Arrays.equals(extraNonce1, that.extraNonce1) && Arrays.equals(extraNonce2, that.extraNonce2) && nTime.equals(that.nTime) && Arrays.equals(nonce, that.nonce);
		}

		@Override
		public int hashCode() {
			int result = Objects.hash(nTime);
			result = 31 * result + Arrays.hashCode(extraNonce1);
			result = 31 * result + Arrays.hashCode(extraNonce2);
			result = 31 * result + Arrays.hashCode(nonce);
			return result;
		}
	}

	private final Set<Object> submissions = new HashSet<>();

	public BlockTemplate(String jobId, JSONObject rpcData) {
		this.jobId = jobId;
		this.rpcData = rpcData;
		this.target = rpcData.has("b") ? Utils.getBigInteger(rpcData, "b") : new BigInteger(rpcData.getString("bits"), 16);
		this.difficulty = new BigDecimal(DIFF_1).divide(new BigDecimal(target), RoundingMode.DOWN).setScale(2, RoundingMode.DOWN);
		this.msg = HexFormat.of().parseHex(rpcData.getString("msg"));
	}

	public JSONObject rpcData;
	public String jobId;
	public BigInteger target;
	public BigDecimal difficulty;
	public byte[] msg;

	public byte[] serializeCoinbase(byte[] extraNonce1, byte[] extraNonce2) {
		return Utils.concat(msg, extraNonce1, extraNonce2);
	}

	public boolean registerSubmit(byte[] extraNonce1, byte[] extraNonce2, String nTime, byte[] nonce) {
		return submissions.add(new Submission(extraNonce1, extraNonce2, nTime, nonce));
	}

	private JSONArray jobParams;

	public JSONArray getJobParams() {
		if (jobParams != null) return jobParams;
		return jobParams = jsonArray(
				jobId,
				rpcData.getLong("height"),
				rpcData.getString("msg"),
				"",
				"",
				rpcData.getInt("version"),
				Utils.getBigInteger(rpcData, "b"),
				"",
				true
		);
	}
}
