[Previous Page](Kubernetes-Workshop5.md) / [Next Page](Kubernetes-Workshop7.md)
---
***Note: This contents is Beta version!!***


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

***If you are using Kubernetes 1.9 or latest, you can use the [Automatic sidecar injection](https://istio.io/docs/setup/kubernetes/sidecar-injection/#automatic-sidecar-injection) functionality. It means that it automaticaly inject the sidecare and you don't need to execute the above command by hand.***   


As you can see, without any modification of the existing congiration, you can inject additional functionality into your service by using Envoy Proxy.


## 6.2 Install Istio 0.8.0 (LTS Version)

Please refer to the followin site?  
* [https://github.com/istio/istio/releases/](https://github.com/istio/istio/releases/)  

The version up of Istio will be extreamly fast (Now 0.8.0 (Long Time Support version) : 2018/06/01)!! 


### 6.2.1 Download & Install
At first, please download the latest Istio project ? 
  
```
$ curl -L https://git.io/getLatestIstio | sh -
```

I installed Istio by using Helm as follows.  
***However installation of Istio prior to version 0.8.0 with Helm is unstable and not recommended.***  
Please refer to the following? [Installation with Helm](https://istio.io/docs/setup/kubernetes/helm-install/)


I installed without the automatic sidecar injection.

```
$ cd istio-0.8.0

$ helm template install/kubernetes/helm/istio --name istio --namespace istio-system --set sidecarInjectorWebhook.enabled=false > $HOME/istio.yaml
```

Then please create the namespace for Istio as istio-system. And please install the istio?

```
$ kubectl create namespace istio-system
namespace "istio-system" created

$ kubectl apply -f $HOME/istio.yaml 
configmap "istio-statsd-prom-bridge" created
configmap "istio-mixer-custom-resources" created
configmap "prometheus" created
configmap "istio" created
configmap "istio-sidecar-injector" created
serviceaccount "istio-egressgateway-service-account" created
serviceaccount "istio-ingress-service-account" created
serviceaccount "istio-ingressgateway-service-account" created
serviceaccount "istio-mixer-post-install-account" created
clusterrole.rbac.authorization.k8s.io "istio-mixer-post-install-istio-system" created
clusterrolebinding.rbac.authorization.k8s.io "istio-mixer-post-install-role-binding-istio-system" created
job.batch "istio-mixer-post-install" created
serviceaccount "istio-mixer-service-account" created
serviceaccount "istio-pilot-service-account" created
serviceaccount "prometheus" created
serviceaccount "istio-citadel-service-account" created
serviceaccount "istio-cleanup-old-ca-service-account" created
customresourcedefinition.apiextensions.k8s.io "rules.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "attributemanifests.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "circonuses.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "deniers.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "fluentds.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "kubernetesenvs.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "listcheckers.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "memquotas.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "noops.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "opas.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "prometheuses.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "rbacs.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "servicecontrols.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "solarwindses.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "stackdrivers.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "statsds.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "stdios.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "apikeys.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "authorizations.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "checknothings.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "kuberneteses.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "listentries.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "logentries.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "metrics.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "quotas.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "reportnothings.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "servicecontrolreports.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "tracespans.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "serviceroles.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "servicerolebindings.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "destinationpolicies.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "egressrules.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "routerules.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "virtualservices.networking.istio.io" created
customresourcedefinition.apiextensions.k8s.io "destinationrules.networking.istio.io" created
customresourcedefinition.apiextensions.k8s.io "serviceentries.networking.istio.io" created
customresourcedefinition.apiextensions.k8s.io "gateways.networking.istio.io" created
customresourcedefinition.apiextensions.k8s.io "policies.authentication.istio.io" created
customresourcedefinition.apiextensions.k8s.io "httpapispecbindings.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "httpapispecs.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "quotaspecbindings.config.istio.io" created
customresourcedefinition.apiextensions.k8s.io "quotaspecs.config.istio.io" created
clusterrole.rbac.authorization.k8s.io "istio-ingress-istio-system" created
clusterrole.rbac.authorization.k8s.io "istio-mixer-istio-system" created
clusterrole.rbac.authorization.k8s.io "istio-pilot-istio-system" created
clusterrole.rbac.authorization.k8s.io "prometheus-istio-system" created
clusterrolebinding.rbac.authorization.k8s.io "prometheus-istio-system" created
clusterrole.rbac.authorization.k8s.io "istio-citadel-istio-system" created
role.rbac.authorization.k8s.io "istio-cleanup-old-ca-istio-system" created
clusterrolebinding.rbac.authorization.k8s.io "istio-ingress-istio-system" created
clusterrolebinding.rbac.authorization.k8s.io "istio-mixer-admin-role-binding-istio-system" created
clusterrolebinding.rbac.authorization.k8s.io "istio-pilot-istio-system" created
clusterrolebinding.rbac.authorization.k8s.io "istio-citadel-istio-system" created
rolebinding.rbac.authorization.k8s.io "istio-cleanup-old-ca-istio-system" created
service "istio-egressgateway" created
service "istio-ingress" created
service "istio-ingressgateway" created
service "istio-policy" created
service "istio-telemetry" created
service "istio-statsd-prom-bridge" created
deployment.extensions "istio-statsd-prom-bridge" created
service "istio-pilot" created
service "prometheus" created
service "istio-citadel" created
deployment.extensions "istio-egressgateway" created
deployment.extensions "istio-ingress" created
deployment.extensions "istio-ingressgateway" created
deployment.extensions "istio-policy" created
deployment.extensions "istio-telemetry" created
deployment.extensions "istio-pilot" created
deployment.extensions "prometheus" created
deployment.extensions "istio-citadel" created
job.batch "istio-cleanup-old-ca" created
horizontalpodautoscaler.autoscaling "istio-egressgateway" created
horizontalpodautoscaler.autoscaling "istio-ingress" created
horizontalpodautoscaler.autoscaling "istio-ingressgateway" created
```


### 6.2.2 Install Add-On package

#### Install Grafana

In order to install additional functionality, you can install the following command. Following is the instllation for Grafana.

```
$ cd install/kubernetes/addons/

$ kubectl apply -f grafana.yaml -n istio-system
service "grafana" created
deployment.extensions "grafana" created
serviceaccount "grafana" created
```

After installed Grafana, you can confirm that it installed into istio-system  namaspace.

```
$ kubectl get pod,svc,deployment -n istio-system 
NAME                                            READY     STATUS      RESTARTS   AGE
pod/grafana-6bb556d859-8qnvb                    1/1       Running     0          18m
pod/istio-citadel-ff5696f6f-xrw7j               1/1       Running     0          1m
pod/istio-cleanup-old-ca-x92tk                  0/1       Completed   0          1m
pod/istio-egressgateway-58d98d898c-tzpgt        1/1       Running     0          1m
pod/istio-ingress-6fb78f687f-rtpjk              1/1       Running     0          1m
pod/istio-ingressgateway-6bc7c7c4bc-7s546       1/1       Running     0          1m
pod/istio-mixer-post-install-qb75p              0/1       Completed   0          2m
pod/istio-pilot-6c5c6b586c-9j476                2/2       Running     0          1m
pod/istio-policy-5c7fbb4b9f-44c6p               2/2       Running     0          1m
pod/istio-statsd-prom-bridge-6dbb7dcc7f-gk77c   1/1       Running     0          2m
pod/istio-telemetry-54b5bf4847-z2qfw            2/2       Running     0          1m
pod/prometheus-586d95b8d9-hr8b9                 1/1       Running     0          1m

NAME                               TYPE           CLUSTER-IP     EXTERNAL-IP   PORT(S)                                                               AGE
service/grafana                    ClusterIP      10.0.17.143    <none>        3000/TCP                                                              18m
service/istio-citadel              ClusterIP      10.0.13.38     <none>        8060/TCP,9093/TCP                                                     1m
service/istio-egressgateway        ClusterIP      10.0.165.12    <none>        80/TCP,443/TCP                                                        2m
service/istio-ingress              LoadBalancer   10.0.34.78     <pending>     80:32000/TCP,443:31190/TCP                                            2m
service/istio-ingressgateway       LoadBalancer   10.0.165.34    <pending>     80:31380/TCP,443:31390/TCP,31400:31400/TCP                            2m
service/istio-pilot                ClusterIP      10.0.86.248    <none>        15003/TCP,15005/TCP,15007/TCP,15010/TCP,15011/TCP,8080/TCP,9093/TCP   1m
service/istio-policy               ClusterIP      10.0.155.98    <none>        9091/TCP,15004/TCP,9093/TCP                                           2m
service/istio-statsd-prom-bridge   ClusterIP      10.0.60.164    <none>        9102/TCP,9125/UDP                                                     2m
service/istio-telemetry            ClusterIP      10.0.195.4     <none>        9091/TCP,15004/TCP,9093/TCP,42422/TCP                                 2m
service/prometheus                 ClusterIP      10.0.189.138   <none>        9090/TCP                                                              1m

NAME                                             DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
deployment.extensions/grafana                    1         1         1            1           18m
deployment.extensions/istio-citadel              1         1         1            1           1m
deployment.extensions/istio-egressgateway        1         1         1            1           1m
deployment.extensions/istio-ingress              1         1         1            1           1m
deployment.extensions/istio-ingressgateway       1         1         1            1           1m
deployment.extensions/istio-pilot                1         1         1            1           1m
deployment.extensions/istio-policy               1         1         1            1           1m
deployment.extensions/istio-statsd-prom-bridge   1         1         1            1           2m
deployment.extensions/istio-telemetry            1         1         1            1           1m
deployment.extensions/prometheus                 1         1         1            1           1m
```

In order to show the dashboard of Grafana, you can execute the following port-foraward command.

```
$ kubectl -n istio-system port-forward $(kubectl -n istio-system get \
  pod -l app=grafana -o jsonpath='{.items[0].metadata.name}') 3000:3000 &
```

Then please access to the 3000 port of your local machine like follows?

[http://localhost:3000](http://localhost:3000)


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

If you would like to add the "Envoy Proxy" into your services, please execute following command? As you can see, in side of kubectl command, ***istioctl kube-inject*** command invoked. It will add additional configuration to your create-deployment-svc.yaml  file.   
Then you can execute the ***kubectl apply*** command as usual.

```
$ kubectl apply --record -f <(/usr/local/bin/istioctl \
 kube-inject -f ./create-deployment-svc.yaml)
```

## 6.4 Traffic Management for Istio 0.8.0

***Note for Istio 0.8.0 :***  
If you used the previous version of Istio (ex: 0.7.x), the configuration for "Traffic Management" is extreamly  difference.
In previous version, There was the resources like `RouteRule`, `DestinationPolicy`, `EgressRule` and `Ingress`. However they changed to following resources.

* `RouteRule` -> `VirtualService`
* `DestinationPolicy` -> `DestinationRule`
* `EgressRule` -> `ServiceEntry`
* `Ingress` -> `Gateway` (recommended to use)

The detail : Please refer to the 
[Route Rules v1alpha3](https://istio.io/docs/reference/config/istio.networking.v1alpha3)?

---

![](https://c2.staticflickr.com/2/1728/41828692835_02163bd116_z.jpg)

I deployed following two services.

* ***front-service*** as Front End Service
* ***trans-text-service*** as Back End Service


### 6.4.1 front-service deployment

In order to deploy the front-service, I created following artifact file.

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: front-service
spec:
  selector:
    matchLabels:
      app: front-service
  replicas: 2
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
        app: front-service
        version: v1
    spec:
      imagePullSecrets:
        - name: docker-reg-credential
      containers:
      - name: front-service
        image: yoshio.azurecr.io/tyoshio2002/front-end-svc:1.0
        env:
        - name: TRANSLATOR_SERVER_URL
          valueFrom:
            configMapKeyRef:
              name: front-service-config
              key: TRANSLATOR_SERVER_URL
        livenessProbe:
          httpGet:
            path: /app/front/top/health
            port: 8080
          initialDelaySeconds: 120
          timeoutSeconds: 5
          periodSeconds: 10
          failureThreshold: 3
        readinessProbe:
          httpGet:
            path: /app/front/top/health
            port: 8080
          initialDelaySeconds: 120
          timeoutSeconds: 5
          periodSeconds: 10
          failureThreshold: 3
        resources:
          limits:
            memory: "1Gi"
          requests:
            memory: "600Mi"
---
apiVersion: v1
kind: Service
metadata:
  labels:
    app: front-service
  name: front-service
spec:
  ports:
  - port: 80
    name: http
    targetPort: 8080
  - port: 443
    name: https
    targetPort: 8080
  selector:
    app: front-service
  sessionAffinity: None
  type: ClusterIP
```
***(This is the configuration for k8s 1.9.x.)***  

For connecting to the backend service(trans-service), I created following Config map to change the destination easily based on 12 Factor App.

```
apiVersion: v1
kind: ConfigMap
metadata:
  name: front-service-config
#  namespace: test-env
data:
  TRANSLATOR_SERVER_URL: "trans-text-service.default.svc.cluster.local"
```

For access to the above front service, I created following VirtualService. The following means that if user access to the server with prefix uri "/app/front", all of access will transfer to the front-service(front-service.default.svc.cluster.local).  
And the Gateway (External access) will be used as "front-gateway".

```
apiVersion: networking.istio.io/v1alpha3
kind: VirtualService
metadata:
  name: front-virtual-service
spec:
  hosts:
  - "*"
  gateways:
  - front-gateway
  http:
  - match:
    - uri:
        prefix: /app/front
    route:
    - destination:
        host: front-service
```

The following is the Gateway configuration, it used ingressgateway not ingress. 

```
apiVersion: networking.istio.io/v1alpha3
kind: Gateway
metadata:
  name: front-gateway
spec:
  selector:
    istio: ingressgateway # use istio default controller
  servers:
  - port:
      number: 80
      name: http
      protocol: HTTP
    hosts:
    - "*"
```

If you executed the following command, you can confirm the external IP address for "***ingressgateway (40.xxx.xxx.xxx)***".


```
$ kubectl get svc -n istio-system|grep gateway
istio-egressgateway        ClusterIP      10.0.165.12    <none>           80/TCP,443/TCP                                                        6d
istio-ingressgateway       LoadBalancer   10.0.165.34    40.xxx.xxx.xxx    80:31380/TCP,443:31390/TCP,31400:31400/TCP                            6d
```

And inside of your front-service, Pleaase crete like following code?

```
    @GET
    @Path(value = "/health")
    public Response healthCheck() {
        return Response.ok("Healthy").build();
    }
```

After that, you can confirm the availability of the service like follows.

```
$ curl -v 40.xxx.xxx.xxx/app/front/top/health
*   Trying 40.xxx.xxx.xxx...
* TCP_NODELAY set
* Connected to 40.xxx.xxx.xxx (40.xxx.xxx.xxx) port 80 (#0)
> GET /app/front/top/health HTTP/1.1
> Host: 40.xxx.xxx.xxx
> User-Agent: curl/7.54.0
> Accept: */*
> 
< HTTP/1.1 200 OK
< server: envoy
< content-type: text/plain
< date: Mon, 11 Jun 2018 06:55:26 GMT
< content-length: 7
< x-frame-options: SAMEORIGIN
< x-envoy-upstream-service-time: 26
< 
* Connection #0 to host 40.xxx.xxx.xxx left intact
Healthy
```

### 6.4.2 trans-text-service deployment (Two Version)

In order to provide the translation service I deployed a "trans-service". This  is text translation service from English to Japanese. And I added two label for this Deploymetn as ***"app: trans-service"*** and ***"version: vX"***.

And also in order to expose the above deployment, I created "trans-service"  ***Services***. In the definition, I only wrote the ***app: trans-service***. on selector (not included the "version: v1"). It means that the Service can expose multiple version of trans-services. It is the benefit of the label and selector of Kubernetes.

For Version 1 of "trans-text-service", I created following YAML.

```
apiVersion: extensions/v1beta1
kind: Deployment
metadata:
  name: trans-text-service-v1       # <------ Need to change
spec:
  replicas: 2
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
        app: trans-text-service
        version: v1                 # <------ Need to change
    spec:
      imagePullSecrets:
        - name: docker-reg-credential
      containers:
      - name: trans-text-service
        image: yoshio.azurecr.io/tyoshio2002/trans-text-service:1.0   # <------ Need to change
        # terminationGracePeriodSeconds: 60
        livenessProbe:
          httpGet:
            path: /app/translate/message
            port: 8080
          initialDelaySeconds: 120
          timeoutSeconds: 5
          periodSeconds: 10
          failureThreshold: 3
        readinessProbe:
          httpGet:
            path: /app/translate/message
            port: 8080
          initialDelaySeconds: 120
          timeoutSeconds: 5
          periodSeconds: 10
          failureThreshold: 3
        resources:
          limits:
            memory: "1Gi"
          requests:
            memory: "600Mi"
---
apiVersion: v1
kind: Service
metadata:
  labels:
    app: trans-text-service
  name: trans-text-service
spec:
  ports:
  - port: 80
    name: http
    targetPort: 8080
  - port: 443
    name: https
    targetPort: 8080
  selector:
    app: trans-text-service
  sessionAffinity: None
  type: LoadBalancer

```


In order to deploy the Deployment and Service, please execute following command?

```
$ kubectl apply -f <(/usr/local/bin/istioctl kube-inject \
-f ./create-deployment-svc.yaml)
```

After modified the source code and created v2 of the service, please create the YAML file for Version2 like follows?

```
apiVersion: extensions/v1beta1
kind: Deployment
metadata:
  name: trans-text-service-v2       # <------ Need to change
spec:
  replicas: 2
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
        app: trans-text-service
        version: v2                 # <------ Need to change
    spec:
      imagePullSecrets:
        - name: docker-reg-credential
      containers:
      - name: trans-text-service
        image: yoshio.azurecr.io/tyoshio2002/trans-text-service:2.0   # <------ Need to change
        # terminationGracePeriodSeconds: 60
        livenessProbe:
          httpGet:
            path: /app/translate/message
            port: 8080
          initialDelaySeconds: 120
          timeoutSeconds: 5
          periodSeconds: 10
          failureThreshold: 3
        readinessProbe:
          httpGet:
            path: /app/translate/message
            port: 8080
          initialDelaySeconds: 120
          timeoutSeconds: 5
          periodSeconds: 10
          failureThreshold: 3
        resources:
          limits:
            memory: "1Gi"
          requests:
            memory: "600Mi"
```

After deployed to the k8s, you can see 4 pods are running with the name of label is "app=trans-text-service".

```
$ kubectl get po --selector app=trans-text-service
NAME                                     READY     STATUS    RESTARTS   AGE
trans-text-service-8454d4dbb-45pc4       2/2       Running   0          3h
trans-text-service-8454d4dbb-t6glh       2/2       Running   0          3h
trans-text-service-v2-7fb4479b74-g72sg   2/2       Running   0          1h
trans-text-service-v2-7fb4479b74-mjzwb   2/2       Running   0          1h
```

Of course, you can filter the application with version as follows.  

***Version 1***  

```
$ kubectl get po --selector app=trans-text-service,version=v1
NAME                                 READY     STATUS    RESTARTS   AGE
trans-text-service-8454d4dbb-45pc4   2/2       Running   0          3h
trans-text-service-8454d4dbb-t6glh   2/2       Running   0          3h
```

***Version 2***  

```
$ kubectl get po --selector app=trans-text-service,version=v2
NAME                                     READY     STATUS    RESTARTS   AGE
trans-text-service-v2-7fb4479b74-g72sg   2/2       Running   0          1h
trans-text-service-v2-7fb4479b74-mjzwb   2/2       Running   0          1h
```


### 6.4.3 Transfer the request to services as Round Robin
After deployed the two version of the Translation service, you need to create the VirtualService and DeploymentRule for accessing to the above services.

![](https://c2.staticflickr.com/2/1728/41828692835_02163bd116_z.jpg)


At first, please create the following VirtualService?

```
apiVersion: networking.istio.io/v1alpha3
kind: VirtualService
metadata:
  name: translate-virtual-service
spec:
  hosts:
  - "trans-text-service"
  http:
  - route:
    - destination:
        host: trans-text-service
```

After created the above, please create the VirtualService like follows.

```
$ istioctl create -f VirtualService.yaml 
Created config virtual-service/default/translate-virtual-service at revision 6952922
$
```

Then if your application connect Front(front-service) and Back(trans-text-service) correctly, you can access to the service via the Gateway (Istio-IngressGateway).

In this situation, I access to the Gateway IP address with following URL, the server response "version-1" and "version-2" as round robin.

```
#### This is the requests for V2 
$ curl http://40.xxx.xxx.xxx/app/front/top/trans-service?eng=this%20is%20a%20pen
{"hostname":"trans-text-service-v2-7fb4479b74-mjzwb","value":"JSON VALUE。","version":"version-2"}

#### This is the requests for V1 
$ curl http://40.xxx.xxx.xxx/app/front/top/trans-service?eng=this%20is%20a%20pen
{"hostname":"trans-text-service-8454d4dbb-45pc4","value":"JSON VALUE。","version":"version-1"}

#### This is the requests for V1 
$ curl http://40.xxx.xxx.xxx/app/front/top/trans-service?eng=this%20is%20a%20pen
{"hostname":"trans-text-service-8454d4dbb-45pc4","value":"JSON VALUE。","version":"version-1"}

#### This is the requests for V2 
$ curl http://40.xxx.xxx.xxx/app/front/top/trans-service?eng=this%20is%20a%20pen
{"hostname":"trans-text-service-v2-7fb4479b74-g72sg","value":"JSON VALUE。","version":"version-2"}$
```


### 6.4.4 Weight Routing to 100% specific service

If you would like to transfer the request to the "version-1" only, you need to create following VirtualService with DestinationRule.  

![](https://c2.staticflickr.com/2/1725/40918122510_e030e08732_z.jpg)

The following means that 100% of request send to the "trans-service" with label "version: v1".

```
apiVersion: networking.istio.io/v1alpha3
kind: VirtualService
metadata:
  name: translate-virtual-service
spec:
  hosts:
  - "trans-text-service"
  http:
  - route:
    - destination:
        host: trans-text-service
        subset: v1
```

And in order to access to the Version 1 only, you need to create the 
***Subset*** inside of the ***DestinationRule***.

```
apiVersion: networking.istio.io/v1alpha3
kind: DestinationRule
metadata:
  name: trans-text-service
spec:
  host: trans-text-service
  subsets:
  - name: v1
    labels:
      app: trans-text-service
      version: v1
  - name: v2
    labels:
      app: trans-text-service
      version: v2
```

In order to create the RouteRule, please execute the following command?

```
$ istioctl create -f DestinationRule.yaml 
Created config destination-rule/default/trans-text-service at revision 6225687
$ istioctl replace -f VirtualService.yaml
Updated config virtual-service/default/translate-virtual-service to revision 6225718
```

Then all of the request will be send to "version1" and never send the request to v2.

```
#### This is the requests for V1 only
$ curl http://40.xxx.xxx.xxx/app/front/top/trans-service?eng=this%20is%20a%20pen
{"hostname":"trans-text-service-8454d4dbb-t6glh","value":"JSON VALUE。","version":"version-1"}
$ curl http://40.xxx.xxx.xxx/app/front/top/trans-service?eng=this%20is%20a%20pen
{"hostname":"trans-text-service-8454d4dbb-t6glh","value":"JSON VALUE。","version":"version-1"}
$ curl http://40.xxx.xxx.xxx/app/front/top/trans-service?eng=this%20is%20a%20pen
{"hostname":"trans-text-service-8454d4dbb-45pc4","value":"JSON VALUE。","version":"version-1"}
$ curl http://40.xxx.xxx.xxx/app/front/top/trans-service?eng=this%20is%20a%20pen
{"hostname":"trans-text-service-8454d4dbb-45pc4","value":"JSON VALUE。","version":"version-1"}
```

### 6.4.5 Restrict access for special Header

For example, if you are developer and would like to confirm the behavior of service before release offcially. Or if you would like to restrict the access for specific users like beta tester, you can transer the request for specific users only.

![](https://c2.staticflickr.com/2/1723/41828692625_a5e0725338_z.jpg)

In fact, If you would like to restrict the access which have specific HTTP header, then you can write like follows. In this sample, if the use specify the special header as ***super: super-secret***,  only then it will send the request to v2 of trans service. If you didn't specify the above header, all of the request will be send to v1.

```
apiVersion: networking.istio.io/v1alpha3
kind: VirtualService
metadata:
  name: translate-virtual-service
spec:
  hosts:
  - "trans-text-service"
  http:
  - match:
    - headers:
        super:
          exact: super-secret
    route:
    - destination:
        host: trans-text-service
        subset: v2
  - route:
    - destination:
        host: trans-text-service
        subset: v1
```

In order to apply the above VirtualService, please execute the following command?

```
$ istioctl replace -f VirtualService.yaml
Updated config virtual-service/default/translate-virtual-service to revision 6230402
```

After appied it, please access to the Gateway IP address like follows. Then if you don't specify the special header, all of requests will be send to the version1. And only if you specify the special header, the reuqest will send to version2.

```
#### This is the requests for V1 
$ curl http://40.xxx.xxx.xxx/app/front/top/trans-service?eng=this%20is%20a%20pen
{"hostname":"trans-text-service-8454d4dbb-45pc4","value":"JSON VALUE。","version":"version-1"}
$ curl http://40.xxx.xxx.xxx/app/front/top/trans-service?eng=this%20is%20a%20pen
{"hostname":"trans-text-service-8454d4dbb-45pc4","value":"JSON VALUE。","version":"version-1"}
$ curl http://40.xxx.xxx.xxx/app/front/top/trans-service?eng=this%20is%20a%20pen
{"hostname":"trans-text-service-8454d4dbb-t6glh","value":"JSON VALUE。","version":"version-1"}

#### This is the requests for V2 with Special Header
$ curl -H "super: super-secret" http://40.xxx.xxx.xxx/app/front/top/trans-service?eng=this%20is%20a%20pen
{"hostname":"trans-text-service-v2-7fb4479b74-mjzwb","value":"JSON VALUE。","version":"version-2"}
$ curl -H "super: super-secret" http://40.xxx.xxx.xxx/app/front/top/trans-servis%20is%20a%20pen
{"hostname":"trans-text-service-v2-7fb4479b74-mjzwb","value":"JSON VALUE。","version":"version-2"}
$ curl -H "super: super-secret" http://40.xxx.xxx.xxx/app/front/top/trans-service?eng=this%20is%20a%20pen
{"hostname":"trans-text-service-v2-7fb4479b74-g72sg","value":"JSON VALUE。","version":"version-2"}
```

Note:  
1. You must write the definitiuon for V2 is ealier than V1. If you wrote the V1 first, V2 will be disregarded.  
2. Also you need to propagete the special HTTP Header from front-services to backend (translator) service.


### 6.4.6 Canary Release

If you would like to weight the trafic rule, you can specify like follows. 

![](https://c2.staticflickr.com/2/1739/28855012668_9802a3c4f8_z.jpg)

In this example, 80% of request will be transfer to the V1. And 20% of the traffic will be transfer to the V2.

```
apiVersion: networking.istio.io/v1alpha3
kind: VirtualService
metadata:
  name: translate-virtual-service
spec:
  hosts:
  - "trans-text-service"
  http:
  - route:
    - destination:
        host: trans-text-service
        subset: v2
      weight: 20
    - destination:
        host: trans-text-service
        subset: v1
      weight: 80
```

In order to apply the above, please execute following?

```
$ istioctl replace -f VirtualService.yaml
Updated config virtual-service/default/translate-virtual-service to revision 6231476
```

Then you can confirm the traffic as follows. In fact 80% of the traffic was send to v1 and 20% of the request was send to v2.

```
$ curl http://40.xxx.xxx.xxx/app/front/top/trans-service?eng=this%20is%20a%20pen
{"hostname":"trans-text-service-8454d4dbb-45pc4","value":"JSON VALUE。","version":"version-1"}
$ curl http://40.xxx.xxx.xxx/app/front/top/trans-service?eng=this%20is%20a%20pen
{"hostname":"trans-text-service-8454d4dbb-t6glh","value":"JSON VALUE。","version":"version-1"}
$ curl http://40.xxx.xxx.xxx/app/front/top/trans-service?eng=this%20is%20a%20pen
{"hostname":"trans-text-service-8454d4dbb-t6glh","value":"JSON VALUE。","version":"version-1"}

#### This is the requests for V2 
$ curl http://40.xxx.xxx.xxx/app/front/top/trans-service?eng=this%20is%20a%20pen
{"hostname":"trans-text-service-v2-7fb4479b74-g72sg","value":"JSON VALUE。","version":"version-2"}

#### This is the requests for V2 
$ curl http://40.xxx.xxx.xxx/app/front/top/trans-service?eng=this%20is%20a%20pen
{"hostname":"trans-text-service-v2-7fb4479b74-g72sg","value":"JSON VALUE。","version":"version-2"}

$ curl http://40.xxx.xxx.xxx/app/front/top/trans-service?eng=this%20is%20a%20pen
{"hostname":"trans-text-service-8454d4dbb-45pc4","value":"JSON VALUE。","version":"version-1"}
$ curl http://40.xxx.xxx.xxx/app/front/top/trans-service?eng=this%20is%20a%20pen
{"hostname":"trans-text-service-8454d4dbb-45pc4","value":"JSON VALUE。","version":"version-1"}
$ curl http://40.xxx.xxx.xxx/app/front/top/trans-service?eng=this%20is%20a%20pen
{"hostname":"trans-text-service-8454d4dbb-t6glh","value":"JSON VALUE。","version":"version-1"}
$ curl http://40.xxx.xxx.xxx/app/front/top/trans-service?eng=this%20is%20a%20pen
{"hostname":"trans-text-service-8454d4dbb-t6glh","value":"JSON VALUE。","version":"version-1"}
$ curl http://40.xxx.xxx.xxx/app/front/top/trans-service?eng=this%20is%20a%20pen
{"hostname":"trans-text-service-8454d4dbb-45pc4","value":"JSON VALUE。","version":"version-1"}
$
```


### 6.4.7 Request Timeout

In your back end service, sometimes it will take a long time to operate all of the backend services. In this situation, if so many request come to the system, your front-end service may consume all of the resources for network Thread Pool and will be queued the request. As a result, all of the system may damage from the backend service trouble. In order to avoid such the situation, you can configure the Request Timeout for invoking the service.


In my example, I added the waiting code as follows. The following will sleep some seconds between 0s to 5s.

```
        SecureRandom rand = new SecureRandom(); 
        int x = rand.nextInt(5000);        
        Thread.sleep(x);
```

After that, I added the ***timeout*** elements with 3s in the VirtualService. It will wait until 3s. And if it over the second, the request timeout will occure.


```
apiVersion: networking.istio.io/v1alpha3
kind: VirtualService
metadata:
  name: translate-virtual-service
spec:
  hosts:
  - "trans-text-service"
  http:
  - route:
    - destination:
        host: trans-text-service
        subset: v3
    timeout: 3s
```

After created the above file, please execute following to apply?

```
$ istioctl replace -f VirtualService-RequestTimeout.yaml 
Updated config virtual-service/default/translate-virtual-service to revision 7101372
```

Then you can access to the service.  
If the request time is over,  "***upstream request timeout***" message will showed.


```
$ curl http://40.113.230.96/app/front/top/trans-service?eng=this%20is%20a%20pen
{"hostname":"trans-text-service-v3-64564595cd-wwh27","value":"JSON VALUE。","version":"version-3"} 

#### Request Timeout occur on this request
$ curl http://40.113.230.96/app/front/top/trans-service?eng=this%20is%20a%20pen
upstream request timeout 

#### Request Timeout occur on this request
$ curl http://40.113.230.96/app/front/top/trans-service?eng=this%20is%20a%20pen
upstream request timeout 

$ curl http://40.113.230.96/app/front/top/trans-service?eng=this%20is%20a%20pen
{"hostname":"trans-text-service-v3-64564595cd-wwh27","value":"JSON VALUE。","version":"version-3"} 
```


### 6.4.8 CircuitBreaker 

TODO : Need to write for Istio 0.8.0.


---
[Previous Page](Kubernetes-Workshop5.md) / [Next Page](Kubernetes-Workshop7.md)
