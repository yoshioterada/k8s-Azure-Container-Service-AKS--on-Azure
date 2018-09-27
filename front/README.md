# MicroProfile Hello Azure Sample

This is a sample Eclipse [MicroProfile](https://microprofile.io) project that exposes one REST API using JAX-RS, and enables the Health API.

Diving into the `src` directory, we will eventually discover the `Application` class reproduced below:

```java
@ApplicationPath("/api")
public class Application extends javax.ws.rs.core.Application { }
```

The `@ApplicationPath("/api")` annotation specifies the base endpoint for this microservice - that is, that all endpoints will have `/api` preceed the rest of the URL required to access any specific REST endpoint.

Inside the `api` package is a class named `API`, which contains the following code:

```java
@ApplicationScoped
@Path("/api")
public class API {

  @GET
  @Path("/hello")
  @Produces(TEXT_PLAIN)
  public String info() {
    return "Hello, Azure!";
  }
}
```

Through the use of the `@Path("/hello")` annotation, we can see that this REST endpoint, when combined with the `/api` specified in the `Application` class, will be `/api/hello`. When this endpoint is called using an HTTP GET request, we can see that the method will produce text/plain, and in fact it is simply a hard-coded string "Hello, Azure!".  We have now covered all the code required to create a microservice using MicroProfile. We can now use Apache Maven to build and package the WAR file.

## Build and Package

This project can be built, packaged, and deployed to any MicroProfile implementation that is compatible with MicroProfile 1.4+.

1. Run `mvn clean package` and wait until it successfully completes.

A WAR file with a timestamp of `yyMMdd-HH` appended to filename will be generated under `target/microprofile-hello-azure-yyMMdd-HH.war` every time you run `mvn package`.

An uber JAR will also be created by default using Payara Micro as the implementation. This will be located under `target/microprofile-hello-azure-yyMMdd-HH-microbundle.jar`.

## Run locally

To run this project locally, can use Payara Micro, one of the implementations of MicroProfile.

1. Run `mvn package payara-micro:start`.

1. Try accessing [localhost:8080/api/hello](http://localhost:8080/api/hello) and [localhost:8080/health](http://localhost:8080/health) in your web browser. If you see the expected "Hello, Azure!" response (and health-related information for the [/health](http://localhost:8080/health) endpoint), you have successfully deployed the MicroProfile application on your local machine.

1. (Optional) You can also run the uber JAR directly with `java -jar target/microprofile-hello-azure-*-microbundle.jar`.

## Deploy to Azure

For information on how to deploy this project on Azure, please follow these articles:

1. Deploy to Azure App Service
1. Deploy to Azure Web App for Containers
1. Deploy to Azure Container Instances
1. Deploy to Azure Kubernetes Service
1. Deploy to Azure Compute with Terraform
