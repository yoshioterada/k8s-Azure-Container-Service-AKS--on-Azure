[Previous Page](Kubernetes-Workshop1.md) / [Next Page](Kubernetes-Workshop3.md)
---


***Note: This contents is Beta version!!***


# 2. How to install AKS on Azure

## 2.1 Install Azure cli on Mac

Azure CLI version 2.0.64 or later (I used version 2.0.64 in this time.)

```
$ brew update
Updated 5 taps (azure/draft, cloudfoundry/tap, homebrew/core, pivotal/tap, caskroom/cask).
==> New Formulae
amber                   console_bridge          futhark                 jdupes                  mafft                   openimageio             siril                   urdfdom
augustus                container-diff          go@1.9                  krakend                 mariadb-connector-odbc  plank                   skafos                  urdfdom_headers
ballerina               coreos-ct               gocryptfs               kube-ps1                mdcat                   posh                    srt                     vis
bareos-client           dartsim                 gpredict                libbitcoin-consensus    mmseqs2                 primer3                 stellar-core            webtorrent-cli
boost-python3           dashing                 grv                     libccd                  mpir                    qtkeychain              terraforming            zig
calicoctl               dynare                  hlint                   libdill                 nyx                     restview                tmux-xpanes
chrome-export           fcl                     hmmer                   libjwt                  ocrmypdf                shelltestrunner         tomcat@8
clblast                 fruit                   howdoi                  libsbol                 odpi                    shogun                  unravel
==> Updated Formulae
azure-cli ✔                     cpprestsdk                      git-standup                     libebur128                      overmind                        sphinx-doc
azure/draft/draft ✔             cracklib                        git-town                        libgee                          owfs                            spigot
cloudfoundry/tap/cf-cli ✔       cromwell                        gitbucket                       libgosu                         p7zip                           spotbugs
git ✔                           crowdin                         github-keygen                   libgtop                         packer                          sqldiff
kubernetes-cli ✔                cryfs                           gitlab-runner                   libhttpseverywhere              packetbeat                      sqlite-analyzer
kubernetes-helm ✔               cryptopp                        gitless                         libical                         paket                           sqlmap
mercurial ✔                     crystal-icr                     gjs                             liblwgeom                       pandoc                          sratoolkit
mysql ✔                         crystal-lang                    glade                           libmaxminddb                    pandoc-citeproc                 sslh
pivotal/tap/springboot ✔        curl                            glbinding                       libmicrohttpd                   pandoc-crossref                 sslsplit
python
......
$ 
```

```
$ brew install azure-cli
==> Installing dependencies for azure-cli: sqlite
==> Installing azure-cli dependency: sqlite
==> Downloading https://homebrew.bintray.com/bottles/sqlite-3.22.0.high_sierra.bottle.tar.gz
######################################################################## 100.0%
==> Pouring sqlite-3.22.0.high_sierra.bottle.tar.gz
==> Caveats
This formula is keg-only, which means it was not symlinked into /usr/local,
because macOS provides an older sqlite3.

If you need to have this software first in your PATH run:
  echo 'export PATH="/usr/local/opt/sqlite/bin:$PATH"' >> ~/.bash_profile

For compilers to find this software you may need to set:
    LDFLAGS:  -L/usr/local/opt/sqlite/lib
    CPPFLAGS: -I/usr/local/opt/sqlite/include
For pkg-config to find this software you may need to set:
    PKG_CONFIG_PATH: /usr/local/opt/sqlite/lib/pkgconfig

==> Summary
 /usr/local/Cellar/sqlite/3.22.0: 11 files, 3MB
==> Installing azure-cli
==> Downloading https://homebrew.bintray.com/bottles/azure-cli-2.0.27.high_sierra.bottle.tar.gz
######################################################################## 100.0%
==> Pouring azure-cli-2.0.27.high_sierra.bottle.tar.gz
==> Caveats
Bash completion has been installed to:
  /usr/local/etc/bash_completion.d
==> Summary
 /usr/local/Cellar/azure-cli/2.0.27: 8,607 files, 47.9MB
```

```
$ az --version
azure-cli (2.0.27)
```


## 2.2 Login Azure by cli

```
$ az login
To sign in, use a web browser to open the page https://aka.ms/devicelogin and enter the code HDBS8K9FT to authenticate.
[
  {
    "cloudName": "AzureCloud",
    "id": "f77aafe8-****-****-****-d0c37687ef70",
    "isDefault": true,
    "name": "Microsoft Azure \u793e\u5185\u5f93\u91cf\u8ab2\u91d1\u30d7\u30e9\u30f3",
    "state": "Enabled",
    "tenantId": "72f988bf-****-****-****-2d7cd011db47",
    "user": {
      "name": "yoterada@microsoft.com",
      "type": "user"
    }
  },
  {
    "cloudName": "AzureCloud",
    "id": "db56efb3-****-****-****-7841585fe607",
    "isDefault": false,
    "name": "Platform \u793e\u5185\u5f93\u91cf\u8ab2\u91d1\u30d7\u30e9\u30f3",
    "state": "Enabled",
    "tenantId": "72f988bf-****-****-****-2d7cd011db47",
    "user": {
      "name": "yoterada@microsoft.com",
      "type": "user"
    }
  }
]
```

## 2.3 Create Resoruce Group for AKS

```
$ az group create --name MC-YOSHIO-AKS-1137 --location japaneast
{
  "id": "/subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourceGroups/MC-YOSHIO-AKS-1137",
  "location": "japaneast",
  "managedBy": null,
  "name": "MC-YOSHIO-AKS-1137",
  "properties": {
    "provisioningState": "Succeeded"
  },
  "tags": null,
  "type": null
}
```

## 2.4 Create VNet for Virtual Node Preparation

```
$ az network vnet create --resource-group  MC-YOSHIO-AKS-1137 --name myVnet --address-prefixes 10.0.0.0/8 --subnet-name myAKSSubnet --subnet-prefix 10.240.0.0/16
{
  "newVNet": {
    "addressSpace": {
      "addressPrefixes": [
        "10.0.0.0/8"
      ]
    },
    "ddosProtectionPlan": null,
    "dhcpOptions": {
      "dnsServers": []
    },
    "enableDdosProtection": false,
    "enableVmProtection": false,
    "etag": "W/\"8ef746b5-****-****-****-142ee6b8f51e\"",
    "id": "/subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourceGroups/MC-YOSHIO-AKS-1137/providers/Microsoft.Network/virtualNetworks/myVnet",
    "location": "japaneast",
    "name": "myVnet",
    "provisioningState": "Succeeded",
    "resourceGroup": "MC-YOSHIO-AKS-1137",
    "resourceGuid": "5870e00a-****-****-****-9264e11a6240",
    "subnets": [
      {
        "addressPrefix": "10.240.0.0/16",
        "addressPrefixes": null,
        "delegations": [],
        "etag": "W/\"8ef746b5-****-****-****-142ee6b8f51e\"",
        "id": "/subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourceGroups/MC-YOSHIO-AKS-1137/providers/Microsoft.Network/virtualNetworks/myVnet/subnets/myAKSSubnet",
        "interfaceEndpoints": null,
        "ipConfigurationProfiles": null,
        "ipConfigurations": null,
        "name": "myAKSSubnet",
        "networkSecurityGroup": null,
        "provisioningState": "Succeeded",
        "purpose": null,
        "resourceGroup": "MC-YOSHIO-AKS-1137",
        "resourceNavigationLinks": null,
        "routeTable": null,
        "serviceAssociationLinks": null,
        "serviceEndpointPolicies": null,
        "serviceEndpoints": null,
        "type": "Microsoft.Network/virtualNetworks/subnets"
      }
    ],
    "tags": {},
    "type": "Microsoft.Network/virtualNetworks",
    "virtualNetworkPeerings": []
  }
}
```

## 2.5 Create SubNet for Virtual Node Preparation

```
$ az network vnet subnet create --resource-group MC-YOSHIO-AKS-1137 --vnet-name myVnet --name myVirtualNodeSubnet --address-prefixes 10.241.0.0/16
{
  "addressPrefix": "10.241.0.0/16",
  "addressPrefixes": null,
  "delegations": [],
  "etag": "W/\"1a1521e5-****-****-****-3f0dbe1d4c29\"",
  "id": "/subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourceGroups/MC-YOSHIO-AKS-1137/providers/Microsoft.Network/virtualNetworks/myVnet/subnets/myVirtualNodeSubnet",
  "interfaceEndpoints": null,
  "ipConfigurationProfiles": null,
  "ipConfigurations": null,
  "name": "myVirtualNodeSubnet",
  "networkSecurityGroup": null,
  "provisioningState": "Succeeded",
  "purpose": null,
  "resourceGroup": "MC-YOSHIO-AKS-1137",
  "resourceNavigationLinks": null,
  "routeTable": null,
  "serviceAssociationLinks": null,
  "serviceEndpointPolicies": null,
  "serviceEndpoints": null,
  "type": "Microsoft.Network/virtualNetworks/subnets"
}
```

## 2.6 Create Service Principal and ROle Assignment for AKS Creation

### 2.6.1 Create Service Principal

```
$ az ad sp create-for-rbac --skip-assignment
{
  "appId": "7f911902-****-****-****-b731d84a8cd6",
  "displayName": "azure-cli-2019-07-16-13-40-35",
  "name": "http://azure-cli-2019-07-16-13-40-35",
  "password": "94f0d75f-****-****-****-a01e73008d93",
  "tenant": "72f988bf-****-****-****-2d7cd011db47"
}
```

### 2.6.2 Confirm of the VNet ID

```
$ az network vnet show --resource-group MC-YOSHIO-AKS-1137 --name myVnet --query id -o tsv
/subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourceGroups/MC-YOSHIO-AKS-1137/providers/Microsoft.Network/virtualNetworks/myVnet
```

### 2.6.3 Assign the Role 

```
$ az role assignment create --assignee 7f911902-****-****-****-b731d84a8cd6[=appId] --scope /subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourceGroups/MC-YOSHIO-AKS-1137/providers/Microsoft.Network/virtualNetworks/myVnet[=vnetId] --role Contributor
{
  "canDelegate": null,
  "id": "/subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourceGroups/MC-YOSHIO-AKS-1137/providers/Microsoft.Network/virtualNetworks/myVnet/providers/Microsoft.Authorization/roleAssignments/c2d8ca17-****-****-****-31d42d56b8eb",
  "name": "c2d8ca17-****-****-****-31d42d56b8eb",
  "principalId": "9d62cb00-****-****-****-2638bc76a6f2",
  "resourceGroup": "MC-YOSHIO-AKS-1137",
  "roleDefinitionId": "/subscriptions/f77aafe8-****-****-****-d0c37687ef70/providers/Microsoft.Authorization/roleDefinitions/b24988ac-****-****-****-20f7382dd24c",
  "scope": "/subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourceGroups/MC-YOSHIO-AKS-1137/providers/Microsoft.Network/virtualNetworks/myVnet",
  "type": "Microsoft.Authorization/roleAssignments"
}

```


## 2.7 Create Azure Container Service (AKS)

```
$ az aks create \
    --resource-group MC-YOSHIO-AKS-1137 \
    --name yoshioAKSCluster1137 \
    --kubernetes-version 1.13.7 \
    --dns-name-prefix yoshio3-aks-1137 \
    --node-vm-size Standard_DS2_v2 \
    --location japaneast \
    --nodepool-name lindefvmpl \
    --node-count 5 \
    --max-pods 50 \
    --enable-addons monitoring,http_application_routing \
    --network-plugin azure \
    --service-cidr 10.0.0.0/16 \
    --dns-service-ip 10.0.0.10 \
    --docker-bridge-address 172.17.0.1/16 \
    --vnet-subnet-id "/subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourceGroups/MC-YOSHIO-AKS-1137/providers/Microsoft.Network/virtualNetworks/myVnet/subnets/myVirtualNodeSubnet" \
    --service-principal 7f911902-****-****-****-b731d84a8cd6 \
    --client-secret 94f0d75f-****-****-****-a01e73008d93


The behavior of this command has been altered by the following extension: aks-preview
{
  "aadProfile": null,
  "addonProfiles": {
    "httpApplicationRouting": {
      "config": {
        "HTTPApplicationRoutingZoneName": "*****************.japaneast.aksapp.io"
      },
      "enabled": true
    },
    "omsagent": {
      "config": {
        "logAnalyticsWorkspaceResourceID": "/subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourcegroups/defaultresourcegroup-ejp/providers/microsoft.operationalinsights/workspaces/defaultworkspace-f77aafe8-****-****-****-d0c37687ef70-ejp"
      },
      "enabled": true
    }
  },
  "agentPoolProfiles": [
    {
      "availabilityZones": null,
      "count": 5,
      "enableAutoScaling": null,
      "maxCount": null,
      "maxPods": 50,
      "minCount": null,
      "name": "lindefvmpl",
      "orchestratorVersion": "1.13.7",
      "osDiskSizeGb": 100,
      "osType": "Linux",
      "provisioningState": "Succeeded",
      "type": "AvailabilitySet",
      "vmSize": "Standard_DS2_v2",
      "vnetSubnetId": "/subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourceGroups/MC-YOSHIO-AKS-1137/providers/Microsoft.Network/virtualNetworks/myVnet/subnets/myVirtualNodeSubnet"
    }
  ],
  "apiServerAuthorizedIpRanges": null,
  "dnsPrefix": "yoshio3-aks-1137",
  "enablePodSecurityPolicy": false,
  "enableRbac": true,
  "fqdn": "yoshio3-aks-1137-**********.hcp.japaneast.azmk8s.io",
  "id": "/subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourcegroups/MC-YOSHIO-AKS-1137/providers/Microsoft.ContainerService/managedClusters/yoshioAKSCluster1137",
  "kubernetesVersion": "1.13.7",
  "linuxProfile": {
    "adminUsername": "azureuser",
    "ssh": {
      "publicKeys": [
        {
          "keyData": "ssh-rsa **************************"
        }
      ]
    }
  },
  "location": "japaneast",
  "maxAgentPools": 1,
  "name": "yoshioAKSCluster1137",
  "networkProfile": {
    "dnsServiceIp": "10.0.0.10",
    "dockerBridgeCidr": "172.17.0.1/16",
    "loadBalancerSku": "basic",
    "networkPlugin": "azure",
    "networkPolicy": null,
    "podCidr": null,
    "serviceCidr": "10.0.0.0/16"
  },
  "nodeResourceGroup": "MC_MC-YOSHIO-AKS-1137_yoshioAKSCluster1137_japaneast",
  "provisioningState": "Succeeded",
  "resourceGroup": "MC-YOSHIO-AKS-1137",
  "servicePrincipalProfile": {
    "clientId": "7f911902-****-****-****-b731d84a8cd6",
    "secret": null
  },
  "tags": null,
  "type": "Microsoft.ContainerService/ManagedClusters",
  "windowsProfile": null
}
$ 

```

## 2.8 Install kubectl command

```
$ az aks install-cli
Downloading client to /usr/local/bin/kubectl from https://storage.googleapis.com/kubernetes-release/release/v1.8.5/bin/darwin/amd64/kubectl
```

## 2.9 Get credential information to access AKS

```
$ az aks get-credentials --resource-group MCACS-AKS  --name esakscluster
Merged "akscluster" as current context in /Users/foo/.kube/config
```

```
$ ls ~/.kube
cache		config		http-cache
```


## 2.10 Confirm the kubectl command is running or not

```
$ kubectl cluster-info
Kubernetes master is running at https://esaksclust-mcacs-aks-******-********.hcp.eastus.azmk8s.io:443
Heapster is running at https://esaksclust-mcacs-aks-******-********.hcp.eastus.azmk8s.io:443/api/v1/namespaces/kube-system/services/heapster/proxy
KubeDNS is running at https://esaksclust-mcacs-aks-******-********.hcp.eastus.azmk8s.io:443/api/v1/namespaces/kube-system/services/kube-dns:dns/proxy
kubernetes-dashboard is running at https://esaksclust-mcacs-aks-******-********.hcp.eastus.azmk8s.io:443/api/v1/namespaces/kube-system/services/kubernetes-dashboard/proxy

To further debug and diagnose cluster problems, use 'kubectl cluster-info dump'.
```

```
$ kubectl version
Client Version: version.Info{Major:"1", Minor:"9", GitVersion:"v1.9.1", GitCommit:"3a1c9449a956b6026f075fa3134ff92f7d55f812", GitTreeState:"clean", BuildDate:"2018-01-04T11:52:23Z", GoVersion:"go1.9.2", Compiler:"gc", Platform:"darwin/amd64"}
Server Version: version.Info{Major:"1", Minor:"7", GitVersion:"v1.7.7", GitCommit:"8e1552342355496b62754e61ad5f802a0f3f1fa7", GitTreeState:"clean", BuildDate:"2017-09-28T23:56:03Z", GoVersion:"go1.8.3", Compiler:"gc", Platform:"linux/amd64"}
```

```
$ kubectl get nodes
NAME                       STATUS    ROLES     AGE       VERSION
aks-nodepool1-19381275-0   Ready     agent     45d       v1.7.7
aks-nodepool1-19381275-1   Ready     agent     45d       v1.7.7
aks-nodepool1-19381275-2   Ready     agent     45d       v1.7.7
aks-nodepool1-19381275-3   Ready     agent     45d       v1.7.7
aks-nodepool1-19381275-4   Ready     agent     45d       v1.7.7
```



## 2.11 Scale the node for AKS node (VM) not pods

```
$ az aks scale --name esakscluster --resource-group MCACS-AKS --node-count 7
```

## 2.12 Version up the k8s in AKS

***Before execute following command, could you confirm which version of az command you are using? Because old version of az command didn't include the following "get-upgrades" argument.
I userd 2.0.27 version of az command.***

Confirm the available upgrade version in your environment. For following example, you can upgrade to 1.8.7 as latest.


```
$ az aks get-upgrades --name esakscluster --resource-group MCACS-AKS
{
  "additionalProperties": {},
  "agentPoolProfiles": [
    {
      "additionalProperties": {},
      "kubernetesVersion": "1.7.7",
      "name": null,
      "osType": "Linux",
      "upgrades": [
        "1.8.7",
        "1.7.9",
        "1.8.6",
        "1.7.12",
        "1.8.1",
        "1.8.2"
      ]
    }
  ],
  "controlPlaneProfile": {
    "additionalProperties": {},
    "kubernetesVersion": "1.7.7",
    "name": null,
    "osType": "Linux",
    "upgrades": [
      "1.8.7",
      "1.7.9",
      "1.8.6",
      "1.7.12",
      "1.8.1",
      "1.8.2"
    ]
  },
  "id": "/subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourcegroups/MCACS-AKS/providers/Microsoft.ContainerService/managedClusters/esakscluster/upgradeprofiles/default",
  "name": "default",
  "resourceGroup": "MCACS-AKS",
  "type": "Microsoft.ContainerService/managedClusters/upgradeprofiles"
}
```

In order to update the version of k8s, you can execute following command. Please note, it will take a long time over 40 min.   

***Note 1 :   
Please add the  "--debug" option which output the debug messages to the console. If no options, we couldn't uderstand the progress of the upgrade.***

***Note 2 :   (THIS IS a DANGEROUS OPERATION)***  
***You may face some problem during the upgrade command like failed.  If you saw the failed message, please re-execute the same command? I hope it recover the status.***  
   
***The worst case,   
All of the service  in AKS(k8s) may not be able to access from outside. In order to avoid all services stop,  you should create the stand by environment especially for production environment before execute the command.***  
I faced the same issue as [GitHub : Error scaling cluster: nodes not added to cluster and lost connectivity to the cluster #77](https://github.com/Azure/AKS/issues/14).


```
$ az aks upgrade --name esakscluster --resource-group MCACS-AKS  \
  --kubernetes-version 1.8.7 --yes --debug
 - Running ..

..... take a long time (over 40 min) 
```

***Note : During the upgrade, you can access to the existing service.***


Confirm the status up upgrading.

```
$ kubectl get node -w
NAME                       STATUS    ROLES     AGE       VERSION
aks-nodepool1-19381275-0   Ready     agent     14m       v1.8.7
aks-nodepool1-19381275-1   Ready     agent     6m        v1.8.7
aks-nodepool1-19381275-3   Ready     agent     45d       v1.7.7
aks-nodepool1-19381275-4   Ready     agent     45d       v1.7.7
aks-nodepool1-19381275-5   Ready     agent     22m       v1.8.7
aks-nodepool1-19381275-3   Ready     agent     45d       v1.7.7
aks-nodepool1-19381275-1   Ready     agent     6m        v1.8.7
...
```

After finished the upgrade, you can see the folloiwng output.

```
$ az aks upgrade --name esakscluster --resource-group MCACS-AKS  --kubernetes-version 1.8.7 --yes
{
  "additionalProperties": {},
  "agentPoolProfiles": [
    {
      "additionalProperties": {},
      "count": 5,
      "dnsPrefix": null,
      "fqdn": null,
      "name": "nodepool1",
      "osDiskSizeGb": null,
      "osType": "Linux",
      "ports": null,
      "storageProfile": "ManagedDisks",
      "vmSize": "Standard_D1_v2",
      "vnetSubnetId": null
    }
  ],
  "dnsPrefix": "esaksclust-MCACS-AKS-f77aaf",
  "fqdn": "esaksclust-mcacs-aks-******-********.hcp.eastus.azmk8s.io",
  "id": "/subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourcegroups/MCACS-AKS/providers/Microsoft.ContainerService/managedClusters/esakscluster",
  "kubernetesVersion": "1.8.7",
  "linuxProfile": {
    "additionalProperties": {},
    "adminUsername": "azureuser",
    "ssh": {
      "additionalProperties": {},
      "publicKeys": [
        {
          "additionalProperties": {},
          "keyData": "ssh-rsa ***********************************
          ***************************+************************/
         **********************+*********************************
         *+******************+***********************************
         ***+*************************************+******/*******
         **************+********************+*******/************
         */******************************************************
         ****
         foo@local\n"
        }
      ]
    }
  },
  "location": "eastus",
  "name": "esakscluster",
  "provisioningState": "Succeeded",
  "resourceGroup": "MCACS-AKS",
  "servicePrincipalProfile": {
    "additionalProperties": {},
    "clientId": "6905b305-****-****-****-a2d4eaa65921",
    "keyVaultSecretRef": null,
    "secret": null
  },
  "tags": null,
  "type": "Microsoft.ContainerService/ManagedClusters"
```

After finished, you can confirmed the upgraded like following. 
Please see all node changed to v1.8.7.

```
$ kubectl get node
NAME                       STATUS    ROLES     AGE       VERSION
aks-nodepool1-19381275-0   Ready     agent     37m       v1.8.7
aks-nodepool1-19381275-1   Ready     agent     29m       v1.8.7
aks-nodepool1-19381275-2   Ready     agent     21m       v1.8.7
aks-nodepool1-19381275-3   Ready     agent     14m       v1.8.7
aks-nodepool1-19381275-5   Ready     agent     45m       v1.8.7
```

Also you can see following too.

```
$ kubectl version
Client Version: version.Info{Major:"1", Minor:"9", GitVersion:"v1.9.1", GitCommit:"3a1c9449a********************92f7d55f812", GitTreeState:"clean", BuildDate:"2018-01-04T11:52:23Z", GoVersion:"go1.9.2", Compiler:"gc", Platform:"darwin/amd64"}
Server Version: version.Info{Major:"1", Minor:"8", GitVersion:"v1.8.7", GitCommit:"b30876a5********************10b37738c9c", GitTreeState:"clean", BuildDate:"2018-01-16T21:52:38Z", GoVersion:"go1.8.3", Compiler:"gc", Platform:"linux/amd64"}
```

***Note: K8s version 1.9.x will be suported soon.***

## 2.13 az aks command reference

More detail of ***az aks*** command, please refer to the following manual.

[az aks command reference manual](https://docs.microsoft.com/en-us/cli/azure/aks?view=azure-cli-latest)

---
[Previous Page](Kubernetes-Workshop1.md) / [Next Page](Kubernetes-Workshop3.md)
