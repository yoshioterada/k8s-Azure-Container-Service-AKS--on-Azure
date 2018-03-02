[Previous Page](Kubernetes-Workshop5.md) / [Next Page](Kubernetes-Workshop7.md)
---
***Note: This contents is Beta version!!***

---
* [Azure 無料アカウントのご取得はコチラから](https://aka.ms/jjug_mar2)  
***Azure の無料アカウントで料金は発生しますか？  
いいえ。無料で開始でき、最初の 30 日間に使用する ¥22,500 クレジットを取得します。サービスの利用を開始した後でも、アップグレードするまでは無料で利用できます。***  

---

# 6. Istio (Service Mesh)

## 6.1 About Istio

[https://github.com/istio/istio/releases/](https://github.com/istio/istio/releases/)


## 6.2 Install Istio

### 6.2.1 Download & Intall
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

```
$ kubectl apply -f addons/grafana.yaml 
service "grafana" created
deployment "grafana" created
```

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

```
$ kubectl -n istio-system port-forward $(kubectl -n istio-system get \
  pod -l app=grafana -o jsonpath='{.items[0].metadata.name}') 3000:3000 &
```


#### Install Prometheus

```
$ kubectl apply -f addons/prometheus.yaml
configmap "prometheus" created
service "prometheus" created
deployment "prometheus" created
```

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

```
$ kubectl -n istio-system port-forward $(kubectl -n istio-system get \
  pod -l app=prometheus -o jsonpath='{.items[0].metadata.name}') 9090:9090 &
```

#### Install Jaeger (Distributed Tracing instead of Zipkin)

```
kubectl apply -n istio-system -f https://raw.githubusercontent.com/jaegertracing/jaeger-kubernetes/master/all-in-one/jaeger-all-in-one-template.yml
```


```
            @HeaderParam("x-request-id") String xreq,
            @HeaderParam("x-b3-traceid") String xtraceid,
            @HeaderParam("x-b3-spanid") String xspanid,
            @HeaderParam("x-b3-parentspanid") String xparentspanid,
            @HeaderParam("x-b3-sampled") String xsampled,
            @HeaderParam("x-b3-flags") String xflags,
            @HeaderParam("x-ot-span-context") String xotspan)    
```


```
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


```
$ kubectl apply --record -f <(/usr/local/bin/istioctl \
 kube-inject -f ./create-deployment-svc.yaml --includeIPRanges=10.244.0.0/24)
```

## 6.4 Traffic Management

### 6.4.1 Weight Routing to 100% specific service


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
      app: trans-service
      version: v1
    weight: 100
```

```
$ istioctl create -f routerule-v1.yaml
```

### 6.4.2 Restrict access for special Header

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

```
$ istioctl create -f routerule-v2.yaml
```


### 6.4.3 Canary Release

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

```
$ istioctl create -f routerule-v3.yaml
```


### 6.4.4 Request Timeout

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

```
$ istioctl create -f routerule-v4.yaml
```


### 6.4.5 CircuitBreaker 

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

```
$ istioctl create -f dest-rule.yaml
```


---
[Previous Page](Kubernetes-Workshop5.md) / [Next Page](Kubernetes-Workshop7.md)
