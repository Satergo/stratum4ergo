# stratum4ergo

Uses Java 17. The CLI main class is `com.satergo.stratum4ergo.CLI`. `./gradlew run` to test run using the CLI.

Default node configuration: Node IP 127.0.0.1. Port 9053.

Default pool server configuration: Port 9999.

CLI configuration can be changed in [cli.properties](cli.properties).

The wallet payout address is specified in the configuration of your node (ergo.node.miningPubKeyHex).

## Todo
- Write an actual example
- Disconnecting inactive peer
- Maybe support multiple client connections, authorization, banning

## Java API usage
```java
ErgoStratumServer server = new ErgoStratumServer(options);
server.startListening(9999);
```