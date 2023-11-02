# stratum4ergo

Uses Java 17. The CLI main class is `com.satergo.stratum4ergo.CLI`. `./gradlew run` to test run using the CLI.

CLI configuration can be changed in [cli.properties](cli.properties).

The wallet payout address is specified in the configuration of your node, like this:
```hocon
ergo {
	node {
		mining = true
		useExternalMiner = true
		// the hex encoding of the content of your address, can be obtained using the node API endpoint /utils/addressToRaw
		miningPubKeyHex = ""
	}
}
```

The node will need to have its wallet initialized.

## Todo
- Write an actual example
- Disconnecting inactive peer
- Maybe support multiple client connections, authorization, banning

## Java API usage
```java
ErgoStratumServer server = new ErgoStratumServer(options);
server.startListening(4444);
```