# Azure Kubernetes Services WorkShop


***Note: This contents is Beta version!!***


## [1. Key Concept of k8s](Kubernetes-Workshop1.md)  

  
## [2. How to install AKS on Azure](Kubernetes-Workshop2.md)  
  
2.1 Install Azure cli on Mac  
2.2 Login Azure by cli  
2.3 Provider Register  
2.4 Create Resource Gruop for AKS  
2.5 Create Azure Container Service (AKS)  
2.6 Install kubectl command  
2.7 Get credential information to access AKS  
2.8 Confirm the kubectl command is running or not  
2.9 Scale the node for AKS node (VM) not pods  
2.10 Version up the k8s in AKS  
2.11 az aks command reference  
  
## [3. Basic operation to create and publish the Service](Kubernetes-Workshop3.md)
3.0 Prepare Dockerfile (Multi-Stage Build)  
3.1 Create Deployment manifest(YAML) file  
3.1.1 Rolling Upgrade  
3.1.2 Label is very important  
3.1.3 Liveness & ReadinessProbe Probe  
3.1.4 Request Limitation  
3.1.5 Init Container  
3.1.6 PostStart Hook of POD  
3.1.7 PreStop Hook of POD   
3.2 Create Service manifest(YAML) file  
3.3 Create All-in-one manifest(YAML) file (create-deployment-svc.yaml)  
3.4 Build and Publish the services  
3.5 Confirm the deployment and services  
3.6 How to access the Application (port-forward)  
3.7 How to write YAML file?  

## [4. Most userful kubectl command](Kubernetes-Workshop4.md)  
4.1 About kubectl command  
4.2 Display one or more resources  
4.3 kubectl get namespaces(ns)  
4.4 kubectl get pods(po)  
4.5 kubectl get services(svc)  
4.6 kubectl get deployments(deploy)  
4.7 Show Multiple Resources  
4.8 kubectl describe RESOURCES  
4.9 kubectl exec -it pod command  
4.10 kubectl logs pod  
4.11 kubectl get configmap(cm)  
4.12 kubectl get secrets  
4.13 kubectl apply -f manifest(YAML)  
4.14 kubectl delete resources  
4.15 kubectl edit svc/jsr-confirm-service  
4.16 kubectl get node -o wide  
4.17 kubectl port-forward pod LOCALPORT:DESTPORT  
4.18 kubectl scale deployment DEPLOYMENT --replicas=N  
4.19 kubectl autoscale deployment  DEPLOYMENT --min=2 --max=5 --cpu-percent=80  
4.20 kubectl config --kubeconfig=config use-context akscluster  
4.21 kubectl config set-context esakscluster --namespace=order-system-production  
4.22 kubectl top pods --all-namespaces  

## [5. RESTful operation](Kubernetes-Workshop5.md)    
5.1 Verify API Server  
5.2 Get an Access Token to connect  
5.3 Get pod in namespace.  
5.4 List all pods in namespace.  
5.5 Scale the number of pod  
5.6 RESTful API Reference documents

## [6. Istio (Service Mesh)](Kubernetes-Workshop6.md)

6.1 About Istio  
6.2 Install Istio  
6.2.1 Download & Intall  
6.2.2 Install Add-On package
6.3 How to use the Istio  
6.4 Traffic Management  
6.4.1 Weight Routing to 100% specific  service   
6.4.2 Restrict access for special Header  
6.4.3 Canary Release  
6.4.4 Request Timeout   
6.4.5 CircuitBreaker   


## [7. Take care of using the k8s](Kubernetes-Workshop7.md)    
7.1 Please don't use the latest tag  
7.2 Please consider to create 1 svc on 1 container?  
7.3 Please confirm which version are you using.  
7.4 Please don't treat pod directly  
7.5 Please consider to create more small size image?  
7.6 Do you trust the images on DockerHub?  
7.7 LoadBalancer is high cost to expose   the service  
7.8 Please don't access to Node directly by ssh  
7.9 Rollout: Specify "--record" to apply command  
7.10 Use configmap or secret for configure the external service

## [8. Useful Tool for k8s managing and monitoring](Kubernetes-Workshop8.md)    
8.1 Development & Deploy  
8.1.1 Helm (package management)  
8.1.2 Draft (Dev & Deploy)  
8.1.3 Spinnaker Release Pipeline  
8.2 Monitoring on Server  
8.2.1 Prometheus  
8.2.2 Grafana  
8.2.3 fluentd with ELK Stack  
8.2.4 Searchlight  
8.2.5 Weave  
8.3 Monotoring on Desktop  
8.3.1 Kubernetic  
8.3.2 Kubetail  
8.3.3 kubewatch (Notification to Slack)  
8.4 Management  
8.4.1 Heptio Ark (Backup & Restore)  
8.4.2 Chaos Monkey for k8s  
8.4.3 Open Policy Agent  
8.4.4 Ksonnet (Definition instead of YAML)  
8.4.5 Open Service Broker & Service Catalog  
8.4.6 Clair (Static analysis of vulnerabilities)  