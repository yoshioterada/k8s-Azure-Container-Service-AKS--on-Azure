[Previous Page](README.md) / [Next Page](Kubernetes-Workshop2.md)
---

***Note: This contents is Beta version!!***

---
* [Azure 無料アカウントのご取得はコチラから](https://aka.ms/jjug_mar2)  
***Azure の無料アカウントで料金は発生しますか？  
いいえ。無料で開始でき、最初の 30 日間に使用する ¥22,500 クレジットを取得します。サービスの利用を開始した後でも、アップグレードするまでは無料で利用できます。***  

---

# 1. Key Concept of k8s


![](https://c1.staticflickr.com/5/4767/38748623620_bcba40c4cf_c.jpg)

***The Master node*** is the cluster's control plane and is responsible for the management of Kubernetes cluster. 

The Master node contains:

* ***API Server***: It is used for controling the cluster. The API Server provide the Kubernetes RESTful API. In order to control the Kubernetes, we can use RESTful API application, kubectl command which use REST API and also Kubernetes dashboard as GUI.

* ***Scheduler***: It control the deployment of pods and also the scheduler select the nodes where the pod should run.

* ***etcd***: It provide back-end configuration storage for the Kubernetes cluster. The etcd is a simple key-value store. It will be used for storing configurations, security informations, job schedules, deployed pod and service states, namespaces, and replication information.

* ***Controller***: The controllers are background threads that handle the tasks in the cluster. Controllers include a Node controller, Replication controllers, Route Controller and Namespace controller.

* ***kubectl***: A command-line (CLI) tool that can manage all of the Kubernetes operations. In order to access to the Master node, it use the REST commands.


***The Node*** is used to run pods. we can create  multiple nodes. The node provides services that manage the networking between the containers, as well as assigning resources to containers and communicating with the master node. The nodes contain:

* ***Kubelet***: kublet is used for communicating between the node and the Master node. It runs the pod's containers using Docker, mounts the pod's required volumes, tests for container status, and reports pod and node status back to the Master node.

* ***Proxy***: Every node in a Kubernetes cluster runs a kube-proxy. kube-proxy is responsible for implementing a form of virtual IP for Services of type other than ExternalName. 

* ***Docker***: Docker is running on each nodes and it is used for running the pods. Docker handles downloading container images and starting containers.

* ***Pods***: Pods is set of containers. it can be managed as a single unit. The containers in a pod are co-located and share the resources such as storage, namespaces, mount directory, port number and IP addresses.

* ***Service***: An abstraction on top of the number of pods. A Kubernetes service is a set of pods that work together, for instance, as a tier in a multi-tier application. Services enable communication between pods.
And in order to understand the pods in the Service was used by Label selector.


---
[Previous Page](README.md) / [Next Page](Kubernetes-Workshop2.md)
