# Quarkus: Introduction of New Method for Quickly Launching Java applications on  Container

*Please refer to the following article such kind of person who think that starting Java applications on the Docker environment is slow?*

***```Java Application started only 0.005 sec in my env!!```***   

Last week(March 7, 2019), Red Hat announced the new Technology as  [Quarkus](https://quarkus.io).  
I tried this Technology. I felt that, In fact, I was waiting this until now and it was which I expected! From now on, it seems to be one of the attention technologies for me.  
If you are running Java applications on Docker/k8s, please take a look at this article and try it.  

```
Quick summary of Quarkus:
It will create Linux Native binary using GraalVM which can create 
the binary from Java Source Code. After created the Linux Native 
binary, it copy into the Docker Image and run the binary.

It is a technology that can greatly shorten the startup time 
which was the subject of the Java application so far.
```  

Note:  
In this article, I don't care about the detail of [GraalVM](https://www.graalvm.org/). If you don't know it, please refer to the original Web Site of [GraalVM](https://www.graalvm.org/)?  


## Preparation

* Obtain GraalVM from [https://github.com/oracle/graal](https://github.com/oracle/graal)
* Download & Install JDK 8+ or latest version
* Obtain Maven/Gradle

After got the above GraalVM, please specify the environment variable of  GRAALVM_HOME?

For example, please add the following on .bash_profile or so on?

```
GRAALVM_HOME=/Library/Java/JavaVirtualMachines/graalvm-ce-1.0.0-rc13/Contents/Home
export GRAALVM_HOME
```

## Evaluation of Quarkus  

### 1. Create new Java project according to Quarkus's quick start

Please execute the following command to create a new Project of Quarkus?

```
$ mvn io.quarkus:quarkus-maven-plugin:0.11.0:create \
    -DprojectGroupId=org.acme \
    -DprojectArtifactId=getting-started \
    -DclassName="org.acme.quickstart.GreetingResource" \
    -Dpath="/hello"
```

After executed the command, you can see following directories and files.

```
├── pom.xml
└── src
    ├── main
    │   ├── docker
    │   │   └── Dockerfile
    │   ├── java
    │   │   └── org
    │   │       └── acme
    │   │           └── quickstart
    │   │               └── GreetingResource.java
    │   └── resources
    │       └── META-INF
    │           ├── microprofile-config.properties
    │           └── resources
    │               └── index.html
    └── test
        └── java
            └── org
                └── acme
                    └── quickstart
                        ├── GreetingResourceTest.java
                        └── NativeGreetingResourceIT.java
```


### 2. Create Linux Native Binary from Java Source Code

Please execute the following command to build the Java Source Code as well creat the Linux Native Binary.

```
$ mvn package -Pnative -Dnative-image.docker-build=true
```

Note:  
***-Dnative-image.docker-build=true*** is the option which create the Linux binary on Docker. If you don't specify the option, it will create the native binary for which you build the environment. (Ex: if you compile it on Mac OS/X, it will create Mac OS/X Binary not Linux).

#### 2.1 Confirmation of created Linux Binary

After finished all build process, the binary and other artifact will be stored on ***target*** directory.

```
$ ls -l target/getting-started-1.0-SNAPSHOT-runner
-rwxr-xr-x 1 yoterada staff 20112760 3 11 13:52 getting-started-1.0-SNAPSHOT-runner
```  

#### 2.2 Confirmation of binary by using file command

Not only ls command but also please execute file command? After executed it, you can see look like following result. It showed that it was created ELF binary of Linux.

```
$ file getting-started-1.0-SNAPSHOT-runner
getting-started-1.0-SNAPSHOT-runner: ELF 64-bit LSB executable, 
x86-64, version 1 (SYSV), dynamically linked, interpreter /lib64/ld-linux-x86-64.so.2, 
for GNU/Linux 2.6.32, BuildID[sha1]=61ef9e78267993b688b9cf2de04e2aff9f1a4bfa, 
with debug_info, not stripped
```

### 3.  Build Docker Image

You can see and use the Dockerfile on src/main/docker directory which was automatiaclly created on the creation of new project.

***Note:***  
You can confrim that it copy the Linux ELF Binary on the line of 17.

***Auto generated Dockerfile is as follows:***

```
####
# Before building the docker image run:
#
# mvn package -Pnative -Dnative-image.docker-build=true
#
# Then, build the image with:
#
# docker build -f src/main/docker/Dockerfile -t quarkus/getting-started .
#
# Then run the container using:
#
# docker run -i --rm -p 8080:8080 quarkus/getting-started
#
###
FROM registry.fedoraproject.org/fedora-minimal
WORKDIR /work/
COPY target/*-runner /work/application
RUN chmod 775 /work
EXPOSE 8080
CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]
```

Create the Container Image from Dockerfile.

```
$ docker build -f src/main/docker/Dockerfile -t tyoshi2002/quarkus-quickstart:1.0 .
```

###4. Run Docker Image

Please run the image by using following command?

```
$ docker run -i --rm -p 8080:8080 tyoshi2002/quarkus-quickstart:1.0
2019-03-11 05:10:42,697 INFO [io.quarkus] (main) Quarkus 0.11.0 started in 0.005s. Listening on: http://0.0.0.0:8080
2019-03-11 05:10:42,699 INFO [io.quarkus] (main) Installed features: [cdi, resteasy]
```

***Note:***  
***In my environment, it could start the Java Application with only 0.005 sec which support REST and CDI!!***  

```
Quarkus 0.11.0 started in 0.005s. Listening on: http://0.0.0.0:8080
```

###5. Confirmation of Running Application

In order to confirm the application running correctly, please execute the curl command or access to the URL by using Web Browser?

```
$ curl http://localhost:8080/hello
hello
```

###6. Remarks
Quarkus also has a functionality of Development Mode, which allows for Hot Deployment during development.
To perform Hot Deployment, please execute the following command?

```
$ mvn compile quarkus:dev
```

After executed the above command, please try to modify the source code of Java and reloade the browser again? You can confirm the modified code without restart the process.


###7. Note

Internal Implementation of CDI in Quarkus (called ArC) is not fully compliant with the specifications of CDI, it is a subset implementing some specifications. Below is a list of functions on support and unsupported functions on CDI, please check here.

<A HREF="https://quarkus.io/guides/cdi-reference.html#supported_features">Supported CDI Functionality</A>
<A HREF="https://quarkus.io/guides/cdi-reference.html#limitations">CDI  Limited Functionality</A>

###8. Extension

You can add additional extensions for your Quarkus applications. You can refer to the list of all extensions as follows.

```
$ mvn quarkus:list-extensions
Picked up JAVA_TOOL_OPTIONS: -Dfile.encoding=UTF-8
[INFO] Scanning for projects...
[INFO] 
[INFO] ----------------------< org.acme:getting-started >----------------------
[INFO] Building getting-started 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- quarkus-maven-plugin:0.11.0:list-extensions (default-cli) @ getting-started ---
[INFO] Available extensions:
[INFO] 	 * Agroal - Database connection pool (io.quarkus:quarkus-agroal)
[INFO] 	 * Arc (io.quarkus:quarkus-arc)
[INFO] 	 * AWS Lambda (io.quarkus:quarkus-amazon-lambda)
[INFO] 	 * Camel Core (io.quarkus:quarkus-camel-core)
[INFO] 	 * Camel Infinispan (io.quarkus:quarkus-camel-infinispan)
[INFO] 	 * Camel Netty4 HTTP (io.quarkus:quarkus-camel-netty4-http)
[INFO] 	 * Camel Salesforce (io.quarkus:quarkus-camel-salesforce)
[INFO] 	 * Eclipse Vert.x (io.quarkus:quarkus-vertx)
[INFO] 	 * Hibernate ORM (io.quarkus:quarkus-hibernate-orm)
[INFO] 	 * Hibernate ORM with Panache (io.quarkus:quarkus-hibernate-orm-panache)
[INFO] 	 * Hibernate Validator (io.quarkus:quarkus-hibernate-validator)
[INFO] 	 * Infinispan Client (io.quarkus:quarkus-infinispan-client)
[INFO] 	 * JDBC Driver - H2 (io.quarkus:quarkus-jdbc-h2)
[INFO] 	 * JDBC Driver - MariaDB (io.quarkus:quarkus-jdbc-mariadb)
[INFO] 	 * JDBC Driver - PostgreSQL (io.quarkus:quarkus-jdbc-postgresql)
[INFO] 	 * Kotlin (io.quarkus:quarkus-kotlin)
[INFO] 	 * Narayana JTA - Transaction manager (io.quarkus:quarkus-narayana-jta)
[INFO] 	 * RESTEasy (io.quarkus:quarkus-resteasy)
[INFO] 	 * RESTEasy - JSON-B (io.quarkus:quarkus-resteasy-jsonb)
[INFO] 	 * Scheduler (io.quarkus:quarkus-scheduler)
[INFO] 	 * Security (io.quarkus:quarkus-elytron-security)
[INFO] 	 * SmallRye Fault Tolerance (io.quarkus:quarkus-smallrye-fault-tolerance)
[INFO] 	 * SmallRye Health (io.quarkus:quarkus-smallrye-health)
[INFO] 	 * SmallRye JWT (io.quarkus:quarkus-smallrye-jwt)
[INFO] 	 * SmallRye Metrics (io.quarkus:quarkus-smallrye-metrics)
[INFO] 	 * SmallRye OpenAPI (io.quarkus:quarkus-smallrye-openapi)
[INFO] 	 * SmallRye OpenTracing (io.quarkus:quarkus-smallrye-opentracing)
[INFO] 	 * SmallRye Reactive Messaging (io.quarkus:quarkus-smallrye-reactive-messaging)
[INFO] 	 * SmallRye Reactive Messaging - Kafka Connector (io.quarkus:quarkus-smallrye-reactive-messaging-kafka)
[INFO] 	 * SmallRye Reactive Streams Operators (io.quarkus:quarkus-smallrye-reactive-streams-operators)
[INFO] 	 * SmallRye Reactive Type Converters (io.quarkus:quarkus-smallrye-reactive-type-converters)
[INFO] 	 * SmallRye REST Client (io.quarkus:quarkus-smallrye-rest-client)
[INFO] 	 * Spring DI compatibility layer (io.quarkus:quarkus-spring-di)
[INFO] 	 * Undertow (io.quarkus:quarkus-undertow)
[INFO] 	 * Undertow WebSockets (io.quarkus:quarkus-undertow-websockets)
[INFO] 
Add an extension to your project by adding the dependency to your project or use `mvn quarkus:add-extension -Dextensions="name"`
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 1.696 s
[INFO] Finished at: 2019-03-11T19:45:32+09:00
[INFO] ------------------------------------------------------------------------
```

Based on the above list, you can add and specify the groupId and artifactId of the extensions to be added.

```
$ mvn quarkus:add-extension -Dextensions="groupId:artifactId"　
```

For example, In order to add the Health Check Functionality
```
$ mvn quarkus:add-extension -Dextensions="io.quarkus:quarkus-smallrye-health"　
```

###9. Finally

I feel that this technology is very good overall. Not only the above functionalities but also it integread very useful functionality like.

* Asynchronous processing by using CompletionStage 
* JUnit's test using <A HREF="http://rest-assured.io/"> REST Assured </A> 

It was Cool!!
If you are interested, please try it!

<a href="http://aka.ms/azure-java-jp">Java on Azure Related Contents</a>