# ここから始める Kubernetes ハンズオン

## 1. Ubuntu 18.04 を利用する場合

### 1.1 Docker インストール

Ubuntu 18.04 に Docker をインストール

```
# sudo apt-get update
# sudo apt-get install apt-transport-https ca-certificates curl software-properties-common
# curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
# sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable test edge"
# sudo apt-get update
# sudo apt-get install docker-ce
```

### 1.2. Azure CLI インストール

Ubuntu 18.04 に Azure CLI (az コマンドラインツールのインストール)

```
curl -sL https://aka.ms/InstallAzureCLIDeb | sudo bash
```

### 1.3. Java のソースコード作成

[Git Clone](https://github.com/yoshioterada/k8s-Azure-Container-Service-AKS--on-Azure)

```
git clone https://github.com/yoshioterada/k8s-Azure-Container-Service-AKS--on-Azure
```

### 1.4. Dockerfile の作成

[0-Dockerfile-for-Maven](https://github.com/yoshioterada/k8s-Azure-Container-Service-AKS--on-Azure/blob/master/FrontService/0-Dockerfile-for-Maven)

```
FROM maven:3.6.1-jdk-8-slim
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml package
RUN rm -rf src pom.xml
```

[0-MultiBuild-Prep.sh](https://github.com/yoshioterada/k8s-Azure-Container-Service-AKS--on-Azure/blob/master/FrontService/0-MultiBuild-Prep.sh)

```
$ docker build -t maven-include-localrepo:1.1 . -f 0-Dockerfile-for-Maven
```

[1-Dockerfile-Multi](https://github.com/yoshioterada/k8s-Azure-Container-Service-AKS--on-Azure/blob/master/FrontService/1-Dockerfile-Multi)

```
#####################################################
# Build the Source Code of Java by using Maven Image
#####################################################
FROM maven-include-localrepo:1.1 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package


#####################################################
# Build container image copying from BUILD artifact
#####################################################
FROM mcr.microsoft.com/java/jdk:8u212-zulu-alpine
#FROM mcr.microsoft.com/java/jdk:11u3-zulu-alpine

# create directory for application
RUN mkdir /app
WORKDIR /app
# add user for application
RUN adduser -S java
USER java

COPY --from=build /usr/src/app/target/front-spring-0.0.1-SNAPSHOT.jar app.jar

# set entrypoint to execute spring boot application
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar" ]
```


### 1.5. Docker イメージの作成

[2-build-create.sh](https://github.com/yoshioterada/k8s-Azure-Container-Service-AKS--on-Azure/blob/master/FrontService/2-build-create.sh)

```
#!/bin/bash
set -e

if [ "$1" = "" ]
then
    echo "./build-create.sh [version-number]"
    exit 1
fi
export VERSION=$1

DOCKER_IMAGE=tyoshio2002/front-spring-service
DOCKER_REPOSITORY=*****.azurecr.io

# Build docker image
docker build -t $DOCKER_IMAGE:$VERSION . -f 1-Dockerfile-Multi
docker tag $DOCKER_IMAGE:$VERSION $DOCKER_REPOSITORY/$DOCKER_IMAGE:$VERSION

# Push the image to Private Docker Registry
# docker push $DOCKER_REPOSITORY/$DOCKER_IMAGE:$VERSION
```

```
$ ./2-build-create.sh 1.0
```


### 1.6. Docker の動作確認

```
$ docker run -p 8080:8080 -it tyoshio2002/front-spring-service:1.8 

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.1.6.RELEASE)

2019-09-26 05:45:15.116  INFO 1 --- [           main] c.y.frontspring.FrontSprintApplication   : Starting FrontSprintApplication v0.0.1-SNAPSHOT on 3db2c68fea44 with PID 1 (/app/app.jar started by java in /app)
2019-09-26 05:45:15.127  INFO 1 --- [           main] c.y.frontspring.FrontSprintApplication   : No active profile set, falling back to default profiles: default
2019-09-26 05:45:18.627  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2019-09-26 05:45:18.695  INFO 1 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2019-09-26 05:45:18.697  INFO 1 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.21]
2019-09-26 05:45:18.900  INFO 1 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2019-09-26 05:45:18.901  INFO 1 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 3613 ms
2019-09-26 05:45:20.327  INFO 1 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2019-09-26 05:45:20.645  WARN 1 --- [           main] i2019-09-26 05:45:21.070  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2019-09-26 05:45:21.079  INFO 1 --- [           main] c.y.frontspring.FrontSprintApplication   : Started FrontSprintApplication in 7.372 seconds (JVM running for 8.518)
2019-09-26 05:45:22.308  INFO 1 --- [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2019-09-26 05:45:22.308  INFO 1 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2019-09-26 05:45:22.326  INFO 1 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 18 ms
```

```
$ curl localhost:8080/sample/hello
front-hello-v1
```



## 3. ACR コンテナ・レジストリに Docker Login
[Azure Container Registry の作成](https://github.com/yoshioterada/DEIS-k8s-ACS/blob/master/CreateAzureContainerRegistry.md)

```
$ docker login -u [Username] -p "[password]"  *****.azurecr.io
```

## 4. ACR コンテナ・レジストリに Docker イメージを Push

[2-build-create.sh](https://github.com/yoshioterada/k8s-Azure-Container-Service-AKS--on-Azure/blob/master/FrontService/2-build-create.sh)

```
$ docker push $DOCKER_REPOSITORY/$DOCKER_IMAGE:$VERSION
```

```
#!/bin/bash
set -e

if [ "$1" = "" ]
then
    echo "./build-create.sh [version-number]"
    exit 1
fi
export VERSION=$1

DOCKER_IMAGE=tyoshio2002/front-spring-service
DOCKER_REPOSITORY=*****.azurecr.io

# Build docker image
docker build -t $DOCKER_IMAGE:$VERSION . -f 1-Dockerfile-Multi
docker tag $DOCKER_IMAGE:$VERSION $DOCKER_REPOSITORY/$DOCKER_IMAGE:$VERSION

# Push the image to Private Docker Registry
docker push $DOCKER_REPOSITORY/$DOCKER_IMAGE:$VERSION
```


## 5. k8s コンテナ・レジストリの接続情報を作成

```
$ kubectl create secret docker-registry docker-reg-credential \
  --docker-server=yoshio.azurecr.io \
  --docker-username=yoshio  \
  --docker-password="**************************" \
  --docker-email=foo-bar@microsoft.com
```

```
$ kubectl get secret
NAME                    TYPE                                  DATA   AGE
default-token-wdtws     kubernetes.io/service-account-token   3      71d
docker-reg-credential   kubernetes.io/dockerconfigjson        1      55d
```

## 6. k8s Deployment YAML の作成

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: spring-front-service
spec:
  replicas: 2
  selector:
    matchLabels:
      app: spring-front-service
  minReadySeconds: 120
  progressDeadlineSeconds: 600
  strategy:
    type: RollingUpdate
    rollingUpdate:
      maxSurge: 50%
      maxUnavailable: 50%
  template:
    metadata:
      labels:
        app: spring-front-service
        version: v1
        stage: staging
    spec:
      securityContext:
        runAsUser: 1000 
      imagePullSecrets:
        - name: docker-reg-credential
      containers:
      - name: spring-front-service
        image: yoshio.azurecr.io/tyoshio2002/front-spring-service:1.7
        env:
          - name: TZ
            value: JST-9
#        livenessProbe:
#          httpGet:
#            path: /healthliveness
#            port: 8080
#          initialDelaySeconds: 120
#          timeoutSeconds: 5
#          periodSeconds: 10
#          failureThreshold: 3
#        readinessProbe:
#          httpGet:
#            path: /healthrediness
#            port: 8080
#          initialDelaySeconds: 30
#          timeoutSeconds: 5
#          periodSeconds: 10
#          failureThreshold: 5
        resources:
          requests:
            cpu: 100m
          limits:
            cpu: 300m
```

## 7. k8s Deployment の適用

```
kubectl apply --record -f 4-create-deployment-svc.yaml
```

```
#!/bin/bash
set -e

if [ "$1" = "" ]
then
    echo "./build-create.sh [version-number]"
    exit 1
fi
export VERSION=$1

DOCKER_IMAGE=tyoshio2002/front-spring-service
DOCKER_REPOSITORY=yoshio.azurecr.io

# Build docker image
#docker build -t $DOCKER_IMAGE:$VERSION . -f 1-Dockerfile
#docker tag $DOCKER_IMAGE:$VERSION $DOCKER_REPOSITORY/$DOCKER_IMAGE:$VERSION

# Push the image to Private Docker Registry
#docker push $DOCKER_REPOSITORY/$DOCKER_IMAGE:$VERSION

# Change the version for docker image inside of YAML file 
sed -i -e "s|image: .*|image: $DOCKER_REPOSITORY/$DOCKER_IMAGE:${VERSION}|g" 4-create-deployment-svc.yaml

# Apply the new Image to the Service
kubectl apply --record -f 4-create-deployment-svc.yaml


# Clean the image
docker rm $(docker ps -aq)
docker images | awk '/<none/{print $3}' | xargs docker rmi 
```

## 8. k8s の基本的な操作

```
kubectl get node
```

```
kubectl get node -o wide
```

```
kubectl version
```

```
kubectl cluster-info
```

```
kubectl get po
```

```
kubectl get po -o wide
```

```
kubectl describe po [POD-NAME]
```

```
kubectl logs [POD-NAME]
```

```
kubectl logs -f [POD-NAME]
```

```
kubectl port-forward [POD-NAME] -p 8080:8080
```

```
kubectl exec -it [POD-NAME] /bin/sh
```

```
kubectl explain [RESOUCE-NAME: deployment,service,ingress etc.]
```

```
$ kubectl get po --v=9
```


## 9. Service YAML の作成

[11-Service.yaml](https://raw.githubusercontent.com/yoshioterada/k8s-Azure-Container-Service-AKS--on-Azure/master/FrontService/11-Service.yaml)

```
apiVersion: v1
kind: Service
metadata:
  labels:
    app: spring-front-service
  name: spring-front-service
spec:
  ports:
  - port: 80
    name: http
    targetPort: 8080
  selector:
    app: spring-front-service
  sessionAffinity: None
  type: ClusterIP
```

## 10. Ingress YAML の作成
[12-Ingress.yaml](https://raw.githubusercontent.com/yoshioterada/k8s-Azure-Container-Service-AKS--on-Azure/master/FrontService/12-Ingress.yaml)

```
apiVersion: extensions/v1beta1
kind: Ingress
metadata:
  name: front-service
  annotations:
    kubernetes.io/ingress.class: addon-http-application-routing
spec:
  rules:
  - host: front-service.a18a7b7650f64e25a8d3.japaneast.aksapp.io
    http:
      paths:
      - backend:
          serviceName: spring-front-service 
          servicePort: 80
        path: /
```

## Appendix A： Azure アカウントの作成
TBD



## Appendix B： Azure Portal の Cloud Shell を利用する場合

### 2.1. Java のソースコード作成

```
Git Clone
git clone https://github.com/yoshioterada/k8s-Azure-Container-Service-AKS--on-Azure
```

### 2.2. Dockerfile の作成

```
#####################################################
# Build container image
# To use this file,
# Please execute the following command
#
# $ mvn clean package
#####################################################
#FROM openjdk:jdk-alpine
FROM mcr.microsoft.com/java/jdk:8u212-zulu-alpine
#FROM mcr.microsoft.com/java/jdk:11u3-zulu-alpine

# create directory for application
RUN mkdir /app
WORKDIR /app
# add user for application
RUN adduser -S java
USER java

COPY target/front-spring-0.0.1-SNAPSHOT.jar app.jar

# set entrypoint to execute spring boot application
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar" ]
```

### 2.3. Docker イメージの作成
```
$ az acr build --registry yoshio --image front-service:v1.2 . -f 1-Dockerfile
...
Run ID: ce4 was successful after 5m36s
```


### 2.4. Docker の動作確認 in Azure Container Instance

```
$ az container create --resource-group ContainerInstance --name front-service2 --image yoshio.azurecr.io/front-service:v1.2 --dns-name-label front-service2 --ports8080 --registry-username yoshio --registry-password "ieaEplJm7Fs71Vlpo=1Lw/Y6mBU6yGnb"
```

もしくは

```
az container create \
    --resource-group $RES_GROUP \
    --name acr-tasks \
    --image $ACR_NAME.azurecr.io/helloacrtasks:v1 \
    --registry-login-server $ACR_NAME.azurecr.io \
    --registry-username $(az keyvault secret show --vault-name $AKV_NAME --name $ACR_NAME-pull-usr --query value -o tsv) \
    --registry-password $(az keyvault secret show --vault-name $AKV_NAME --name $ACR_NAME-pull-pwd --query value -o tsv) \
    --dns-name-label acr-tasks-$ACR_NAME \
    --query "{FQDN:ipAddress.fqdn}" \
    --output table
```


```
$ az container show --resource-group ContainerInstance --name front-service2 --query "{FQDN:ipAddress.fqdn,ProvisioningState:provisioningState}" --out table
FQDN                                        ProvisioningState
------------------------------------------  -------------------
front-service2.japaneast.azurecontainer.io  Succeeded
```

```
$ az container attach --resource-group ContainerInstance  --name front-service2
Unable to load extension 'aks-preview'. Use --debug for more information.
Container 'front-service2' is in state 'Running'...
(count: 1) (last timestamp: 2019-09-26 05:11:27+00:00) pulling image "yoshio.azurecr.io/front-service:v1.2"
(count: 1) (last timestamp: 2019-09-26 05:11:44+00:00) Successfully pulled image "yoshio.azurecr.io/front-service:v1.2"
(count: 1) (last timestamp: 2019-09-26 05:11:44+00:00) Created container
(count: 1) (last timestamp: 2019-09-26 05:11:44+00:00) Started container
(count: 1) (last timestamp: 2019-09-26 05:29:41+00:00) pulling image "yoshio.azurecr.io/front-service:v1.2"
(count: 1) (last timestamp: 2019-09-26 05:30:02+00:00) Successfully pulled image "yoshio.azurecr.io/front-service:v1.2"
(count: 1) (last timestamp: 2019-09-26 05:30:10+00:00) Created container
(count: 1) (last timestamp: 2019-09-26 05:30:10+00:00) Started container

Start streaming logs:

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.1.6.RELEASE)

2019-09-26 05:30:12.595  INFO 1 --- [           main] c.y.frontspring.FrontSprintApplication   : Starting FrontSprintApplication v0.0.1-SNAPSHOT on wk-caas-9f223d224de64cabb075001852c4538f-ea544c2ae3d42d702e9196 with PID 1 (/app/app.jar started by java in /app)
2019-09-26 05:30:12.605  INFO 1 --- [           main] c.y.frontspring.FrontSprintApplication   : No active profile set, falling back to default profiles: default
2019-09-26 05:30:16.239  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2019-09-26 05:30:16.328  INFO 1 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2019-09-26 05:30:16.329  INFO 1 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.21]
2019-09-26 05:30:16.542  INFO 1 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2019-09-26 05:30:16.543  INFO 1 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 3783 ms
2019-09-26 05:30:17.946  INFO 1 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2019-09-26 05:30:18.210  WARN 1 --- [           main] ion$DefaultTemplateResolverConfiguration : Cannot find template location: classpath:/templates/ (please add some templates or check your Thymeleaf configuration)
2019-09-26 05:30:18.614  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2019-09-26 05:30:18.617  INFO 1 --- [           main] c.y.frontspring.FrontSprintApplication   : Started FrontSprintApplication in 7.202 seconds (JVM running for 8.141)
2019-09-26 05:30:34.327  INFO 1 --- [nio-8080-exec-5] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2019-09-26 05:30:34.328  INFO 1 --- [nio-8080-exec-5] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2019-09-26 05:30:34.340  INFO 1 --- [nio-8080-exec-5] o.s.web.servlet.DispatcherServlet        : Completed initialization in 12 ms
Header 'host' = front-service2.japaneast.azurecontainer.io:8080
Header 'connection' = keep-alive
Header 'upgrade-insecure-requests' = 1
```

```
$ curl http://front-service2.japaneast.azurecontainer.io:8080/sample/hello
front-hello-v1
```


