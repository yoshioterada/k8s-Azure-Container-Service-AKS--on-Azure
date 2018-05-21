[Previous Page](Kubernetes-Workshop6.md) / [Next Page](Kubernetes-Workshop8.md)
---
***Note: This contents is Beta version!!***



# 7. Take care to use the k8s


## 7.1 Please don't use the latest tag

In general, in order to create the docker image, you execute like following command.

```
# Build docker image
docker build -t tyoshio2002/account-service .
docker tag tyoshio2002/account-service:$VERSION yoshio.azurecr.io/tyoshio2002/account-service:$VERSION

# Push the image to Private Docker Registry
docker push yoshio.azurecr.io/tyoshio2002/account-service:$VERSION
```

In the above command, please don't use the ***latest*** in the ***VERSION*** field? If you use the latest, we can't understand what kind of the version is used in the latest version and also it is difficult to roll back the specific version. In order to undersatnd clearly, please specify the actual degit number? 

The default imagePullPolicy for a container is IfNotPresent, which causes the kubelet to pull an image only if it does not already exist locally. If you want the image to be pulled every time Kubernetes starts the container, specify imagePullPolicy: Always.

An alternative, but deprecated way to have Kubernetes always pull the image is to use the :latest tag, which will implicitly set the imagePullPolicy to Always.


## 7.2 Please consider to create 1 svc on 1 container?

If you include multiple service to 1 container, it is very difficult to manage the contailer like scale up or scale down, or delete or modify.
Please isolate the service to individual container?


## 7.3 Please confirm which version are you using.

The evolution of k8s is extreamly fast, and the command line interface and/or manifest(YAML) statement changed version to version.
So You need to confirm which version you are using.

```
$ kubectl get node
NAME                       STATUS    ROLES     AGE       VERSION
aks-nodepool1-19381275-0   Ready     agent     37m       v1.8.7
aks-nodepool1-19381275-1   Ready     agent     29m       v1.8.7
aks-nodepool1-19381275-2   Ready     agent     21m       v1.8.7
aks-nodepool1-19381275-3   Ready     agent     14m       v1.8.7
aks-nodepool1-19381275-5   Ready     agent     45m       v1.8.7
```

***After upgrade the version of k8s, you need to modify the manifest(yaml) file too.***


## 7.4 Please don't treat pod directly

Pod is volatility and there is no functionality of auto restart. For example, some pod is running and if it failed or downed or something wrong status, it  will be never restared.

```
apiVersion: v1
kind: Pod
metadata:
  name: nginx
spec:
  containers:
  - name: nginx
    image: nginx:1.7.9
    ports:
    - containerPort: 80
```

Instead of using the pod directly, plase use the "***Deployment***"? In the deployment, it has a functionality of the "***ReplicaSet***" with additional function. So I recommend you to use the "Deployment".


## 7.5 Please consider to create more small size image?

For example,   
1. Ubuntu docker image is bigger than Alpine Linux docker image.  

```
openjdk:8-jre         124 MB
openjdk:8-jre-alpine   56 MB
```

Note:   
The alpine linux is not included all of the libraries like SSL. Depends on the services, you need to select the appropriate images.

And not only OS image but also please consider to use JDK or JRE ?

```
# FROM openjdk:8-jdk-alpine
FROM openjdk:8-jre-alpine
```

If I changed the image from JDK(1.18) to JRE(1.19) on Dockerfile, of course I could decrease the size of image.

```
$ docker images |grep account
tyoshio2002/account-service                    1.19     0d1ae7f0e1d4  2 minutes ago   290MB
yoshio.azurecr.io/tyoshio2002/account-service  1.19     0d1ae7f0e1d4  2 minutes ago   290MB
tyoshio2002/account-service                    1.18     1079c35a99f4  9 minutes ago   353MB
yoshio.azurecr.io/tyoshio2002/account-service  1.18     1079c35a99f4  9 minutes ago   353MB
```

***For Java SE 9 :***  
In the above example, I used Java SE 8. However we can create custom JRE since Java SE 9. In order to create more small size of Docker image, please consider to use the Java SE 9 or latest?

* [Slim modular Java 9 runtime Docker image with Alpine Linux](https://blog.jdriven.com/2017/11/modular-java-9-runtime-docker-alpine/)  
* [Building tiny docker containers with JDK9](https://blog.dekstroza.io/building-minimal-docker-containers-with-java-9/)  


## 7.6 Do you trust the images on DockerHub?

If you get the un-trusted image from DockerHub, it is very dangerous. Please push the trusted image only to private Docker Hub?

## 7.7 LoadBalancer is high cost to expose the service

In order to expose the Service, you can specify 4 type for it.

```
apiVersion: v1
kind: Service
metadata:
  labels:
    app: account-service
  name: account-service
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
    app: account-service
  sessionAffinity: None
  type: ClusterIP
```

1. ***ClusterIP***: Exposes the service on a cluster-internal IP. Choosing this value makes the service only reachable from within the cluster. This is the default ServiceType.  
2. ***NodePort***: Exposes the service on each Node’s IP at a static port (the NodePort). A ClusterIP service, to which the NodePort service will route, is automatically created. You’ll be able to contact the NodePort service, from outside the cluster, by requesting <NodeIP>:<NodePort>.  
3. ***LoadBalancer***: Exposes the service externally using a cloud provider’s load balancer. NodePort and ClusterIP services, to which the external load balancer will route, are automatically created.  
4. ***ExternalName***: Maps the service to the contents of the externalName field (e.g. foo.bar.example.com), by returning a CNAME record with its value. No proxying of any kind is set up. This requires version 1.7 or higher of kube-dns.

In the above, please don't use the LoadBalancer many time? It will consume the cost and public IP address. If you would like to connect to the pods in service, you can use the ***"kubectl port-forward"*** command. And you would like to access to the service on production, you will be able to use ***Ingress***.


## 7.8 Please don't access to Node directly by ssh

Node host should be protected and strongly configure the restrict the access.
In general, in order to confirm the status of the pod, please use the ***kubectl exec*** command? instead of node access?

## 7.9 Rollout: Specify "--record" to apply command

Please specify the "***--record***" option?

```
$ kubectl apply --record -f <(/usr/local/bin/istioctl kube-inject -f ./create-deployment-svc.yaml --includeIPRanges=10.244.0.0/24)
deployment "account-service" created
service "account-service" created
```

After specified it, you can confirm your past work behavior which revision you had worked.

```
$ kubectl rollout history deployment/account-service
deployments "account-service"
REVISION  CHANGE-CAUSE
1         kubectl apply --record=true --filename=/dev/fd/63
```

If you don't specify it, there was no CHANGE-CAUSE.

```
$ kubectl rollout history deployment/front-service
deployments "front-service"
REVISION  CHANGE-CAUSE
1         <none>
2         <none>
3         <none>
4         <none>
5         <none>
6         <none>
```

You can also confirm the detail of the revision like follows.

```
$ kubectl rollout history deployment/account-service --revision=1|grep Image
    Image:	docker.io/istio/proxy_init:0.5.0
    Image:	alpine
    Image:	yoshio.azurecr.io/tyoshio2002/account-service:1.17
    Image:	docker.io/istio/proxy:0.5.0
```

You would like to Roll Back to Previous version, you can execute following command.

```
$ kubectl rollout undo deployment/account-service --to-revision=N
```

## 7.10 Use configmap or secret for configure the external service

Based on the [12 Factor App](https://12factor.net/), you should configure the Backend service to the config map or secret as config server.

For example, in your microservice application, you need to access to another micro services, then you should configure the URL or host name to config map like follows.

```
apiVersion: v1
kind: ConfigMap
metadata:
  name: account-service-config
  namespace: order-system-production
data:
  ACCOUT_SERVICE_SERVER_URL: "account-service.order-system-production.svc.cluster.local"
  CUSTOMER_MANAGEMENT_SERVER_URL: "customer-management-service.order-system-production.svc.cluster.local"
  TRANSLATOR_SERVER_URL: "trans-service.order-system-production.svc.cluster.local"
```

For example, you need to configure like credential information, you should configure them to the Secret like follows.

```
apiVersion: v1
data:
  database: an******dA==
  host: ZmI4YTgzNWEt****************************sLmRhdGFiYXNlLmF6dXJlLmNvbQo=
  password: WTBzaGkwMTE=
  port: MzMwNg==
  username: YXp1cmV1****************************Mi05ZGQ1LTUwMWE5MWJlMTEyYQo=
  jdbcurl: amRiYzpteXN***********************************UJlaGF2aW9yPWNvbnZlcnRUb051bGw=
kind: Secret
metadata:
  creationTimestamp: 2018-02-01T05:29:01Z
  name: mysql-secret-for-jsr
  namespace: order-system-production
  ownerReferences:
  - apiVersion: servicecatalog.k8s.io/v1beta1
    blockOwnerDeletion: true
    controller: true
    kind: ServiceBinding
    name: mysql-secret-for-jsr
    uid: cf25cf7b-0710-11e8-81c6-0a580af40218
  resourceVersion: "2982943"
  selfLink: /api/v1/namespaces/order-system-production/secrets/mysql-secret-for-jsr
  uid: cf8d468d-0710-11e8-8fa4-0a58ac1f264c
type: Opaque
```


---
[Previous Page](Kubernetes-Workshop6.md) / [Next Page](Kubernetes-Workshop8.md)
