# simple-rest-war

Wildfly tries to export opentelemetry traces despite setting sampler-type to `off`. This occurs only when CDI is enabled in the deployed war.

```bash
./mvnw clean package
unzip -d ~/tools ~/Downloads/wildfly-39.0.1.Final.zip
~/tools/wildfly-39.0.1.Final/bin/standalone.sh
~/tools/wildfly-39.0.1.Final/bin/jboss-cli.sh --connect --file=opentelemetry-off.cli
```
Restart

```bash
wildfly-39.0.1.Final/bin/standalone.sh
cp target/test.war ~/tools/wildfly-39.0.1.Final/standalone/deployments
```

The war deploys, and Wildfly tries to export opentelemetry traces despite explicitly setting sampler-type to `off`.

```
12:41:36,051 WARNING [io.smallrye.opentelemetry.implementation.exporters.sender.VertxGrpcSender] (vert.x-eventloop-thread-2) Failed to export LogsRequestMarshalers. The request could not be executed. Full error message: Connection refused: localhost/127.0.0.1:4317
12:41:36,053 SEVERE [io.opentelemetry.exporter.internal.grpc.GrpcExporter] (vert.x-eventloop-thread-2) Failed to export logs. The request could not be executed. Error message: Connection refused: localhost/127.0.0.1:4317: io.netty.channel.AbstractChannel$AnnotatedConnectException: Connection refused: localhost/127.0.0.1:4317
	Suppressed: java.lang.IllegalStateException: Retries exhausted: 3/3
		at io.smallrye.reactive.mutiny@2.8.0//io.smallrye.mutiny.helpers.ExponentialBackoff$1.lambda$apply$0(ExponentialBackoff.java:46)
		at io.smallrye.reactive.mutiny@2.8.0//io.smallrye.mutiny.groups.MultiOnItem.lambda$transformToUni$6(MultiOnItem.java:268)
```

```bash
~/tools/wildfly-39.0.1.Final/bin/jboss-cli.sh --connect                             
[standalone@localhost:9990 /] /subsystem=opentelemetry:read-attribute(name=sampler-type,resolve-expressions=true
{
"outcome" => "success",
"result" => "off"
}
``` 
