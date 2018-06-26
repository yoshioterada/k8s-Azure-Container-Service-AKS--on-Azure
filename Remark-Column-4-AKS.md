# Remark Column for AKS

Following is some links for AKS related topic.

## Network Related

* [Network configuration in Azure Kubernetes Service (AKS)](https://github.com/MicrosoftDocs/azure-docs/blob/master/articles/aks/networking-overview.md)

* [Enforcing Network Policies using kube-router on AKS](https://www.techdiction.com/2018/06/02/enforcing-network-policies-using-kube-router-on-aks/)

* [Kubernetes Network Policy Recipes](https://github.com/ahmetb/kubernetes-network-policy-recipes)

* [Microsoft Azure Container Networking](https://github.com/Azure/azure-container-networking/tree/master/docs)

* [Brigade Kubernetes Gateway](https://github.com/Azure/brigade-k8s-gateway)


* [Custom VNET](https://github.com/Azure/acs-engine/blob/master/docs/kubernetes/features.md#feat-custom-vnet)


* [Grant AKS cluster Service Princpal access to VNET RG](https://github.com/Azure/AKS/blob/master/examples/vnet/00-README.md)

* [Sample Script to create VNET](https://github.com/serbrech/AKS-vnet-kubenet/blob/master/scripts/deploy-aks-cutom-vnet.sh#L53)

* [How to connect Azure API management to your Kubernetes cluster](https://fizzylogic.nl/2017/06/16/how-to-connect-azure-api-management-to-your-kubernetes-cluster/)

* [Azure-AKS-ApplicationGateway-WAF](https://github.com/kizotheitguy/Azure-AKS-ApplicationGateway-WAF)

## Disk Related
* [create a stateful set with Azure Disk](https://github.com/andyzhangx/demo/tree/master/linux/statefulset)

* [disk FlexVolume driver for Kubernetes (Preview)](https://github.com/Azure/kubernetes-volume-drivers/tree/master/flexvolume/dysk)

* [azure disk plugin known issues](https://github.com/andyzhangx/demo/blob/master/issues/azuredisk-issues.md)


## AAD & RBAC & Security Related

* [Integrate Azure Active Directory with AKS - Preview](https://docs.microsoft.com/en-us/azure/aks/aad-integration)

* [Microsoft Azure Container Service Engine - Kubernetes AAD integration Walkthrough](https://github.com/Azure/acs-engine/blob/master/docs/kubernetes/aad.md)


* [Adding SSL/TLS To Azure Container Instances](https://medium.com/@samkreter/adding-ssl-tls-to-azure-container-instances-1e608a8f321c)

* [Kubernetes 1.8 with RBAC enabled and Azure Active Directory integration](http://blog.jreypo.io/containers/microsoft/azure/cloud/cloud-native/kubernetes-18-with-rbac-enabled-and-azure-active-directory-integration/)

* [Using RBAC Authorization](https://kubernetes.io/docs/reference/access-authn-authz/rbac/#service-account-permissions)

* [Use portal to create an Azure Active Directory application and service principal that can access resources](https://docs.microsoft.com/en-us/azure/azure-resource-manager/resource-group-create-service-principal-portal)

* [Kubernetes 1.8 with RBAC enabled and Azure Active Directory integration](http://blog.jreypo.io/containers/microsoft/azure/cloud/cloud-native/kubernetes-18-with-rbac-enabled-and-azure-active-directory-integration/)

* [Integrate Azure Active Directory with AKS - Preview](https://docs.microsoft.com/en-us/azure/aks/aad-integration#create-rbac-binding)

* [Container Image Scan (Aqua Security)](https://azuremarketplace.microsoft.com/en-us/marketplace/apps/aqua-security.aquasec-csp-2_5)

* [Container Image Scan (Twistlock)](https://azuremarketplace.microsoft.com/en-us/marketplace/apps/twistlock.twistlock?tab=Overview)

## Management Cluster Related

* Maximum number of AKS node is 100 (Jul/2018).

* [Authenticate with Azure Container Registry from Azure Kubernetes Service](https://docs.microsoft.com/en-us/azure/container-registry/container-registry-auth-aks?toc=%2fazure%2faks%2ftoc.json)

* [Create a Kubernetes cluster with Azure Kubernetes Service and Terraform](https://docs.microsoft.com/en-us/azure/terraform/terraform-create-k8s-cluster-with-tf-and-aks)

* [Cluster Autoscaler on Azure](https://github.com/kubernetes/autoscaler/tree/master/cluster-autoscaler/cloudprovider/azure)

* [Exploiting path traversal in kubectl cp](https://hansmi.ch/articles/2018-04-openshift-s2i-security#poc-kubectl-cp)

* [Configure Out Of Resource Handling](https://kubernetes.io/docs/tasks/administer-cluster/out-of-resource/#node-oom-behavior)

* [Monitoring Azure Kubernetes Service (AKS) with Azure Monitor container health (preview)](https://azure.microsoft.com/en-us/blog/monitoring-azure-kubernetes-service-aks-with-azure-monitor-container-health-preview/)

* [Container Monitoring solution in Log Analytics](https://docs.microsoft.com/en-us/azure/log-analytics/log-analytics-containers)

* [Configuring kubelet Garbage Collection](https://kubernetes.io/docs/concepts/cluster-administration/kubelet-garbage-collection/)

* [Configure Out Of Resource Handling](https://kubernetes.io/docs/tasks/administer-cluster/out-of-resource/)

* [SSH into Azure Kubernetes Service (AKS) cluster nodes](https://docs.microsoft.com/en-us/azure/aks/aks-ssh)

* [Troubleshooting  
VMExtensionProvisioningError or   VMExtensionProvisioningTimeout](https://github.com/Azure/acs-engine/blob/master/docs/kubernetes/troubleshooting.md#vmextensionprovisioningerror-or-vmextensionprovisioningtimeout)

* [Service principals with Azure Kubernetes Service (AKS)](https://docs.microsoft.com/en-us/azure/aks/kubernetes-service-principal)

* [Use Azure Kubernetes Service with Kafka on HDInsight](https://docs.microsoft.com/en-us/azure/hdinsight/kafka/apache-kafka-azure-container-services)

## Development and CI & CD

* [Draft: Streamlined Kubernetes Development](https://github.com/Azure/draft)

* [Draft Homebrew Repository](https://github.com/Azure/homebrew-draft)

* [Brigade: Event-based Scripting for Kubernetes](https://github.com/Azure/brigade)

* [Azure Dev Spaces](https://docs.microsoft.com/en-us/azure/dev-spaces/azure-dev-spaces?utm_campaign=Azure%20Dev%20Spaces%20private%20preview&utm_source=hs_email&utm_medium=email&utm_content=62984601&_hsenc=p2ANqtz-981kgiuI_cgxhnduoWQnj7LZuqTGszlOWN9UY11hyYJYbpgGaDmCaiRmqm6wT-HN6hKLhn7yquDVvDmGWZkrCz3Fo4bg&_hsmi=62984601)


* [Preview of Visual Studio Kubernetes Tools](https://blogs.msdn.microsoft.com/visualstudio/2018/06/08/preview-of-visual-studio-kubernetes-tools/)

* [Tutorial: Deploy your ASP.NET Core App to Azure Kubernetes Service (AKS) with the Azure DevOps Project](https://docs.microsoft.com/en-us/vsts/pipelines/actions/azure-devops-project-aks?view=vsts)

* [Continuous deployment with Jenkins and Azure Kubernetes Service](https://docs.microsoft.com/en-us/azure/aks/jenkins-continuous-deployment?toc=%2Fen-us%2Fazure%2Fjenkins%2Ftoc.json&bc=%2Fen-us%2Fazure%2Fbread%2Ftoc.json)


## etc

* [Support policy for containers](https://support.microsoft.com/en-us/help/4035670/support-policy-for-containers)

* [ACS Engine](https://github.com/Azure/acs-engine/releases/)

* [Blackbelt AKS Hackfest](https://github.com/Azure/blackbelt-aks-hackfest/tree/master/labs/day1-labs)


* [Docker JSON File logging driver](https://docs.docker.com/config/containers/logging/json-file/)

* [Virtual Kubelet](https://github.com/virtual-kubelet/virtual-kubelet)

* [SmartHotel360 AKS DevSpaces Demo](https://github.com/Microsoft/SmartHotel360-AKS-DevSpaces-Demo)


## Azure Container Instance

* [ACI Event Driven Worker Queue](https://github.com/Azure-Samples/aci-event-driven-worker-queue/blob/master/README.md)
