package com.satergo.stratum4ergo;

import com.satergo.stratum4ergo.data.Options;
import com.satergo.stratum4ergo.data.ShareData;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HexFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Pool {

	private final Options options;
	private final ErgoStratumServer server;

	public JobManager jobManager;
	private NodeInterface nodeInterface;

	public Pool(Options options, ErgoStratumServer server) {

		this.options = options;
		this.server = server;

		setupJobManager();
	}

	public void start() throws IOException {
		nodeInterface = new NodeInterface(options.nodeApiUrl());
		if (!nodeInterface.isOnline())
			throw new IllegalStateException("node is offline");
		JSONObject info = nodeInterface.info();
		options.data().protocolVersion = info.getJSONObject("parameters").getInt("blockVersion");
		options.data().difficulty = Utils.getBigInteger(info, "difficulty").multiply(new BigInteger(String.valueOf(options.difficultyMultiplier())));
		setupJobManager();
		getBlockTemplate();
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
			if (getBlockTemplate()) {
				System.out.println("Found block with polling");
			}
		}, 0, options.blockRefreshInterval(), TimeUnit.MILLISECONDS);
	}

	private void setupJobManager() {

		jobManager = new JobManager(options);

		jobManager.addEventListener(JobManagerEvent.NewBlock.class, e -> {
			server.broadcastMiningJob(e.blockTemplate());
		});
		jobManager.addEventListener(JobManagerEvent.UpdatedBlock.class, e -> {
			server.broadcastMiningJob(e.blockTemplate());
		});
		jobManager.addEventListener(JobManagerEvent.Share.class, e -> {
			ShareData shareData = e.shareData();
			var isValidBlock = shareData instanceof ShareData.Success;

			if (isValidBlock) {
				submitBlock(shareData, e.nonce());
				if (getBlockTemplate())
					System.out.println("New block found after submission");
			}
		});
	}

	private void submitBlock(ShareData shareData, byte[] nonce) {
		nodeInterface.solution(HexFormat.of().formatHex(nonce));
	}

	private boolean getBlockTemplate() {
		JSONObject candidate = nodeInterface.miningCandidate();
		candidate.put("height", candidate.getLong("h"));
		candidate.put("version", options.data().protocolVersion); // unused
		return jobManager.processTemplate(candidate);
	}
}
