package com.satergo.stratum4ergo.data;

import com.satergo.stratum4ergo.Utils;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.HexFormat;

public record MiningCandidate(byte[] msg, long height, int version, BigInteger b) {

	public static MiningCandidate fromJson(JSONObject obj, int version) {
		return new MiningCandidate(
				HexFormat.of().parseHex(obj.getString("msg")),
				obj.getInt("h"),
				version,
				obj.has("b") ? Utils.getBigInteger(obj, "b") : null);
	}
}
