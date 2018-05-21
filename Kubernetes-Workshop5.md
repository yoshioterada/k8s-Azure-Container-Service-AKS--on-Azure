[Previous Page](Kubernetes-Workshop4.md) / [Next Page](Kubernetes-Workshop6.md)
---
***Note: This contents is Beta version!!***


# 5. RESTful operation

* [RESTful API for 1.8](https://v1-8.docs.kubernetes.io/docs/api-reference/v1.8/)  
* [RESTful API for 1.9](https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.9/)

## 5.1 Verify API Server

```
$ kubectl cluster-info
Kubernetes master is running at https://esaksclust-mcacs-aks-******-********.hcp.eastus.azmk8s.io:443
Heapster is running at https://esaksclust-mcacs-aks-******-********.hcp.eastus.azmk8s.io:443/api/v1/namespaces/kube-system/services/heapster/proxy
KubeDNS is running at https://esaksclust-mcacs-aks-******-********.hcp.eastus.azmk8s.io:443/api/v1/namespaces/kube-system/services/kube-dns:dns/proxy
kubernetes-dashboard is running at https://esaksclust-mcacs-aks-******-********.hcp.eastus.azmk8s.io:443/api/v1/namespaces/kube-system/services/kubernetes-dashboard/proxy
```

You can confirm the API server is running and connect to following URL.  
***https://esaksclust-mcacs-aks-******-********.hcp.eastus.azmk8s.io:443***

## 5.2 Get an Access Token to connect

```
$ kubectl describe secret $(kubectl get secrets | grep default | cut -f1 -d ' ') | grep -E '^token' | cut -f2 -d':' | tr -d '\t'

eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3N
lcnZpY2VhY2NvdW50Iiwia3ViZ**************************************
****************************************************************
****************************************************************
****************************************************************
****************************************************************
****************************************************************
****************************************************************
************************************************************-
****************************************************************
****************************************************************
****************************************************************
********************************************************-*******
****************************************************************
****************************************************************
****************************************************************
****************************************************************
****************************************************************
*************************
2UL2bYpEFSGA41uNghvCnXaKLVDG79WgXZvQl7g_b130DtAgjUglylQ
```

## 5.3 get pod in namespace.

[GET /api/v1/namespaces/{namespace}/pods/{name}](https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.9/#read-61)

```
$ curl -k -H "Content-Type: applicatio/json" \
-H "Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.e
yJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZ************
****************************************************************
****************************************************************
****************************************************************
****************************************************************
****************************************************************
****************************************************************
************************************************************-
****************************************************************
****************************************************************
****************************************************************
********************************************************-*******
****************************************************************
****************************************************************
****************************************************************
****************************************************************
****************************************************************
***************************************************
2UL2bYpEFSGA41uNghvCnXaKLVDG79WgXZvQl7g_b130DtAgjUglylQ" \
https://esaksclust-mcacs-aks-******-********.hcp.eastus.azmk8s.io \ 
/api/v1/namespaces/order-system-production(namespace)/pods/accou\
nt-service-55bc4d6f94-2pvqn

{
  "kind": "Pod",
  "apiVersion": "v1",
  "metadata": {
    "name": "account-service-55bc4d6f94-2pvqn",
    "generateName": "account-service-55bc4d6f94-",
    "namespace": "order-system-production",
    "selfLink": "/api/v1/namespaces/order-system-production/pods/account-service-55bc4d6f94-2pvqn",
    "uid": "a5a613ca-1b1c-11e8-a63d-0a58ac1f2e0f",
    "resourceVersion": "6752950",
    "creationTimestamp": "2018-02-26T17:44:08Z",
    "labels": {
      "app": "account-service",
      "pod-template-hash": "1167082950",
      "version": "v1"
    },
....... 
```

## 5.4 List all pods in namespace.

[GET /api/v1/namespaces/{namespace}/pods](https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.9/#list-62)

```
$ curl -k -H "Content-Type: applicatio/json" \
-H "Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.e
yJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZ************
****************************************************************
****************************************************************
****************************************************************
****************************************************************
****************************************************************
****************************************************************
************************************************************-
****************************************************************
****************************************************************
****************************************************************
********************************************************-*******
****************************************************************
****************************************************************
****************************************************************
****************************************************************
****************************************************************
***************************************************
2UL2bYpEFSGA41uNghvCnXaKLVDG79WgXZvQl7g_b130DtAgjUglylQ" \
https://esaksclust-mcacs-aks-******-********.hcp.eastus.azmk8s.io \ 
/api/v1/namespaces/order-system-production(namespace)/pods

The result was showed as JSON data.
```

## 5.5 Scale the number of pod

[PUT /apis/apps/v1beta2/namespaces/{namespace}/deployments/{deployment-name}/scale]()

```
$ curl -k -H "Content-Type: applicatio/json" \
-H "Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.e
yJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZ************
****************************************************************
****************************************************************
****************************************************************
****************************************************************
****************************************************************
****************************************************************
************************************************************-
****************************************************************
****************************************************************
****************************************************************
********************************************************-*******
****************************************************************
****************************************************************
****************************************************************
****************************************************************
****************************************************************
***************************************************
2UL2bYpEFSGA41uNghvCnXaKLVDG79WgXZvQl7g_b130DtAgjUglylQ" \
https://esaksclust-mcacs-aks-******-********.hcp.eastus.azmk8s.io \ 
/apis/apps/v1beta2/namespaces/order-system-production/deployments/ \
account-service/scale -d 
"{
    \"kind\": \"Scale\",
    \"apiVersion\": \"apps/v1beta2\",
    \"metadata\": {
        \"name\": \"account-service\",
        \"namespace\": \"order-system-production\",
        \"uid\": \"da604a1f-****-****-****-0a58ac1f2e0f\"
    },
    \"spec\": {
        \"replicas\": 3
    }
}"
```

## 5.6 RESTful API Reference documents

The above is the sample basic operation for RESTful API for k8s, if you would like to implement your own, please refere to the following reference document.

* [RESTful API for 1.8](https://v1-8.docs.kubernetes.io/docs/api-reference/v1.8/)  
* [RESTful API for 1.9](https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.9/)


---
[Previous Page](Kubernetes-Workshop4.md) / [Next Page](Kubernetes-Workshop6.md)
