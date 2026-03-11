# simple-rest-war

Wildfly tries to export opentelemetry traces despite setting sampler-type to `off`. This occurs only when CDI is enabled in the deployed war.

```bash
./mvnw clean package
curl -L  https://github.com/wildfly/wildfly/releases/download/39.0.1.Final/wildfly-39.0.1.Final.zip -o /tmp/wildfly-39.0.1.Final.zip
unzip -d target /tmp/wildfly-39.0.1.Final.zip
target/wildfly-39.0.1.Final/bin/standalone.sh

# from another terminal
target/wildfly-39.0.1.Final/bin/jboss-cli.sh --connect --file=opentelemetry-off.cli
```
Restart

```bash
target/wildfly-39.0.1.Final/bin/standalone.sh

# from another terminal
cp target/test.war target/wildfly-39.0.1.Final/standalone/deployments
```

The war deploys, and Wildfly starts to export opentelemetry traces.

```
12:41:36,051 WARNING [io.smallrye.opentelemetry.implementation.exporters.sender.VertxGrpcSender] (vert.x-eventloop-thread-2) Failed to export LogsRequestMarshalers. The request could not be executed. Full error message: Connection refused: localhost/127.0.0.1:4317
12:41:36,053 SEVERE [io.opentelemetry.exporter.internal.grpc.GrpcExporter] (vert.x-eventloop-thread-2) Failed to export logs. The request could not be executed. Error message: Connection refused: localhost/127.0.0.1:4317: io.netty.channel.AbstractChannel$AnnotatedConnectException: Connection refused: localhost/127.0.0.1:4317
	Suppressed: java.lang.IllegalStateException: Retries exhausted: 3/3
```

[Full stack trace](error.log)

## Check the sampler-type setting

```bash
target/wildfly-39.0.1.Final/bin/jboss-cli.sh --connect
[standalone@localhost:9990 /] /subsystem=opentelemetry:read-attribute(name=sampler-type,resolve-expressions=true)
{
"outcome" => "success",
"result" => "off"
}
```
