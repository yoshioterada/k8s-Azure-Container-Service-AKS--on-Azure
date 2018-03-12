[Previous Page](Kubernetes-Workshop5.md) / [Next Page](Kubernetes-Workshop7.md)
---
***Note: This contents is Beta version!!***

---
* [Azure 無料アカウントのご取得はコチラから](https://aka.ms/jjug_mar2)  
***Azure の無料アカウントで料金は発生しますか？  
いいえ。無料で開始でき、最初の 30 日間に使用する ¥22,500 クレジットを取得します。サービスの利用を開始した後でも、アップグレードするまでは無料で利用できます。***  

---
Before this section, I would like to share following.

For [Istio](https://istio.io/) , I presented at the [Payara & Hazelcast Japanese Office launch event](https://glassfish.doorkeeper.jp/events/68844) and [Developers Summit 2018 Japan](https://event.shoeisha.jp/devsumi/20180215/session/1629/) by using this [ (SlildeShare) presentation material by Japanese](https://www.slideshare.net/tyoshio2002/istio-on-k8s-on-azure-aks).

For the above event, some online media reported my presentation as follows.

* [What is "service mesh"? What is "Istio", what do you use? what is beneft?](http://www.atmarkit.co.jp/ait/articles/1802/09/news015.html)
* [Payara & Hazelcast launch Japanese Offoce, Future of Java EE](https://thinkit.co.jp/article/13356)


# 6. Istio (Service Mesh)

In order to create Microservices, we need to consider so many things for example :

* Service Discovery
* Retry Timeout
* Load Balancer
* Bulk Head
* Circuit Breaker
* Network Management
* Blue/Green Deployment
* Feature Flag
* Canary Deployment
* Detect Failure
* Log output
* Health Check
* ......

Before use the service mesh technology, individual programer must implements the above functionality by themselves. For example, for Java developers, in order to create the cloud native application, we had been implemented the above functionality by using Netflix OSS like Eureka, Ribbon, Hystrix, Zuul and Lagom, failsafe and so on. In fact, I think that it is very good technology and libraries.  

However please wait and consider? It is is not the business logic but operation and common use case to implement the Cloud Native Application. I'm a Java Developpers, so I can learn and use the above libraries from Java source code. However if you must implement the micro services by using other programing language, which libraries is appropriate for implementing the Circuit Breaker? In order to implemnt the Circuit Breaker on other programing language, you must investigate and learn and evaluate the new libraries for individual languages. I don't want to do that.  
If you are thinking that the same things, The "Service Mesh" technologies like "Istio" may help you.

## 6.1 About Istio concept for Java Developers

I think that the concept of "Service Mesh" and "Side Car" pattern is not new concept. If you are familir with Java, you already had been using the above concept in your Java programing. In fact, There is a  [Proxy](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Proxy.html) class in Java, and may be you had been using Dependency Injection(DI/CDI) framework. And also you will use the Aspect Oriented Programming:AOP） model. In fact, it is the same concept of "Service Mesh" and "Side Car" pattern.

In fact,  it is very useful for us to separete the cross cutting concern from business logic programing code like loggint and authentication and so on. In order to do that we had been using the [Interceptor](https://docs.oracle.com/javaee/7/api/javax/enterprise/inject/spi/Interceptor.html) and [Filter](https://docs.oracle.com/javaee/7/api/javax/servlet/Filter.html) class.  And if you use the CDI, automaticaly the system added the new functionality to original class like Transaction context, Security context and so on. This is very useful because Java developer can focus on the business logic implementation not cross cutting concern. Without any modification of business logic code, you can add the functionality out side of the business logic programing code.

Same as the Java programing, the "Service Mesh" and "Side Car Pattern" can add additional functionality 
out of the Service. Of course the layer is difference from the Programing model and Service model. However it is very useful for us to additional functionality into the existing service withour any modification of the Services.

In fact, the Istio provide the following functionality without any modification of the existing services by using [Envoy Proxy](https://www.envoyproxy.io) which is create by Lyft.

* Network Routing
* Security (Private certificate authority)
* Monitoring & Managing

For example, in general, if you are using Kubernetes and would like to create the "Deployment" and "Service", you will execute like following command.

```
#/usr/local/bin/kubectl apply --record -f create-deployment-svc.yaml
```

After installed the Istio, you can execute following command which installed the new "Envoy Proxy" container in your pod, and automatiaclly you are able to invoke your services via the "Envoy Proxy".

```
/usr/local/bin/kubectl apply --record -f <(/usr/local/bin/istioctl kube-inject -f ./create-deployment-svc.yaml --includeIPRanges=10.244.0.0/24)
```

As you can see, without any modification of the existing congiration, you can inject additional functionality into your service by using Envoy Proxy.


## 6.2 Install Istio

Please refer to the followin site?  
* [https://github.com/istio/istio/releases/](https://github.com/istio/istio/releases/)  

The version up of Istio will be extreamly fast (Now 0.6.0 : 2018/03/01)!! 


### 6.2.1 Download & Install
At first, please download the latest Istio project ? 
  
```
$ curl -L https://git.io/getLatestIstio | sh -
```

Then you can install Istio by using following command.

```
$ cd istio-0.5.0/install/kubernetes

$ kubectl apply -f istio.yaml 
namespace "istio-system" created
clusterrole "istio-pilot-istio-system" created
clusterrole "istio-initializer-istio-system" created
clusterrole "istio-mixer-istio-system" created
clusterrole "istio-ca-istio-system" created
clusterrole "istio-sidecar-istio-system" created
clusterrolebinding "istio-pilot-admin-role-binding-istio-system" created
....
```

### 6.2.2 Install Add-On package

#### Install Grafana

In order to install additional functionality, you can install the following command. Following is the instllation for Grafana.

```
$ kubectl apply -f addons/grafana.yaml 
service "grafana" created
deployment "grafana" created
```

After installed Grafana, you can confirm that it installed into istio-system  namaspace.

```
$ kubectl get pod,svc,deployment -n istio-system 
NAME                                READY     STATUS    RESTARTS   AGE
po/grafana-3617079618-khhm6         1/1       Running   0          1m
po/istio-ca-1363003450-dwmhz        1/1       Running   0          12m
po/istio-ingress-1005666339-n2171   1/1       Running   0          12m
po/istio-mixer-465004155-pdlmp      3/3       Running   0          13m
po/istio-pilot-1861292947-shxkm     2/2       Running   0          12m

NAME                TYPE           CLUSTER-IP     EXTERNAL-IP    PORT(S)                                                            AGE
svc/grafana         ClusterIP      10.0.177.170   <none>         3000/TCP                                                           1m
svc/istio-ingress   LoadBalancer   10.0.196.241   **.***.**.**   80:30860/TCP,443:32633/TCP                                         12m
svc/istio-mixer     ClusterIP      10.0.79.187    <none>         9091/TCP,15004/TCP,9093/TCP,9094/TCP,9102/TCP,9125/UDP,42422/TCP   13m
svc/istio-pilot     ClusterIP      10.0.87.192    <none>         15003/TCP,443/TCP                                                  13m

NAME                   DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
deploy/grafana         1         1         1            1           1m
deploy/istio-ca        1         1         1            1           12m
deploy/istio-ingress   1         1         1            1           12m
deploy/istio-mixer     1         1         1            1           13m
deploy/istio-pilot     1         1         1            1           12m
```

In order to show the dashboard of Grafana, you can execute the following port-foraward command.

```
$ kubectl -n istio-system port-forward $(kubectl -n istio-system get \
  pod -l app=grafana -o jsonpath='{.items[0].metadata.name}') 3000:3000 &
```

Then please access to the 3000 port of your local machine like follows?

[http://localhost:3000](http://localhost:3000)


#### Install Prometheus

In order to install additional functionality, you can install the following command. Following is the instllation for Prometheus.

```
$ kubectl apply -f addons/prometheus.yaml
configmap "prometheus" created
service "prometheus" created
deployment "prometheus" created
```

After installed Prometheus, you can confirm that it installed into istio-system  namaspace.

```
$ kubectl get po,svc,deploy,cm -n istio-system
NAME                                READY     STATUS    RESTARTS   AGE
po/grafana-3617079618-khhm6         1/1       Running   0          3m
po/istio-ca-1363003450-dwmhz        1/1       Running   0          15m
po/istio-ingress-1005666339-n2171   1/1       Running   0          15m
po/istio-mixer-465004155-pdlmp      3/3       Running   0          15m
po/istio-pilot-1861292947-shxkm     2/2       Running   0          15m
po/prometheus-168775884-pfzbv       1/1       Running   0          1m

NAME                TYPE           CLUSTER-IP     EXTERNAL-IP    PORT(S)                                                            AGE
svc/grafana         ClusterIP      10.0.177.170   <none>         3000/TCP                                                           3m
svc/istio-ingress   LoadBalancer   10.0.196.241   52.224.66.20   80:30860/TCP,443:32633/TCP                                         15m
svc/istio-mixer     ClusterIP      10.0.79.187    <none>         9091/TCP,15004/TCP,9093/TCP,9094/TCP,9102/TCP,9125/UDP,42422/TCP   15m
svc/istio-pilot     ClusterIP      10.0.87.192    <none>         15003/TCP,443/TCP                                                  15m
svc/prometheus      ClusterIP      10.0.191.195   <none>         9090/TCP                                                           1m

NAME                   DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
deploy/grafana         1         1         1            1           3m
deploy/istio-ca        1         1         1            1           15m
deploy/istio-ingress   1         1         1            1           15m
deploy/istio-mixer     1         1         1            1           15m
deploy/istio-pilot     1         1         1            1           15m
deploy/prometheus      1         1         1            1           1m

NAME                                       DATA      AGE
cm/istio                                   1         15m
cm/istio-ingress-controller-leader-istio   0         15m
cm/istio-mixer                             1         15m
cm/prometheus                              1         1m
```

In order to show the dashboard of Prometheus, you can execute the following port-foraward command.

```
$ kubectl -n istio-system port-forward $(kubectl -n istio-system get \
  pod -l app=prometheus -o jsonpath='{.items[0].metadata.name}') 9090:9090 &
```

[http://localhost:9090](http://localhost:9090)

#### Install Jaeger (Distributed Tracing instead of Zipkin)

In order to install additional functionality, you can install the following command. Following is the instllation for Jaeger. Jaeger is one of the distributed Tracing service. You can also install Zipkin instead of 
Jaeger. However in this time, I installed the Jaeger.

```
kubectl apply -n istio-system -f https://raw.githubusercontent.com/jaegertracing/jaeger-kubernetes/master/all-in-one/jaeger-all-in-one-template.yml
```

If you would like to create multiple microservices on youe environment, some service may need to invoke other services like front-end service and back-end service. If you use the Zipkin or Jaeger, it is possible for us to understand the latency for individual services.
In order to use it, you need to receive and send (add) the specific HTTP Headers on your programing code like follows. And if you added the headers, the distributed tracing system caluculate the latency.

For example, following is the sample source code for JAX-RS in Server side, at first you need to receive the following HTTP headers. And if In side of your application, you need to invoke out side of services, You need to propaget the HTTP headers to the out services like follows

```
    @GET
    @Path(value = "/listUsers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(
            @HeaderParam("x-request-id") String xreq,
            @HeaderParam("x-b3-traceid") String xtraceid,
            @HeaderParam("x-b3-spanid") String xspanid,
            @HeaderParam("x-b3-parentspanid") String xparentspanid,
            @HeaderParam("x-b3-sampled") String xsampled,
            @HeaderParam("x-b3-flags") String xflags,
            @HeaderParam("x-ot-span-context") String xotspan) {
         // your business logic code.

        // Inside of your application, you need to invoke out side of services
        // You need to propaget the headers to the out services like follows

        Client client = ClientBuilder.newBuilder()
                .build();
        Response response = client.target(ACCOUT_SERVICE_SERVER_URL)
                .request()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Accept", MediaType.APPLICATION_JSON)
                .header("x-request-id", xreq)
                .header("x-b3-traceid", xtraceid)
                .header("x-b3-spanid", xspanid)
                .header("x-b3-parentspanid", xparentspanid)
                .header("x-b3-sampled", xsampled)
                .header("x-b3-flags", xflags)
                .header("x-ot-span-context", xotspan)
                .get();
```

## 6.3 How to use the Istio 

***Note:***  
***I used Istio 0.5.0 in the following sample with AKS Kubernetes 1.9.2.  
Because if I used the latest version of Istio 0.6.0, I faced some trouble.  
So I used Istio version 0.5.0.***

If you would like to add the "Envoy Proxy" into your services, please execute following command? As you can see, in side of kubectl command, ***istioctl kube-inject*** command invoked. It will add additional configuration to your create-deployment-svc.yaml  file.   
Then you can execute the ***kubectl apply*** command as usual.

```
$ kubectl apply --record -f <(/usr/local/bin/istioctl \
 kube-inject -f ./create-deployment-svc.yaml --includeIPRanges=10.244.0.0/24)
```

## 6.4 Traffic Management

At first, in order to provide the service I deployed a "trans-service-v1" ***Deployment***. This  is text translation service from English to Japanese. And I added two label for this Deploymetn as ***"app: trans-service"*** and ***"version: v1"***.

And also in order to expose the above deployment, I created "trans-service"  ***Services***. In this definition, I only wrote the ***app: trans-service***. on selector (not included the "version: v1"). It means that this Service can expose multiple version of trans-services. It is the benefit of the label and selector.


```
apiVersion: extensions/v1beta1
kind: Deployment
metadata:
  name: trans-service-v1
  namespace: order-system-production
spec:
  replicas: 1
  template:
    metadata:
      labels:
        app: trans-service
        version: v1
    spec:
      imagePullSecrets:
        - name: docker-reg-credential
      containers:
      - name: trans-service
        image: yoshio.azurecr.io/tyoshio2002/translation-service:1.16
---
apiVersion: v1
kind: Service
metadata:
  labels:
    app: trans-service
  name: trans-service
  namespace: order-system-production
spec:
  ports:
  - port: 80
    name: http
    targetPort: 8080
  - port: 443
    name: https
    targetPort: 8080
  selector:
    app: trans-service
  sessionAffinity: None
  type: ClusterIP
```

In order to deploy the Deployment and Service, please execute following command?

```
$ kubectl apply -f <(/usr/local/bin/istioctl kube-inject \
-f ./create-deployment-svc.yaml --includeIPRanges=10.0.0.0/8)
```

In order to be able to access to the above service, I created following Istio Ingress.

```
apiVersion: extensions/v1beta1
kind: Ingress
metadata:
  annotations:
    kubernetes.io/ingress.class: istio
  name: front-gateway
spec:
  rules:
  - http:
      paths:
      - backend:
          serviceName: trans-service 
          servicePort: 80
        path: /app/translate/.*
```

And in order to deploy the Ingress, please execute following command?

```
$ kubectl apply -f <(/usr/local/bin/istioctl kube-inject -f ingress.yaml)
```

After deployed the above, please deploy Version2 of Trans service as follows?  I wrote two label as ***"app: trans-service"*** and ***"version: v2"*** , and the container image specified another version as 1.17.

```
apiVersion: extensions/v1beta1
kind: Deployment
metadata:
  name: trans-service-v2
  namespace: order-system-production
spec:
  replicas: 1
  template:
    metadata:
      labels:
        app: trans-service
        version: v2
    spec:
      imagePullSecrets:
        - name: docker-reg-credential
      containers:
      - name: trans-service
        image: yoshio.azurecr.io/tyoshio2002/translation-service:1.17
        imagePullPolicy: IfNotPresent
```

In this situation, I access to the Ingress IP address with following URL, the server response "version-1" and "version-2" as round robin.

```
$ curl  -X POST -H "Content-Type:application/json" 52.***.***.15/app/translate/message \ 
 -d "{\"message\": \"this is a pen\"}"
{"value":"これはペンです。","version":"version-2"}$ 
$ curl  -X POST -H "Content-Type:application/json" 52.***.***.15/app/translate/message \ 
 -d "{\"message\": \"this is a pen\"}"
{"value":"これはペンです。","version":"version-1"}
$ curl  -X POST -H "Content-Type:application/json" 52.***.***.15/app/translate/message \ 
 -d "{\"message\": \"this is a pen\"}"
{"value":"これはペンです。","version":"version-2"}
$ curl  -X POST -H "Content-Type:application/json" 52.***.***.15/app/translate/message \ 
-d "{\"message\": \"this is a pen\"}"
{"value":"これはペンです。","version":"version-1"}$
```


### 6.4.1 Weight Routing to 100% specific service

If you would like to transfer the request to the "version-1" only. Then you need to create following RouteRule.  It means that 100% of request send to the "trans-service" with label "version: v1".

```
apiVersion: config.istio.io/v1alpha2
kind: RouteRule
metadata:
  name: trans-service-default
  namespace: order-system-production
spec:
  destination:
     name: trans-service
  precedence: 1
  route:
  - labels:
      version: v1
    weight: 100
```

In order to create the RouteRule, please execute the following command?

```
$ istioctl create -f routerule-v1.yaml 
Created config route-rule/order-system-production/trans-service-default at revision 864106
```

Then all of the request will be send to "version1" and never send the request to v2.

```
$ curl  -X POST -H "Content-Type:application/json" 52.***.***.15/app/translate/message -d "{\"message\": \"this is a pen\"}"
{"value":"これはペンです。","version":"version-1"}$ 
$ curl  -X POST -H "Content-Type:application/json" 52.***.***.15/app/translate/message -d "{\"message\": \"this is a pen\"}"
{"value":"これはペンです。","version":"version-1"}$ 
$ curl  -X POST -H "Content-Type:application/json" 52.***.***.15/app/translate/message -d "{\"message\": \"this is a pen\"}"
{"value":"これはペンです。","version":"version-1"}$ 
$ curl  -X POST -H "Content-Type:application/json" 52.***.***.15/app/translate/message -d "{\"message\": \"this is a pen\"}"
{"value":"これはペンです。","version":"version-1"}$ 
$
```

### 6.4.2 Restrict access for special Header

For example, if you are developer and would like to confirm the behavior of service before release offcially. Or if you would like to restrict the access for specific users like beta tester, you can transer the request for specific users only.
In fact, If you would like to restrict the access which have specific HTTP header, then you can write like follows. In this sample, if the use specify the special header as ***x-dev-user: super-secret***,  only then it will send the request to v2 of trans service. If you didn't specify the above header, all of the request will be send to v1.

```
apiVersion: config.istio.io/v1alpha2
kind: RouteRule
metadata:
  name: trans-service-featureflag
  namespace: order-system-production
spec:
  destination:
     name: trans-service
  precedence: 2
  match:
    request:
      headers:
        x-dev-user:
          exact: "super-secret"
  route:
  - labels:
      version: v2
      app: trans-service
```

In order to apply the above Route Rule, please execute the following command?

```
$ istioctl create -f routerule-v2.yaml 
Created config route-rule/order-system-production/trans-service-featureflag at revision 864727
$
```

After appied the rule, please access to the Ingress IP address like follows. Then if you don't specify the special header, all of requests will be send to the version1. And only if you specify the special header, the reuqest will send to version2.

```
$ curl  -X POST -H "Content-Type:application/json" 52.***.***.15/app/translate/message \ 
-d "{\"message\": \"this is a pen\"}"
{"value":"これはペンです。","version":"version-1"}$ 
$ curl  -X POST -H "Content-Type:application/json" 52.***.***.15/app/translate/message \
-d "{\"message\": \"this is a pen\"}"
{"value":"これはペンです。","version":"version-1"}$ 
$ curl  -X POST -H "Content-Type:application/json" 52.***.***.15/app/translate/message \
-d "{\"message\": \"this is a pen\"}"
{"value":"これはペンです。","version":"version-1"}$ 
$ curl  -X POST -H "x-dev-user: super-secret" -H "Content-Type:application/json"  \
    52.***.***.15/app/translate/message -d "{\"message\": \"this is a pen\"}"
{"value":"これはペンです。","version":"version-2"}$ 
$ curl  -X POST -H "x-dev-user: super-secret" -H "Content-Type:application/json"  \
    52.***.***.15/app/translate/message -d "{\"message\": \"this is a pen\"}"
{"value":"これはペンです。","version":"version-2"}$ 
$ curl  -X POST -H "x-dev-user: super-secret" -H "Content-Type:application/json"  \
     52.***.***.15/app/translate/message -d "{\"message\": \"this is a pen\"}"
{"value":"これはペンです。","version":"version-2"}$ 
```

Note:  
In the above two senario, I created two Route Rule with difference name like follows. It means that you can create multiple Route Rule for  your environment. And you can specify the priority of the Route Rule in the ***precedence:*** field in RouteRule. The high value is high priority. In this example,  the precedence value inside of trans-service-featureflag is 2. So trans-service-featureflag is high priority.

```
$ istioctl get routerule
NAME				KIND					NAMESPACE
trans-service-default		RouteRule.config.istio.io.v1alpha2	order-system-production
trans-service-featureflag	RouteRule.config.istio.io.v1alpha2	order-system-production
```


### 6.4.3 Canary Release
//TODO: Need to write explanation

```
apiVersion: config.istio.io/v1alpha2
kind: RouteRule
metadata:
  name: trans-service-canary
  namespace: order-system-production
spec:
  destination:
     name: trans-service
  precedence: 3
  route:
  - labels:
      app: trans-service
      version: v1
    weight: 70
  - labels:
      app: trans-service
      version: v2
    weight: 30
```
//TODO: Need to write explanation

```
$ istioctl create -f routerule-v3.yaml
```


### 6.4.4 Request Timeout
//TODO: Need to write explanation

```
apiVersion: config.istio.io/v1alpha2
kind: RouteRule
metadata:
  name: trans-service-bulkhead
  namespace: order-system-production
spec:
  destination:
     name: trans-service
  precedence: 6
  route:
  - labels:
      app: trans-service
      version: v3
    weight: 100
  httpReqTimeout:
    simpleTimeout:
      timeout: 5s
```
//TODO: Need to write explanation

```
$ istioctl create -f routerule-v4.yaml
```


### 6.4.5 CircuitBreaker 
//TODO: Need to write explanation

```
apiVersion: config.istio.io/v1beta1
kind: DestinationPolicy
metadata:
  name: translator-circuit-breaker
  namespace: order-system-production
spec:
  destination:
    name: trans-service
    labels:
      app: trans-service
      version: v3
  circuitBreaker:
    simpleCb:
      maxConnections: 100               # limit of 100 connections(Maximum number of connections to a backend)
      httpMaxRequests: 1000             # 1000 concurrent requests
      httpMaxRequestsPerConnection: 10  # more than 10 req/connection 
      httpMaxPendingRequests: 10        # Maximum number of pending requests to a backend
      httpConsecutiveErrors: 7          # Number of 5XX errors before circuit is opened
      sleepWindow: 5m                   # Minimum time the circuit will be open. 
      httpDetectionInterval: 2m         # hosts to be scanned every 3 mins
```
//TODO: Need to write explanation

```
$ istioctl create -f dest-rule.yaml
```


---
[Previous Page](Kubernetes-Workshop5.md) / [Next Page](Kubernetes-Workshop7.md)
