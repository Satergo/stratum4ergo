package com.satergo.stratum4ergo.message;

import com.redbottledesign.bitcoin.rpc.stratum.message.ResponseMessage;
import com.redbottledesign.bitcoin.rpc.stratum.message.Result;

import static com.satergo.stratum4ergo.Utils.jsonArray;

public class Response {

	public static ResponseMessage subscribe(String id, String subscriptionId, String extraNonce1, int extraNonce2Size) {
		return new ResponseMessage(id, () ->
				jsonArray(
					jsonArray(
							jsonArray("mining.set_difficulty", subscriptionId),
							jsonArray("mining.notify", subscriptionId)
					),
					extraNonce1,
					extraNonce2Size
				)
		);
	}

	public static ResponseMessage authorize(String id, boolean authorized, String error) {
		return new ResponseMessage(id, () -> authorized, error);
	}

	public static ResponseMessage submit(String id, Result result, String error) {
		return new ResponseMessage(id, result, error);
	}
}
