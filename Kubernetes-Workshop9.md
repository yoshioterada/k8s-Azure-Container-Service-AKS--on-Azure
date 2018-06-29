[Previous Page](Kubernetes-Workshop8.md) / [Top Page](README.md)
---

***Note: This contents is Beta version!!***

# 9. AKS access to Azure Database for MySQL via VNet

![](https://c2.staticflickr.com/2/1801/41277626490_12643f1c1f_c.jpg)

## 9.1 Create Resource Group for AKS with VNet Support

```
$ az group create --name MC-AKS-JPN --location japaneast
{
  "id": "/subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourceGroups/MC-AKS-JPN",
  "location": "japaneast",
  "managedBy": null,
  "name": "MC-AKS-JPN",
  "properties": {
    "provisioningState": "Succeeded"
  },
  "tags": null
}
```

```
$ az network vnet create -g MC-AKS-JPN --name AKS-VNET --address-prefixes 10.1.0.0/16 -l japaneast
{
  "newVNet": {
    "addressSpace": {
      "addressPrefixes": [
        "10.1.0.0/16"
      ]
    },
    "ddosProtectionPlan": null,
    "dhcpOptions": {
      "dnsServers": []
    },
    "enableDdosProtection": false,
    "enableVmProtection": false,
    "etag": "W/\"e3eb15fb-****-****-****-22e982650529\"",
    "id": "/subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourceGroups/MC-AKS-JPN/providers/Microsoft.Network/virtualNetworks/AKS-VNET",
    "location": "japaneast",
    "name": "AKS-VNET",
    "provisioningState": "Succeeded",
    "resourceGroup": "MC-AKS-JPN",
    "resourceGuid": "8358fee4-****-****-****-05b35450436c",
    "subnets": [],
    "tags": {},
    "type": "Microsoft.Network/virtualNetworks",
    "virtualNetworkPeerings": []
  }
}
```

```
$ az network vnet subnet create \
 -g MC-AKS-JPN \
 -n AKS-Subnet \
 --vnet-name AKS-VNET \
 --address-prefix 10.1.0.0/24 \
 --service-endpoints Microsoft.SQL
{
  "addressPrefix": "10.1.0.0/24",
  "etag": "W/\"bdde7c69-****-****-****-041a057f6be1\"",
  "id": "/subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourceGroups/MC-AKS-JPN/providers/Microsoft.Network/virtualNetworks/AKS-VNET/subnets/AKS-Subnet",
  "ipConfigurations": null,
  "name": "AKS-Subnet",
  "networkSecurityGroup": null,
  "provisioningState": "Succeeded",
  "resourceGroup": "MC-AKS-JPN",
  "resourceNavigationLinks": null,
  "routeTable": null,
  "serviceEndpoints": [
    {
      "locations": [
        "japaneast"
      ],
      "provisioningState": "Succeeded",
      "service": "Microsoft.Sql"
    }
  ]
}
```

![015](https://c2.staticflickr.com/2/1782/29215626348_a678d01cb0_c.jpg)
![016](https://c1.staticflickr.com/1/846/28218833927_233b62fce8_c.jpg)
![018](https://c2.staticflickr.com/2/1790/28218834447_fe2448cbdc_c.jpg)


## 9.2 Install AKS with VNet Support

***We CAN'T execute the above command in this time.*** 

```
$ az aks create -g "MC-AKS-JPN" --name "yoshio-jpneast-aks" \
--generate-ssh-keys \
--network-plugin "azure" \
--vnet-subnet-id "/subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourceGroups/MC-AKS-JPN/providers/Microsoft.Network/virtualNetworks/MC-AKS-JPN/subnets/kubernetes" 
--kubernetes-version 1.10.3 --node-count 5 --dns-name-prefix yoshio3-aks
```

In the future, we may be able to execute the above command. 
In stead of the above command, we will create the AKS Cluster by using Azure Portal GUI screen. 



![001a](https://c2.staticflickr.com/2/1806/41276573920_0106aab4ab_c.jpg)
![002](https://c2.staticflickr.com/2/1824/41276573810_cfc42573ec_c.jpg)
![003](https://c2.staticflickr.com/2/1826/41276573870_83f730bc74_c.jpg)
![004](https://c1.staticflickr.com/1/846/41276573590_86bf67df4a_c.jpg)
![005](https://c2.staticflickr.com/2/1824/42369144324_65b719bf7a_c.jpg)
![006](https://c1.staticflickr.com/1/847/41276573140_c72f75c6c7_c.jpg)
![007](https://c1.staticflickr.com/1/917/42369144174_6d3ca50608_c.jpg)
![008](https://c1.staticflickr.com/1/921/41276572800_00afdbff47_c.jpg)
![009](https://c2.staticflickr.com/2/1808/42369144044_41ae89b1e9_c.jpg)
![010](https://c2.staticflickr.com/2/1826/29215350158_fb62b63158_c.jpg)
![011](https://c2.staticflickr.com/2/1827/42369143844_2b9825dee5_c.jpg)
![012](https://c1.staticflickr.com/1/835/29215349898_54dfb489f7_c.jpg)



```
$ az aks get-credentials --resource-group MC-AKS-JPN --name yoshio-aks 
Merged "yoshio-aks" as current context in /Users/yoterada/.kube/config

```



## 9.3 Create Resoruce Group for MySQL with VNet Support


```
$ az extension add --name rdbms-vnet
$ az extension list
[
  {
    "extensionType": "whl",
    "name": "rdbms-vnet",
    "version": "10.0.0"
  }
]
```

```
$ az group create --name MySQL-RG --location japaneast 
{
  "id": "/subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourceGroups/MySQL-RG",
  "location": "japaneast",
  "managedBy": null,
  "name": "MySQL-RG",
  "properties": {
    "provisioningState": "Succeeded"
  },
  "tags": null
}
```

```
$ az mysql server create \
     --name my-mysqlserver \
     --resource-group MySQL-RG \
     --location japaneast \
     --admin-user myloginuser \
     --admin-password PASSWORD \
     --sku-name GP_Gen4_2
{
  "administratorLogin": "yoterada",
  "earliestRestoreDate": "2018-06-29T12:21:50.183000+00:00",
  "fullyQualifiedDomainName": "my-mysqlserver.mysql.database.azure.com",
  "id": "/subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourceGroups/MySQL-RG/providers/Microsoft.DBforMySQL/servers/my-mysqlserver",
  "location": "japaneast",
  "name": "my-mysqlserver",
  "resourceGroup": "MySQL-RG",
  "sku": {
    "capacity": 2,
    "family": "Gen4",
    "name": "GP_Gen4_2",
    "size": null,
    "tier": "GeneralPurpose"
  },
  "sslEnforcement": "Enabled",
  "storageProfile": {
    "backupRetentionDays": 7,
    "geoRedundantBackup": "Disabled",
    "storageMb": 5120
  },
  "tags": null,
  "type": "Microsoft.DBforMySQL/servers",
  "userVisibleState": "Ready",
  "version": "5.7"
}
$ 
```

## 9.4 Add Security Rule for AKS to MySQL



```
$ az mysql server vnet-rule create  \
  -n k8sRule   \
  -g MySQL-RG  \
  -s my-mysqlserver \
  --subnet /subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourceGroups/MC-AKS-JPN/providers/Microsoft.Network/virtualNetworks/AKS-VNET/subnets/AKS-Subnet 
{
  "id": "/subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourceGroups/MySQL-RG/providers/Microsoft.DBforMySQL/servers/my-mysqlserver/virtualNetworkRules/k8sRule",
  "ignoreMissingVnetServiceEndpoint": false,
  "name": "k8sRule",
  "resourceGroup": "MySQL-RG",
  "state": "Ready",
  "type": "Microsoft.DBforMySQL/servers/virtualNetworkRules",
  "virtualNetworkSubnetId": "/subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourceGroups/MC-AKS-JPN/providers/Microsoft.Network/virtualNetworks/AKS-VNET/subnets/AKS-Subnet"
}
$
```

![015](https://c2.staticflickr.com/2/1808/28218832987_02997c3499_c.jpg)

## 9.5 Access to MySQL from k8s POD

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: ubuntu
  labels:
    app: ubuntu
spec:
  replicas: 1
  selector:
    matchLabels:
      app: ubuntu
  template:
    metadata:
      labels:
        app: ubuntu
        env: test
        version: v1
    spec:
      containers:
      - name: ubuntu
        image: ubuntu
        command:
          - sleep
          - infinity
```

```
$ kubectl apply -f ubuntu.yaml 
deployment.apps "ubuntu" created
```

```
$ kubectl get po
NAME                      READY     STATUS    RESTARTS   AGE
ubuntu-69cd8cd996-lf4wh   1/1       Running   0          42s
```

```
$ kubectl exec -it ubuntu-69cd8cd996-lf4wh /bin/bash
root@ubuntu-69cd8cd996-lf4wh:/#
```


```
root@ubuntu-69cd8cd996-lf4wh:/# apt update
Get:1 http://security.ubuntu.com/ubuntu bionic-security InRelease [83.2 kB]
Get:2 http://archive.ubuntu.com/ubuntu bionic InRelease [242 kB]
Get:3 http://archive.ubuntu.com/ubuntu bionic-updates InRelease [88.7 kB]
...
Get:18 http://archive.ubuntu.com/ubuntu bionic-backports/universe amd64 Packages [2807 B]
Fetched 25.5 MB in 6s (4107 kB/s)                                                                                                                                                                    
Reading package lists... Done
Building dependency tree       
Reading state information... Done
8 packages can be upgraded. Run 'apt list --upgradable' to see them.
```

```
root@ubuntu-69cd8cd996-lf4wh:/# apt install  mysql-client
Reading package lists... Done
Building dependency tree       
Reading state information... Done
The following additional packages will be installed:
  libaio1 libedit2 libnuma1 mysql-client-5.7 mysql-client-core-5.7 mysql-common
...
Setting up mysql-client-core-5.7 (5.7.22-0ubuntu18.04.1) ...
Setting up mysql-client-5.7 (5.7.22-0ubuntu18.04.1) ...
Setting up mysql-client (5.7.22-0ubuntu18.04.1) ...
Processing triggers for libc-bin (2.27-3ubuntu1) ...
```

![013](https://c1.staticflickr.com/1/915/42183213555_a28b39a021_c.jpg)

```
root@ubuntu-69cd8cd996-lf4wh:/# mysql -u myloginuser@my-mysqlserver \
-h my-mysqlserver.mysql.database.azure.com -p
Enter password: 
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 65403
Server version: 5.6.39.0 MySQL Community Server (GPL)

Copyright (c) 2000, 2018, Oracle and/or its affiliates. All rights reserved.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| mysql              |
| performance_schema |
| sys                |
+--------------------+
4 rows in set (0.00 sec)

mysql> show variables like "chara%";
+--------------------------+--------------------------+
| Variable_name            | Value                    |
+--------------------------+--------------------------+
| character_set_client     | latin1                   |
| character_set_connection | latin1                   |
| character_set_database   | latin1                   |
| character_set_filesystem | binary                   |
| character_set_results    | latin1                   |
| character_set_server     | latin1                   |
| character_set_system     | utf8                     |
| character_sets_dir       | c:\mysql\share\charsets\ |
+--------------------------+--------------------------+
8 rows in set (0.01 sec)

```

---
[Previous Page](Kubernetes-Workshop8.md) / [Top Page](README.md)