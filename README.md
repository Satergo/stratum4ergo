# stratum4ergo

Uses Java 17. CLI main class is `com.satergo.stratum4ergo.CLI`. `./gradlew run` to test CLI.

Default node configuration: Node IP 127.0.0.1. Port 9053.

Default pool server configuration: Port 9999.

(Can be changed in the CLI class)

The address / wallet is specified in your node configuration.

## Todo
- Integrate into Satergo (will be very easy to do)
- Write actual example
- Configuration file for CLI
- Split CLI tool from main codebase
- Disconnecting inactive peer
- Maybe support multiple client connections, authorization, banning

## Java API usage
```java
ErgoStratumServer server = new ErgoStratumServer(options);
server.startListening(9999);
```