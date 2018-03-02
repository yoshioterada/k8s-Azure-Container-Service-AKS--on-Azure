[Previous Page](Kubernetes-Workshop3.md) / [Next Page](Kubernetes-Workshop5.md)
---

***Note: This contents is Beta version!!***

---
* [Azure 無料アカウントのご取得はコチラから](https://aka.ms/jjug_mar2)  
***Azure の無料アカウントで料金は発生しますか？  
いいえ。無料で開始でき、最初の 30 日間に使用する ¥22,500 クレジットを取得します。サービスの利用を開始した後でも、アップグレードするまでは無料で利用できます。***  

---


# 4. Most userful kubectl command

## 4.1 About kubectl command

In order to manage the k8s, we use the kubectl command. kubectl has many argument as follows. In this section, I will introduce you the most useful command to manage and monitor the k8s.

```
$ kubectl 
kubectl controls the Kubernetes cluster manager. 

Find more information at https://github.com/kubernetes/kubernetes.

Basic Commands (Beginner):
  create         Create a resource from a file or from stdin.
  expose         Take a replication controller, service, deployment or pod and expose it as a new Kubernetes Service
  run            Run a particular image on the cluster
  set            Set specific features on objects
  run-container  Run a particular image on the cluster. This command is deprecated, use "run" instead

Basic Commands (Intermediate):
  get            Display one or many resources
  explain        Documentation of resources
  edit           Edit a resource on the server
  delete         Delete resources by filenames, stdin, resources and names, or by resources and label selector

Deploy Commands:
  rollout        Manage the rollout of a resource
  rolling-update Perform a rolling update of the given ReplicationController
  scale          Set a new size for a Deployment, ReplicaSet, Replication Controller, or Job
  autoscale      Auto-scale a Deployment, ReplicaSet, or ReplicationController

Cluster Management Commands:
  certificate    Modify certificate resources.
  cluster-info   Display cluster info
  top            Display Resource (CPU/Memory/Storage) usage.
  cordon         Mark node as unschedulable
  uncordon       Mark node as schedulable
  drain          Drain node in preparation for maintenance
  taint          Update the taints on one or more nodes

Troubleshooting and Debugging Commands:
  describe       Show details of a specific resource or group of resources
  logs           Print the logs for a container in a pod
  attach         Attach to a running container
  exec           Execute a command in a container
  port-forward   Forward one or more local ports to a pod
  proxy          Run a proxy to the Kubernetes API server
  cp             Copy files and directories to and from containers.
  auth           Inspect authorization

Advanced Commands:
  apply          ファイル名を指定または標準入力経由でリソースにコンフィグを適用する
  patch          Update field(s) of a resource using strategic merge patch
  replace        Replace a resource by filename or stdin
  convert        Convert config files between different API versions

Settings Commands:
  label          Update the labels on a resource
  annotate       リソースのアノテーションを更新する
  completion     Output shell completion code for the specified shell (bash or zsh)

Other Commands:
  api-versions   Print the supported API versions on the server, in the form of "group/version"
  config         kubeconfigファイルを変更する
  help           Help about any command
  plugin         Runs a command-line plugin
  version        Print the client and server version information
```

## 4.2 Display one or more resources

In order to display resorces, we can use the following command.

```
$ kubectl  get [RESOURCES]
```

For ***[RESOURCES]***, you can specify following. And some of the resources, you can specify short argument which is wrote on ***(aka '   ')*** like ***kubectl get po***.

```
  * all  
  * certificatesigningrequests (aka 'csr')  
  * clusterrolebindings  
  * clusterroles  
  * componentstatuses (aka 'cs')  
  * configmaps (aka 'cm')  
  * controllerrevisions  
  * cronjobs  
  * customresourcedefinition (aka 'crd')  
  * daemonsets (aka 'ds')  
  * deployments (aka 'deploy')  
  * endpoints (aka 'ep')  
  * events (aka 'ev')  
  * horizontalpodautoscalers (aka 'hpa')  
  * ingresses (aka 'ing')  
  * jobs  
  * limitranges (aka 'limits')  
  * namespaces (aka 'ns')  
  * networkpolicies (aka 'netpol')  
  * nodes (aka 'no')  
  * persistentvolumeclaims (aka 'pvc')  
  * persistentvolumes (aka 'pv')  
  * poddisruptionbudgets (aka 'pdb')  
  * podpreset  
  * pods (aka 'po')  
  * podsecuritypolicies (aka 'psp')  
  * podtemplates  
  * replicasets (aka 'rs')  
  * replicationcontrollers (aka 'rc')  
  * resourcequotas (aka 'quota')  
  * rolebindings  
  * roles  
  * secrets  
  * serviceaccounts (aka 'sa')  
  * services (aka 'svc')  
  * statefulsets (aka 'sts')  
  * storageclasses (aka 'sc')
```

## 4.3 kubectl get namespaces(ns)

```
$ kubectl get ns
NAME                      STATUS    AGE
default                   Active    45d
istio-system              Active    20d
kube-public               Active    45d
kube-system               Active    45d
order-system-production   Active    31d
weave                     Active    5d

```


## 4.4 kubectl get pods(po)

If you would like to get the pods in the current context, you just enter the following command.

```
$ kubectl get po
NAME                                          READY     STATUS    RESTARTS   AGE
account-service-2364258777-0rg8s              2/2       Running   0          12d
customer-management-service-872683917-9bf70   2/2       Running   0          13d
details-v1-1476947904-7gbw4                   2/2       Running   0          12d
front-service-267342376-m5tsd                 2/2       Running   0          12d
jsr-confirm-service-msa-1661152496-rgkvt      1/1       Running   0          5d
productpage-v1-1037638640-4v77c               2/2       Running   0          12d
ratings-v1-353054424-v655p                    2/2       Running   0          12d
reviews-v1-401049526-7xlbs                    2/2       Running   0          12d
reviews-v2-4140793682-qlm86                   2/2       Running   0          12d
reviews-v3-3651831602-kwzqn                   2/2       Running   0          12d
trans-service-v1-249069369-zrf3w              2/2       Running   0          11d
trans-service-v2-2894455495-5lh2h             2/2       Running   0          11d
trans-service-v3-413623198-wh0mv              2/2       Running   0          11d
yoshio-service-3546834635-8fpkk               2/2       Running   0          11d
yoshio-service-3546834635-r2h3l               2/2       Running   0          11d
```

If you would like toget the pod in specific namespece, you can specify the namespace with ***-n NAME_OF_NAMESPACE***.

```
$ kubectl get po -n istio-system
NAME                                 READY     STATUS    RESTARTS   AGE
grafana-3098480057-44s4s             1/1       Running   0          20d
istio-ca-2330594145-f16t6            1/1       Running   0          20d
istio-ingress-4274189616-pkrbb       1/1       Running   0          12d
istio-mixer-2464598866-c9jht         3/3       Running   0          12d
istio-pilot-2201259095-9jc2g         2/2       Running   0          20d
jaeger-deployment-1058555976-005xj   1/1       Running   0          11d
prometheus-168775884-jnphl           1/1       Running   0          12d
```

If you would like to confirm the status change of the resources, you can specify the "***-w***" option. After you specified it, you can see the status change in the console.

```
$ kubectl get po -w
NAME                                          READY     STATUS    RESTARTS   AGE
account-service-2364258777-0rg8s              2/2       Running   0          12d
customer-management-service-872683917-9bf70   2/2       Running   0          13d
details-v1-1476947904-7gbw4                   2/2       Running   0          12d
front-service-267342376-m5tsd                 2/2       Running   0          12d
jsr-confirm-service-msa-1661152496-rgkvt      1/1       Running   0          5d
productpage-v1-1037638640-4v77c               2/2       Running   0          12d
ratings-v1-353054424-v655p                    2/2       Running   0          12d
reviews-v1-401049526-7xlbs                    2/2       Running   0          12d
reviews-v2-4140793682-qlm86                   2/2       Running   0          12d
reviews-v3-3651831602-kwzqn                   2/2       Running   0          12d
trans-service-v1-249069369-zrf3w              2/2       Running   0          11d
trans-service-v2-2894455495-5lh2h             2/2       Running   0          11d
trans-service-v3-413623198-wh0mv              2/2       Running   0          11d
yoshio-service-3546834635-8fpkk               2/2       Running   0          11d
yoshio-service-3546834635-r2h3l               2/2       Running   0          11d
```



If you would like to get all of the pods in the all namespaces, you can specify "***--all-namespaces***" option.

```
$ kubectl get pods --all-namespaces
NAMESPACE                 NAME                                          READY     STATUS    RESTARTS   AGE
default                   omsagent-60cq9                                1/1       Running   1          45d
default                   omsagent-fjw6d                                1/1       Running   4          32d
default                   omsagent-ppvjm                                1/1       Running   1          45d
default                   omsagent-qvwp5                                1/1       Running   1          45d
default                   omsagent-rbjb9                                1/1       Running   2          45d
istio-system              grafana-3098480057-44s4s                      1/1       Running   0          20d
istio-system              istio-ca-2330594145-f16t6                     1/1       Running   0          20d
istio-system              istio-ingress-4274189616-pkrbb                1/1       Running   0          12d
istio-system              istio-mixer-2464598866-c9jht                  3/3       Running   0          12d
istio-system              istio-pilot-2201259095-9jc2g                  2/2       Running   0          20d
istio-system              jaeger-deployment-1058555976-005xj            1/1       Running   0          11d
istio-system              prometheus-168775884-jnphl                    1/1       Running   0          12d
kube-system               heapster-342135353-t9dg2                      2/2       Running   0          31d
kube-system               kube-dns-v20-1654923623-1vb74                 3/3       Running   2          45d
kube-system               kube-dns-v20-1654923623-9j64z                 3/3       Running   0          12d
kube-system               kube-proxy-b40jl                              1/1       Running   0          45d
kube-system               kube-proxy-bdxgq                              1/1       Running   0          45d
kube-system               kube-proxy-nb7xr                              1/1       Running   0          45d
kube-system               kube-proxy-nsc76                              1/1       Running   0          45d
kube-system               kube-proxy-q2qrv                              1/1       Running   0          45d
kube-system               kube-svc-redirect-5k105                       1/1       Running   0          45d
kube-system               kube-svc-redirect-5vb34                       1/1       Running   0          45d
kube-system               kube-svc-redirect-8hnvb                       1/1       Running   1          45d
kube-system               kube-svc-redirect-k08jw                       1/1       Running   0          45d
kube-system               kube-svc-redirect-n18s5                       1/1       Running   0          45d
kube-system               kubernetes-dashboard-1672970692-zz0ch         1/1       Running   0          29d
kube-system               tiller-deploy-352283156-6zsq2                 1/1       Running   4          12d
kube-system               tunnelfront-2324165455-lk2b4                  1/1       Running   0          12d
order-system-production   account-service-2364258777-0rg8s              2/2       Running   0          12d
order-system-production   customer-management-service-872683917-9bf70   2/2       Running   0          13d
order-system-production   details-v1-1476947904-7gbw4                   2/2       Running   0          12d
order-system-production   front-service-267342376-m5tsd                 2/2       Running   0          12d
order-system-production   jsr-confirm-service-msa-1661152496-rgkvt      1/1       Running   0          5d
order-system-production   productpage-v1-1037638640-4v77c               2/2       Running   0          12d
order-system-production   ratings-v1-353054424-v655p                    2/2       Running   0          12d
order-system-production   reviews-v1-401049526-7xlbs                    2/2       Running   0          12d
order-system-production   reviews-v2-4140793682-qlm86                   2/2       Running   0          12d
order-system-production   reviews-v3-3651831602-kwzqn                   2/2       Running   0          12d
order-system-production   trans-service-v1-249069369-zrf3w              2/2       Running   0          11d
order-system-production   trans-service-v2-2894455495-5lh2h             2/2       Running   0          11d
order-system-production   trans-service-v3-413623198-wh0mv              2/2       Running   0          11d
order-system-production   yoshio-service-3546834635-8fpkk               2/2       Running   0          11d
order-system-production   yoshio-service-3546834635-r2h3l               2/2       Running   0          11d
weave                     weave-scope-agent-9zm33                       1/1       Running   0          2d
weave                     weave-scope-agent-fk9x6                       1/1       Running   0          2d
weave                     weave-scope-agent-j2blr                       1/1       Running   0          2d
weave                     weave-scope-agent-wt3lv                       1/1       Running   0          2d
weave                     weave-scope-agent-xfg24                       1/1       Running   0          2d
weave                     weave-scope-app-1197481323-fxlr8              1/1       Running   0          5d
```

If you would like to get more information such as IP address of the pod and which pod is running on individual node, you can specify "***-o wide***" options.

```
$ kubectl get pods -o wide
NAME                                          READY     STATUS    RESTARTS   AGE       IP             NODE
account-service-2364258777-0rg8s              2/2       Running   0          12d       10.244.2.102   aks-nodepool1-19381275-2
customer-management-service-872683917-9bf70   2/2       Running   0          13d       10.244.3.123   aks-nodepool1-19381275-1
details-v1-1476947904-7gbw4                   2/2       Running   0          12d       10.244.0.159   aks-nodepool1-19381275-0
front-service-267342376-m5tsd                 2/2       Running   0          12d       10.244.2.103   aks-nodepool1-19381275-2
jsr-confirm-service-msa-1661152496-rgkvt      1/1       Running   0          5d        10.244.3.148   aks-nodepool1-19381275-1
productpage-v1-1037638640-4v77c               2/2       Running   0          12d       10.244.0.162   aks-nodepool1-19381275-0
ratings-v1-353054424-v655p                    2/2       Running   0          12d       10.244.0.160   aks-nodepool1-19381275-0
reviews-v1-401049526-7xlbs                    2/2       Running   0          12d       10.244.0.161   aks-nodepool1-19381275-0
reviews-v2-4140793682-qlm86                   2/2       Running   0          12d       10.244.2.100   aks-nodepool1-19381275-2
reviews-v3-3651831602-kwzqn                   2/2       Running   0          12d       10.244.4.142   aks-nodepool1-19381275-4
trans-service-v1-249069369-zrf3w              2/2       Running   0          11d       10.244.1.115   aks-nodepool1-19381275-3
trans-service-v2-2894455495-5lh2h             2/2       Running   0          11d       10.244.3.134   aks-nodepool1-19381275-1
trans-service-v3-413623198-wh0mv              2/2       Running   0          11d       10.244.4.161   aks-nodepool1-19381275-4
yoshio-service-3546834635-8fpkk               2/2       Running   0          11d       10.244.0.183   aks-nodepool1-19381275-0
yoshio-service-3546834635-r2h3l               2/2       Running   0          11d       10.244.4.156   aks-nodepool1-19381275-4
```

If you would like to list the label, you can execute like follows.

```
$ kubectl get deployments --show-labels
NAME                        DESIRED CURRENT UP-TO-DATE AVAILABLE AGE LABELS
account-service             2       2       2          1         5h  app=account-service,version=v1
customer-management-service 1       1       1          1         21d app=customer-management-service
details-v1                  1       1       1          1         13d app=details,version=v1
front-service               1       1       1          1         21d app=front-service,version=v1
jsr-confirm-service-msa     1       1       1          1         13d app=jsr-confirm-service,version=v1
productpage-v1              1       1       1          1         13d app=productpage,version=v1
ratings-v1                  1       1       1          1         13d app=ratings,version=v1
reviews-v1                  1       1       1          1         13d app=reviews,version=v1
reviews-v2                  1       1       1          1         13d app=reviews,version=v2
reviews-v3                  1       1       1          1         13d app=reviews,version=v3
trans-service-v1            1       1       1          1         21d app=trans-service,version=v1
trans-service-v2            1       1       1          1         21d app=trans-service,version=v2
trans-service-v3            1       1       1          1         12d app=trans-service,version=v3
```

And if you would like to get pod with specific label, you can get them like follows.

```
$ kubectl get pods --selector="version=v3"
NAME                               READY     STATUS    RESTARTS   AGE
reviews-v3-3651831602-hjf6d        2/2       Running   0          1d
trans-service-v3-413623198-7n8nm   2/2       Running   0          1d

$ kubectl get pods --selector="version in (v2,v3)"
NAME                                READY     STATUS    RESTARTS   AGE
reviews-v2-4140793682-zlc9b         2/2       Running   0          1d
reviews-v3-3651831602-hjf6d         2/2       Running   0          1d
trans-service-v2-2894455495-wxdzr   2/2       Running   0          1d
```


## 4.5 kubectl get services(svc)

```
$ kubectl get svc
NAME                          TYPE           CLUSTER-IP     EXTERNAL-IP     PORT(S)                      AGE
account-service               ClusterIP      10.0.206.8     <none>          80/TCP,443/TCP               20d
customer-management-service   ClusterIP      10.0.216.145   <none>          80/TCP,443/TCP               20d
details                       ClusterIP      10.0.50.72     <none>          9080/TCP                     12d
front-service                 ClusterIP      10.0.250.99    <none>          80/TCP,443/TCP               20d
jsr-confirm-service           LoadBalancer   10.0.86.130    40.***.***.244   80:32052/TCP,443:32492/TCP   12d
productpage                   ClusterIP      10.0.184.194   <none>          9080/TCP                     12d
ratings                       ClusterIP      10.0.104.5     <none>          9080/TCP                     12d
reviews                       ClusterIP      10.0.138.23    <none>          9080/TCP                     12d
trans-service                 ClusterIP      10.0.225.28    <none>          80/TCP,443/TCP               20d
yoshio-service                ClusterIP      10.0.135.163   <none>          80/TCP,443/TCP               11d
```


## 4.6 kubectl get deployments(deploy)

```
$ kubectl get deploy
NAME                          DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
account-service               1         1         1            1           20d
customer-management-service   1         1         1            1           20d
details-v1                    1         1         1            1           12d
front-service                 1         1         1            1           20d
jsr-confirm-service-msa       1         1         1            1           12d
productpage-v1                1         1         1            1           12d
ratings-v1                    1         1         1            1           12d
reviews-v1                    1         1         1            1           12d
reviews-v2                    1         1         1            1           12d
reviews-v3                    1         1         1            1           12d
trans-service-v1              1         1         1            1           20d
trans-service-v2              1         1         1            1           20d
trans-service-v3              1         1         1            1           11d
yoshio-service                2         2         2            2           11d
```


## 4.7 Show Multiple Resources

If you would like to confirm the multiple resources in one command, you can specify the resouce type as "commna separated" value.


```
$ kubectl get po,svc,deploy
NAME                                             READY     STATUS    RESTARTS   AGE
po/account-service-2364258777-0rg8s              2/2       Running   0          12d
po/customer-management-service-872683917-9bf70   2/2       Running   0          13d
po/details-v1-1476947904-7gbw4                   2/2       Running   0          12d
po/front-service-267342376-m5tsd                 2/2       Running   0          12d
po/jsr-confirm-service-msa-1661152496-rgkvt      1/1       Running   0          5d
po/productpage-v1-1037638640-4v77c               2/2       Running   0          12d
po/ratings-v1-353054424-v655p                    2/2       Running   0          12d
po/reviews-v1-401049526-7xlbs                    2/2       Running   0          12d
po/reviews-v2-4140793682-qlm86                   2/2       Running   0          12d
po/reviews-v3-3651831602-kwzqn                   2/2       Running   0          12d
po/trans-service-v1-249069369-zrf3w              2/2       Running   0          11d
po/trans-service-v2-2894455495-5lh2h             2/2       Running   0          11d
po/trans-service-v3-413623198-wh0mv              2/2       Running   0          11d
po/yoshio-service-3546834635-8fpkk               2/2       Running   0          11d
po/yoshio-service-3546834635-r2h3l               2/2       Running   0          11d

NAME                              TYPE           CLUSTER-IP     EXTERNAL-IP     PORT(S)                      AGE
svc/account-service               ClusterIP      10.0.206.8     <none>          80/TCP,443/TCP               20d
svc/customer-management-service   ClusterIP      10.0.216.145   <none>          80/TCP,443/TCP               20d
svc/details                       ClusterIP      10.0.50.72     <none>          9080/TCP                     12d
svc/front-service                 ClusterIP      10.0.250.99    <none>          80/TCP,443/TCP               20d
svc/jsr-confirm-service           LoadBalancer   10.0.86.130    40.**.***.244   80:32052/TCP,443:32492/TCP   12d
svc/productpage                   ClusterIP      10.0.184.194   <none>          9080/TCP                     12d
svc/ratings                       ClusterIP      10.0.104.5     <none>          9080/TCP                     12d
svc/reviews                       ClusterIP      10.0.138.23    <none>          9080/TCP                     12d
svc/trans-service                 ClusterIP      10.0.225.28    <none>          80/TCP,443/TCP               20d
svc/yoshio-service                ClusterIP      10.0.135.163   <none>          80/TCP,443/TCP               11d

NAME                                 DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
deploy/account-service               1         1         1            1           20d
deploy/customer-management-service   1         1         1            1           20d
deploy/details-v1                    1         1         1            1           12d
deploy/front-service                 1         1         1            1           20d
deploy/jsr-confirm-service-msa       1         1         1            1           12d
deploy/productpage-v1                1         1         1            1           12d
deploy/ratings-v1                    1         1         1            1           12d
deploy/reviews-v1                    1         1         1            1           12d
deploy/reviews-v2                    1         1         1            1           12d
deploy/reviews-v3                    1         1         1            1           12d
deploy/trans-service-v1              1         1         1            1           20d
deploy/trans-service-v2              1         1         1            1           20d
deploy/trans-service-v3              1         1         1            1           11d
deploy/yoshio-service                2         2         2            2           11d
```

## 4.8 kubectl describe RESOURCES

Show details of a specific resource or group of resources 

```
$ kubectl describe svc account-service
Name:              account-service
Namespace:         order-system-production
Labels:            app=account-service
Annotations:       kubectl.kubernetes.io/last-applied-configuration={"apiVersion":"v1","kind":"Service","metadata":{"annotations":{},"labels":{"app":"account-service"},"name":"account-service","namespace":"order-system-...
Selector:          app=account-service
Type:              ClusterIP
IP:                10.0.206.8
Port:              http  80/TCP
TargetPort:        8080/TCP
Endpoints:         10.244.2.102:8080
Port:              https  443/TCP
TargetPort:        8080/TCP
Endpoints:         10.244.2.102:8080
Session Affinity:  None
Events:            <none>
```

```
$ kubectl describe po jsr-confirm-service-msa-1661152496-rgkvt
Name:           jsr-confirm-service-msa-1661152496-rgkvt
Namespace:      order-system-production
Node:           aks-nodepool1-19381275-1/10.240.0.4
Start Time:     Tue, 20 Feb 2018 16:31:04 +0900
Labels:         app=jsr-confirm-service
                pod-template-hash=1661152496
                version=v1
Annotations:    kubernetes.io/created-by={"kind":"SerializedReference","apiVersion":"v1","reference":{"kind":"ReplicaSet","namespace":"order-system-production","name":"jsr-confirm-service-msa-1661152496","uid":"9eecc...
Status:         Running
IP:             10.244.3.148
Controlled By:  ReplicaSet/jsr-confirm-service-msa-1661152496
Containers:
  jsr-confirm-service:
    Container ID:   docker://b8c82be6faac5709aab256906f0050254f0e75f5e5c4a713b745a2643820bd13
    Image:          yoshio.azurecr.io/tyoshio2002/jsr-confirm-service-msa:2.7
    Image ID:       docker-pullable://yoshio.azurecr.io/tyoshio2002/jsr-confirm-service-msa@sha256:44fddd3fe895323169e40d918a9b86536968f2e99acc8c6b2b96b2785049eb4e
    Port:           <none>
    State:          Running
      Started:      Tue, 20 Feb 2018 16:31:06 +0900
    Ready:          True
    Restart Count:  0
    Environment:
      SERVER_NAME:  <set to the key 'host' in secret 'mysql-secret-for-jsr'>      Optional: false
      USER_NAME:    <set to the key 'username' in secret 'mysql-secret-for-jsr'>  Optional: false
      PASSWORD:     <set to the key 'password' in secret 'mysql-secret-for-jsr'>  Optional: false
      DB_NAME:      <set to the key 'database' in secret 'mysql-secret-for-jsr'>  Optional: false
      PORT_NUMBER:  <set to the key 'port' in secret 'mysql-secret-for-jsr'>      Optional: false
      JDBC_URL:     <set to the key 'jdbcurl' in secret 'mysql-secret-for-jsr'>   Optional: false
    Mounts:
      /var/run/secrets/kubernetes.io/serviceaccount from default-token-9b7qt (ro)
Conditions:
  Type           Status
  Initialized    True 
  Ready          True 
  PodScheduled   True 
Volumes:
  default-token-9b7qt:
    Type:        Secret (a volume populated by a Secret)
    SecretName:  default-token-9b7qt
    Optional:    false
QoS Class:       BestEffort
Node-Selectors:  <none>
Tolerations:     node.alpha.kubernetes.io/notReady:NoExecute for 300s
                 node.alpha.kubernetes.io/unreachable:NoExecute for 300s
Events:          <none>
```

## 4.9 kubectl exec -it pod command

```
$ kubectl exec -it jsr-confirm-service-msa-1661152496-rgkvt env
PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/lib/jvm/java-1.8-openjdk/jre/bin:/usr/lib/jvm/java-1.8-openjdk/bin
HOSTNAME=jsr-confirm-service-msa-1661152496-rgkvt
DB_NAME=jsrlist
PORT_NUMBER=3306
JDBC_URL=jdbc:mysql://fb8a835a-****-****-****-501a91be112a.mysql.database.azure.com:3306/jsrlist?zeroDateTimeBehavior=convertToNull

SERVER_NAME=fb8a835a-****-****-****-501a91be112a.mysql.database.azure.com

USER_NAME=azureuser@fb8a835a-****-****-****-501a91be112a
REVIEWS_PORT_9080_TCP_PROTO=tcp
RATINGS_PORT_9080_TCP_PORT=9080
YOSHIO_SERVICE_SERVICE_PORT_HTTPS=443
```

```
$ kubectl exec -it jsr-confirm-service-msa-1661152496-rgkvt /bin/sh
/ # ls
app.war                etc                    lib                    mnt                    root                   srv                    usr
bin                    hazelcast-default.xml  linuxrc                payara-micro.jar       run                    sys                    var
dev                    home                   media                  proc                   sbin                   tmp
/ # 
```


## 4.10 kubectl logs pod

Print the logs for a container in a pod or specified resource.

```
$ kubectl logs account-service-2364258777-0rg8s
```

If there is multiple container in your pod, you can specify the individual container with "***-c CONTAINER_NAME***".

```
$ kubectl logs account-service-2364258777-0rg8s -c istio-proxy --tail=10 
[2018-02-14T14:23:52.906Z] "GET /app/account/rest/hello HTTP/1.1" 200 - 0 0 10 10 "-" "Jersey/2.25.1 (HttpUrlConnection 1.8.0_111-internal)" "3bcfc3de-7bc3-99a7-adc9-d0bf1a6712cb" "account-service.order-system-production.svc.cluster.local" "10.244.2.102:8080"
[2018-02-14T14:23:53.733Z] "GET /app/account/rest/hello HTTP/1.1" 200 - 0 0 41 40 "-" "Jersey/2.25.1 (HttpUrlConnection 1.8.0_111-internal)" "0125edd5-7ac3-98b5-b1b0-18a565c09509" "account-service.order-system-production.svc.cluster.local" "10.244.2.102:8080"
[2018-02-14T14:23:54.424Z] "GET /app/account/rest/hello HTTP/1.1" 200 - 0 0 25 24 "-" "Jersey/2.25.1 (HttpUrlConnection 1.8.0_111-internal)" "ae0500dd-7391-9363-a0e5-423351454536" "account-service.order-system-production.svc.cluster.local" "10.244.2.102:8080"
[2018-02-14T14:23:55.635Z] "GET /app/account/rest/hello HTTP/1.1" 200 - 0 0 22 21 "-" "Jersey/2.25.1 (HttpUrlConnection 1.8.0_111-internal)" "05a19e6f-939f-9460-a308-c543aab120ae" "account-service.order-system-production.svc.cluster.local" "10.244.2.102:8080"
[2018-02-14T14:23:56.237Z] "GET /app/account/rest/hello HTTP/1.1" 200 - 0 0 6 5 "-" "Jersey/2.25.1 (HttpUrlConnection 1.8.0_111-internal)" "ab6e56fc-6ca8-930b-b99f-38b4c7fbb355" "account-service.order-system-production.svc.cluster.local" "10.244.2.102:8080"
[2018-02-14T14:23:56.873Z] "GET /app/account/rest/hello HTTP/1.1" 200 - 0 0 7 7 "-" "Jersey/2.25.1 (HttpUrlConnection 1.8.0_111-internal)" "322b279b-24d8-9f40-b0a3-76d680329240" "account-service.order-system-production.svc.cluster.local" "10.244.2.102:8080"
[2018-02-14T14:23:57.476Z] "GET /app/account/rest/hello HTTP/1.1" 200 - 0 0 13 13 "-" "Jersey/2.25.1 (HttpUrlConnection 1.8.0_111-internal)" "f750ca01-0192-9d85-ab8b-bbc0954e402e" "account-service.order-system-production.svc.cluster.local" "10.244.2.102:8080"
[2018-02-14T14:23:58.128Z] "GET /app/account/rest/hello HTTP/1.1" 200 - 0 0 2 2 "-" "Jersey/2.25.1 (HttpUrlConnection 1.8.0_111-internal)" "1ac8e106-fef8-9284-883d-906b5b1306ed" "account-service.order-system-production.svc.cluster.local" "10.244.2.102:8080"
[2018-02-14T14:23:58.791Z] "GET /app/account/rest/hello HTTP/1.1" 200 - 0 0 5 4 "-" "Jersey/2.25.1 (HttpUrlConnection 1.8.0_111-internal)" "05318a37-6710-9a63-aa93-d31f609d6e97" "account-service.order-system-production.svc.cluster.local" "10.244.2.102:8080"
[2018-02-15T04:29:12.345Z] "GET /app/account/rest/hello HTTP/1.1" 200 - 0 0 11 11 "-" "Jersey/2.25.1 (HttpUrlConnection 1.8.0_111-internal)" "d7b9d14b-2cd9-9959-b794-df32266936fd" "account-service.order-system-production.svc.cluster.local" "10.244.2.102:8080"
```

If you would like to get the last ***N*** line in the logfile, you can specify "***--tail=N***" like follows.

```
$ kubectl logs account-service-2364258777-0rg8s --tail=10 
[2018-02-15T05:42:03.449+0000] [] [WARNING] [] [com.hazelcast.spi.impl.operationservice.impl.InvocationMonitor] [tid: _ThreadID=59 _ThreadName=hz._hzInstance_1_dev.InvocationMonitorThread] [timeMillis: 1518673323449] [levelValue: 900] [10.244.2.102]:5701 [dev] [3.8.5] MonitorInvocationsTask delayed 12847 ms

[2018-02-15T05:42:15.290+0000] [] [WARNING] [] [com.hazelcast.spi.impl.operationservice.impl.InvocationMonitor] [tid: _ThreadID=59 _ThreadName=hz._hzInstance_1_dev.InvocationMonitorThread] [timeMillis: 1518673335290] [levelValue: 900] [10.244.2.102]:5701 [dev] [3.8.5] MonitorInvocationsTask delayed 21372 ms

[2018-02-15T05:42:38.492+0000] [] [WARNING] [] [com.hazelcast.spi.impl.operationservice.impl.InvocationMonitor] [tid: _ThreadID=59 _ThreadName=hz._hzInstance_1_dev.InvocationMonitorThread] [timeMillis: 1518673358492] [levelValue: 900] [10.244.2.102]:5701 [dev] [3.8.5] BroadcastOperationControlTask delayed 57393 ms

[2018-02-15T05:43:08.546+0000] [] [WARNING] [] [com.hazelcast.spi.impl.operationservice.impl.InvocationMonitor] [tid: _ThreadID=59 _ThreadName=hz._hzInstance_1_dev.InvocationMonitorThread] [timeMillis: 1518673388546] [levelValue: 900] [10.244.2.102]:5701 [dev] [3.8.5] MonitorInvocationsTask delayed 52084 ms

[2018-02-15T05:43:08.872+0000] [] [WARNING] [] [com.hazelcast.spi.impl.operationservice.impl.InvocationMonitor] [tid: _ThreadID=59 _ThreadName=hz._hzInstance_1_dev.InvocationMonitorThread] [timeMillis: 1518673388872] [levelValue: 900] [10.244.2.102]:5701 [dev] [3.8.5] BroadcastOperationControlTask delayed 15434 ms
```

If you would like to get the log file for last ***N*** hours, you can specify "***--since=N time***" like follows.

```
$ kubectl logs account-service-2364258777-0rg8s account-service --since=1h
```


## 4.11 kubectl get configmap(cm)

```
$ kubectl get configmap
NAME                     DATA      AGE
account-service-config   3         31d
```

```
$ kubectl get cm account-service-config -o yaml
apiVersion: v1
data:
  ACCOUT_SERVICE_SERVER_URL: account-service.order-system-production.svc.cluster.local
  CUSTOMER_MANAGEMENT_SERVER_URL: customer-management-service.order-system-production.svc.cluster.local
  TRANSLATOR_SERVER_URL: trans-service.order-system-production.svc.cluster.local
kind: ConfigMap
metadata:
  annotations:
    kubectl.kubernetes.io/last-applied-configuration: |
      {"apiVersion":"v1","data":{"ACCOUT_SERVICE_SERVER_URL":"account-service.order-system-production.svc.cluster.local","CUSTOMER_MANAGEMENT_SERVER_URL":"customer-management-service.order-system-production.svc.cluster.local","TRANSLATOR_SERVER_URL":"trans-service.order-system-production.svc.cluster.local"},"kind":"ConfigMap","metadata":{"annotations":{},"name":"account-service-config","namespace":"order-system-production"}}
  creationTimestamp: 2018-01-25T18:29:09Z
  name: account-service-config
  namespace: order-system-production
  resourceVersion: "2176384"
  selfLink: /api/v1/namespaces/order-system-production/configmaps/account-service-config
  uid: a2692700-****-****-****-0a58ac1f1c07
```

## 4.12 kubectl get secrets

```
$ kubectl get secrets
NAME                   TYPE                                  DATA      AGE
azure-storage-secret   Opaque                                2         28d
default-token-9b7qt    kubernetes.io/service-account-token   3         31d
istio.default          istio.io/key-and-cert                 3         31d
service-bus-secret     Opaque                                2         21d
```

```
apiVersion: v1
kind: Secret
metadata:
  name: azure-storage-secret
type: Opaque
data:
  azurestorageaccountname: eW9***************Rha3M=
  azurestorageaccountkey: YTBmNkx******************************************************EtRejFXWm5NWGNtM1JqNnVsYVJqT3FSZGZzaENYTDhJMFhweUI4UHF0ZUE9PQ==
```

## 4.13 kubectl apply -f manifest(YAML)

In order to create the resoureces in k8s, "***create***" option was used in tha past. However now we can use the "***apply***" option which can modify the resources configuration without delete. Please use this command as default instead of the "create" now?

```
$ kubectl apply -f create-deploy-svc.yaml
deployment "jsr-confirm-service-msa" unchanged
service "jsr-confirm-service" unchanged
```

## 4.14 kubectl delete resources

```
$ kubectl delete -f create-deploy-svc.yaml
```

```
$ kubectl delete ns order-system-development
```

```
$ kubectl delete po account-service-2364258777-0rg8s 
pod "account-service-2364258777-0rg8s" deleted
```

If you are managing important system, following ***--grace-period*** option may be very useful. The default value was 30sec to kill the container. However in your system, you would like to wait more time before stopping, you can specify like follows. In this example, it waiting 60 sec.

```
$ kubectl delete po account-service-7c7cbf499c-b8xpp --grace-period=60
pod "account-service-7c7cbf499c-b8xpp" deleted
```



## 4.15 kubectl edit svc/jsr-confirm-service 

Edit a resource from the default editor. 

```
$ kubectl edit svc/jsr-confirm-service 
service "jsr-confirm-service" edited
```

Open the default editor as follows, and you can directly edit the configration and apply it like follows.

```
# Please edit the object below. Lines beginning with a '#' will be ignored,
# and an empty file will abort the edit. If an error occurs while saving this file will be
# reopened with the relevant failures.
#
apiVersion: v1
kind: Service
metadata:
  annotations:
    kubectl.kubernetes.io/last-applied-configuration: |
      {"apiVersion":"v1","kind":"Service","metadata":{"annotations":{},"labels":{"app":"jsr-confirm-service"},"name":"jsr-confirm-service","namespace":"order-system-production"},"spec":{"externalTrafficPolicy":"Cluster","ports":[{"name":"http","port":80,"targetPort":8080},{"name":"https","port":443,"targetPort":8080}],"selector":{"app":"jsr-confirm-service"},"sessionAffinity":"None","type":"LoadBalancer"}}
  creationTimestamp: 2018-02-14T03:53:31Z
  labels:
    app: jsr-confirm-service
  name: jsr-confirm-service
  namespace: order-system-production
  resourceVersion: "6680836"
  selfLink: /api/v1/namespaces/order-system-production/services/jsr-confirm-service
  uid: 9f4eaaf7-113a-11e8-8fa4-0a58ac1f264c
spec:
  clusterIP: 10.0.86.130
  ports:
  - name: http
    port: 80
    protocol: TCP
    targetPort: 8080
  - name: https
    port: 443
    protocol: TCP
    targetPort: 8080
  selector:
    app: jsr-confirm-service
  sessionAffinity: None
  type: ClusterIP
status:
  loadBalancer:
    ingress:
    - ip: 40.**.***.244
~                                                                                                                                                                                              
~                                                                                                                                                                                              
~                                                                                                                                                                                              
~                                                                                                                                                                                              
~                                                                                                                                                                                              
~                                                                                                                                                                                              
~                                                                                                                                                                                              
~                                                                                                                                                                                              
"/var/folders/wg/gpxl9__j053c0spb08pkcq0c0000gn/T/kubectl-edit-eyoh4.yaml" 37L, 1351C
```

## 4.16 kubectl get node -o wide

```
$ kubectl get no -o wide
NAME                       STATUS    ROLES     AGE       VERSION   EXTERNAL-IP   OS-IMAGE                      KERNEL-VERSION      CONTAINER-RUNTIME
aks-nodepool1-19381275-0   Ready     agent     1d        v1.8.7    <none>        Debian GNU/Linux 8 (jessie)   4.11.0-1016-azure   docker://1.12.6
aks-nodepool1-19381275-1   Ready     agent     1d        v1.8.7    <none>        Debian GNU/Linux 8 (jessie)   4.11.0-1016-azure   docker://1.12.6
aks-nodepool1-19381275-2   Ready     agent     1d        v1.8.7    <none>        Debian GNU/Linux 8 (jessie)   4.11.0-1016-azure   docker://1.12.6
aks-nodepool1-19381275-3   Ready     agent     1d        v1.8.7    <none>        Debian GNU/Linux 8 (jessie)   4.11.0-1016-azure   docker://1.12.6
aks-nodepool1-19381275-5   Ready     agent     1d        v1.8.7    <none>        Debian GNU/Linux 8 (jessie)   4.11.0-1016-azure   docker://1.12.6
```

## 4.17 kubectl port-forward pod LOCAL_PORT:DEST_PORT

Forward one or more local ports to a pod.

For example, if you would like to show the Grafana GUI interface, you can specify the following command.

```
$ kubectl port-forward grafana-3098480057-44s4s  -n istio-system  3000:3000
Forwarding from 127.0.0.1:3000 -> 3000
```

You can also combine the result of another command to get the pod name like follows.

```
$ kubectl -n istio-system port-forward $(kubectl -n istio-system get pod -l app=grafana -o jsonpath='{.items[0].metadata.name}') 3000:3000 &
```


## 4.18 kubectl scale deployment DEPLOYMENT --replicas=N

```
$ kubectl get deployment account-service
NAME              DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
account-service   1         1         1            1           20d
```

```
$ kubectl scale deployment account-service --replicas=3
deployment "account-service" scaled
```

```
$ kubectl get deployment account-service -w
NAME              DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
account-service   3         3         3            1           20d
account-service   3         3         3         2         20d
account-service   3         3         3         3         20d
```

## 4.19 kubectl autoscale deployment DEPLOYMENT --min=2 --max=5 --cpu-percent=80

```
$ kubectl get deployment account-service
NAME              DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
account-service   1         1         1            1           20d
```

```
$ kubectl autoscale deployment account-service --min=2 --max=5 --cpu-percent=80
deployment "account-service" autoscaled
```

```
$ kubectl get deployment account-service -w
NAME              DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
account-service   1         1         1            1           20d
account-service   2         1         1         1         20d
account-service   2         1         1         1         20d
account-service   2         1         1         1         20d
account-service   2         2         2         1         20d
account-service   2         2         2         2         20d
```


## 4.20 kubectl config --kubeconfig=config use-context akscluster

If you have multiple k8s cluster environment, you can select the manage.

```
$ kubectl config get-contexts
CURRENT   NAME                CLUSTER             AUTHINFO                                           NAMESPACE
          akscluster          akscluster          clusterUser_AzureContainerService-AKS_akscluster   
          centralakscluster   centralakscluster   clusterUser_MCACS-AKS-Central_centralakscluster    
*         esakscluster        esakscluster        clusterUser_MCACS-AKS_esakscluster                 order-system-production
          minikube            minikube            minikube                                           
```

```
$ kubectl config current-context
esakscluster
```

```
$ kubectl config --kubeconfig=config use-context akscluster
akscluster
```

## 4.21 kubectl config set-context esakscluster --namespace=order-system-production

```
$ kubectl config current-context
esakscluster
$ kubectl config set-context $(kubectl config current-context) --namespace=order-system-production
Context "esakscluster" modified.
```

## 4.22 kubectl top pods --all-namespaces

```
$ kubectl top pods --all-namespaces
NAMESPACE                 NAME                                          CPU(cores)   MEMORY(bytes)   
default                   example-java-java-6d989865b8-2lh44            0m           53Mi            
default                   example-java-java-6d989865b8-lkjvw            0m           68Mi            
default                   example-spring-boot-java-5b6bd5ff49-bv8q2     0m           126Mi           
default                   omsagent-5s96p                                1m           105Mi           
default                   omsagent-d2tpk                                8m           94Mi            
default                   omsagent-frq7q                                10m          91Mi            
default                   omsagent-n8t2h                                1m           85Mi            
default                   omsagent-tmdvx                                1m           88Mi            
istio-system              grafana-3098480057-bfn5x                      0m           10Mi            
istio-system              istio-ca-2330594145-l2cvh                     0m           14Mi            
istio-system              istio-ingress-4274189616-hdpxn                11m          20Mi            
istio-system              istio-mixer-2464598866-rk8cq                  46m          61Mi            
istio-system              istio-pilot-2201259095-6dndk                  93m          68Mi            
istio-system              jaeger-deployment-1058555976-g62w4            0m           6Mi             
istio-system              prometheus-168775884-qfkzw                    36m          413Mi           
kube-system               draftd-764c469ffc-vmzmb                       1m           731Mi           
kube-system               heapster-342135353-fhx2g                      0m           29Mi            
kube-system               kube-dns-v20-1654923623-2xq58                 4m           18Mi            
kube-system               kube-dns-v20-1654923623-5l556                 4m           20Mi            
kube-system               kube-proxy-cgcgx                              2m           20Mi            
kube-system               kube-proxy-dmq9x                              1m           24Mi            
kube-system               kube-proxy-gx9w7                              2m           21Mi            
kube-system               kube-proxy-qjfhv                              1m           26Mi            
kube-system               kube-proxy-trpvk                              2m           27Mi            
kube-system               kube-svc-redirect-l2fdd                       9m           2Mi             
kube-system               kube-svc-redirect-mkkp6                       8m           2Mi             
kube-system               kube-svc-redirect-p8v79                       9m           2Mi             
kube-system               kube-svc-redirect-rlv9s                       8m           2Mi             
kube-system               kube-svc-redirect-sddqg                       8m           6Mi             
kube-system               kubernetes-dashboard-684dc55d59-2mh66         0m           12Mi            
kube-system               tiller-deploy-352283156-sgr6z                 0m           17Mi            
kube-system               tunnelfront-68bf7d5b88-stkx4                  78m          12Mi            
order-system-production   account-service-55bc4d6f94-2pvqn              81m          379Mi           
order-system-production   account-service-55bc4d6f94-5nptz              71m          381Mi           
order-system-production   account-service-55bc4d6f94-dzrgs              81m          389Mi           
order-system-production   customer-management-service-872683917-46z6b   78m          372Mi           
order-system-production   details-v1-1476947904-8vmm4                   79m          33Mi            
order-system-production   front-service-267342376-84bsh                 74m          358Mi           
order-system-production   jsr-confirm-service-msa-1661152496-62hl7      1m           315Mi           
order-system-production   productpage-v1-1037638640-dk7ng               79m          60Mi            
order-system-production   ratings-v1-353054424-p6bls                    77m          33Mi            
order-system-production   reviews-v1-401049526-ncfg6                    81m          97Mi            
order-system-production   reviews-v2-4140793682-zlc9b                   72m          101Mi           
order-system-production   reviews-v3-3651831602-hjf6d                   71m          101Mi           
order-system-production   trans-service-v1-249069369-cptfh              80m          375Mi           
order-system-production   trans-service-v2-2894455495-wxdzr             75m          383Mi           
order-system-production   trans-service-v3-413623198-7n8nm              77m          366Mi           
weave                     weave-scope-agent-bw82t                       62m          72Mi            
weave                     weave-scope-agent-d5wkc                       55m          78Mi            
weave                     weave-scope-agent-p4vpr                       54m          75Mi            
weave                     weave-scope-agent-pcvdn                       74m          84Mi            
weave                     weave-scope-agent-wjlbr                       58m          72Mi            
weave                     weave-scope-app-1197481323-62brp              47m          124Mi   
```

---
[Previous Page](Kubernetes-Workshop3.md) / [Next Page](Kubernetes-Workshop5.md)
