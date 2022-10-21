package com.satergo.stratum4ergo.message;

import com.satergo.stratum4ergo.BlockTemplate;

import java.math.BigInteger;

import static com.satergo.stratum4ergo.Utils.jsonArray;

public class Announcement {

	public static AnnouncementMessage difficulty(BigInteger difficulty) {
		return new AnnouncementMessage("mining.set_difficulty", () -> jsonArray(difficulty));
	}

	public static AnnouncementMessage miningJob(BlockTemplate blockTemplate) {
		return new AnnouncementMessage("mining.notify", blockTemplate::getJobParams);
	}
}
