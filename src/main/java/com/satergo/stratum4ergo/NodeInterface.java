package com.satergo.stratum4ergo;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NodeInterface {

	private HttpRequest.Builder req(String path) {
		return HttpRequest.newBuilder(baseURI.resolve(path)).header("User-Agent", "stratum4ergo 1.0.0");
	}

	private final HttpClient http = HttpClient.newHttpClient();
	private final URI baseURI;

	public NodeInterface(String apiAddress) {
		baseURI = URI.create(apiAddress);
	}

	public boolean isOnline() {
		try {
			return http.send(req("/info").build(), HttpResponse.BodyHandlers.discarding()).statusCode() == 200;
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public JSONObject info() throws IOException {
		try {
			return new JSONObject(http.send(req("/info").build(), HttpResponse.BodyHandlers.ofString()).body());
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean isAddressValid(String address) {
		try {
			return new JSONObject(http.send(req("/utils/address/" + address).build(), HttpResponse.BodyHandlers.ofString()).body()).getBoolean("isValid");
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public JSONObject miningCandidate() {
		try {
			return new JSONObject(http.send(req("/mining/candidate").build(), HttpResponse.BodyHandlers.ofString()).body());
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean solution(String nonce) {
		try {
			JSONObject postBody = new JSONObject();
			postBody.put("n", nonce);
			HttpResponse<String> response = http.send(req("/mining/solution").POST(HttpRequest.BodyPublishers.ofString(postBody.toString())).build(), HttpResponse.BodyHandlers.ofString());
			return response.statusCode() == 200;
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
