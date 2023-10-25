package com.satergo.stratum4ergo;

import com.satergo.stratum4ergo.data.Options;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class CLI {

	public static void main(String[] args) throws IOException {
		Properties properties = new Properties();
		properties.load(new FileReader(args.length == 0 ? "cli.properties" : args[0], StandardCharsets.UTF_8));
		Options options = Options.fromProperties(properties);
		ErgoStratumServer server = new ErgoStratumServer(options);
		int port = Integer.parseInt(properties.getProperty("port"));
		System.out.println("Stratum server starting at port " + port);
		server.startListening(port);
	}
}
