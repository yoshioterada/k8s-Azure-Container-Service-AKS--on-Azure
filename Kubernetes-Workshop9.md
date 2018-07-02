[Previous Page](Kubernetes-Workshop8.md) / [Top Page](README.md)
---

***Note: This contents is Beta version!!***

# 9. AKS access to Azure Database for MySQL via VNet

In this article, I will explain how to create the VNet environment between Azure Kubernetes Service(AKS) and Azure Database for MySQL like follows.  
If you creted the following environment, you can only access to the MySQL via secure network environment not public network.

![](https://c2.staticflickr.com/2/1801/41277626490_12643f1c1f_c.jpg)


## 9.1 Create Resource Group for AKS with VNet Support

At first, you create a new Resource Group in the Azure. You will create both VNet and AKS in the Resource Group.

In this time, I will crete new Resource Group as ***MS-AKS_JPN*** as follows.

```
$ az group create --name MC-AKS-JPN --location japaneast

##### Following is the sample result of the above command.
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

Then you create the new Azure VNet as ***AKS-VNET*** in the Resource Group as follows. For the VNet we configured the address range of "10.1.0.0/16".

```
$ az network vnet create -g MC-AKS-JPN \
    --name AKS-VNET \
    --address-prefixes 10.1.0.0/16 \ß
    -l japaneast

##### Following is the sample result of the above command.
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

After you created the new Azure VNet, please create SubNet as ***AKS-Subnet*** in the VNet? We specified the address range for the Subnet as "10.1.0.0/24". And also we specified the  services in the "--service-endpoints" which allowed private access to this subnet. In this time, Mycrosoft.SQL is available to access to this SubNet.

```
$ az network vnet subnet create \
    -g MC-AKS-JPN \
    -n AKS-Subnet \
    --vnet-name AKS-VNET \
    --address-prefix 10.1.0.0/24 \
    --service-endpoints Microsoft.SQL
    
##### Following is the sample result of the above command.
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

After you executed the above command, please copy & paste the SubNet id? Following SubNet ID is important, and you will use it int the following section.

*  ***"id": "/subscriptions/f77aafe8-++++-++++-++++-d0c37687ef70/resourceGroups/MC-AKS-JPN/providers/Microsoft.Network/virtualNetworks/AKS-VNET/subnets/AKS-Subnet"***


After you created both VNet and SubNet, you can confirm the configured information on the Azure Portal as follows.

![015](https://c2.staticflickr.com/2/1782/29215626348_a678d01cb0_c.jpg)

In the VNet(AKS-VNET), you can see the Subnet(AKS-Subnet) and "Microsft.SQL" service is available.

![018](https://c2.staticflickr.com/2/1790/28218834447_fe2448cbdc_c.jpg)


## 9.2 Install AKS with VNet Support

***I COULDN'T execute the above command in this time.*** 

```
$ az aks create -g "MC-AKS-JPN" --name "yoshio-jpneast-aks" \
    --generate-ssh-keys \
    --network-plugin "azure" \
    --vnet-subnet-id "/subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourceGroups/MC-AKS-JPN/providers/Microsoft.Network/virtualNetworks/AKS-VNET/subnets/AKS-Subnet" \
    --kubernetes-version 1.10.3 \
    --node-count 5 \
    --dns-name-prefix yoshio3-aks
```

In the future, we may be able to execute the above command. 
In stead of the above command, we will create the AKS Cluster by using Azure Portal GUI screen. 

At first, please goto the same Resource Group as the above VNet Resourece Group(MC-AKS-JPN)? Then you can see the following screen.

![001a](https://c2.staticflickr.com/2/1806/41276573920_0106aab4ab_c.jpg)

Then please push the ***"+ Create a resource"*** in the Left side of the Portal menu? Then you can see the following screen. And please input the ***"Kubernetes Service"*** in the Search Field and enter the key?

![002](https://c2.staticflickr.com/2/1824/41276573810_cfc42573ec_c.jpg)

Then you can see the following screen. Then pleaes select the ***"Kubernetes Service"***?

![003](https://c2.staticflickr.com/2/1826/41276573870_83f730bc74_c.jpg)

Then following screen will be showed. Please push the ***Create*** button?

![004](https://c1.staticflickr.com/1/846/41276573590_86bf67df4a_c.jpg)

Then following screen will be showed.  

In the Basic Tab, please input following value?

|  Input Element   |  Input Value  |
| ---- | ---- |
|  Subscriptions  |  PLEASE SELECT YOUR OWN APPROPRIATE SUBSCRIPTION?  |
|  Resource Group  |  Use existing (MC-AKS-JPN)  |
|  Kubernetes cluster name  |  yoshio-aks  |
|  Region  |  Japan East  |
|  Kubernetes version  |  1.10.3  |
|  DNS name prefix  |  yoshio-aks  |
|  Service Principal  |  Create or reuse existing  |
|  Node size |  Standard DS1 v2(1 vcpus, 3.5GB memory) |
|  Node count  |  5  |

![006](https://c1.staticflickr.com/1/847/41276573140_c72f75c6c7_c.jpg)
![007](https://c1.staticflickr.com/1/917/42369144174_6d3ca50608_c.jpg)


After input all of the field, please push the ***Next: Networking >*** button?  
Then you can see the following screen.

![008](https://c1.staticflickr.com/1/921/41276572800_00afdbff47_c.jpg)

In the screen, please check ***Advanced*** radio button? Then you can see the expaned configuration like follows.

|  Input Element   |  Input Value  |
| ---- | ---- |
| HTTP application routing  | Yes   |
| Network configuration  |  Advanced  |
| Virtual network  | AKS-VNET (You created the above)  |
| Subnet  | AKS-Subnet (You created the above) |
| Kubernetes service address range  | 10.0.0.0/16   |
| Kubernetes DNS service IP address  | 10.0.0.10   |
| Docker Bridge address  | 172.17.0.1/16   |


![009](https://c2.staticflickr.com/2/1808/42369144044_41ae89b1e9_c.jpg)

After input all of the field, please push the ***Next: Monitoring >*** button?  
Then you can see the following screen. Depend on your system, please create the new ***Log Analytics workspace*** like follows?  
Please push the ***Next: Tags >*** button?  

![010](https://c2.staticflickr.com/2/1826/29215350158_fb62b63158_c.jpg)

Then you can see the following screen.  
In the top of the screen, please push the link of TAB for ***Review + create***?


![011](https://c2.staticflickr.com/2/1827/42369143844_2b9825dee5_c.jpg)

Then you can see the following screen.  
If there is no problem, please push the "create" button?

![012](https://c1.staticflickr.com/1/835/29215349898_54dfb489f7_c.jpg)

Then you can insall the AKS with VNet support.  

After you installed AKS successfully, please get the credential from AKS like follows? Then you will be able to execute "kubectl" command on your local system.

```
$ az aks get-credentials --resource-group MC-AKS-JPN --name yoshio-aks 
Merged "yoshio-aks" as current context in /Users/yoterada/.kube/config

```

## 9.3 Create Resoruce Group for MySQL with VNet Support

After you installed the AKS which available the VNet. you can acess to the Azure Managed Database like SQL Server, MySQL and PostgreSQL from AKS. In this time, we access to the managed MySQL.

For MySQL VNet configuration, there already was very useful document as [Create and manage Azure Database for MySQL VNet service endpoints using Azure CLI](https://docs.microsoft.com/sl-si/azure/mysql/howto-manage-vnet-using-cli).

Based on the above document, I created MySQL with VNet service endpoint as follow.  

At first, please add the Extension for VNet support for DB as follows. After you added it, you can confirm the "az extension list" command.

```
$ az extension add --name rdbms-vnet
$ az extension list

##### Following is the sample result of the above command.
[
  {
    "extensionType": "whl",
    "name": "rdbms-vnet",
    "version": "10.0.0"
  }
]
```

Then, please create new Resource Group for installing the MySQL.

```
$ az group create --name MySQL-RG --location japaneast 

##### Following is the sample result of the above command.
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

After created the Resource Group, please install MySQL Server like follows.

```
$ az mysql server create \
     --name my-mysqlserver \
     --resource-group MySQL-RG \
     --location japaneast \
     --admin-user myloginuser \
     --admin-password PASSWORD \
     --sku-name GP_Gen4_2

##### Following is the sample result of the above command.
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

In order to access to the MySQL from private VNet, please create the "vnet-rule" as follows. In the command ***please specify the "--subnet" argument*** which you got the SubNet ID in the above section like follow.

*  ***"id": "/subscriptions/f77aafe8-++++-++++-++++-d0c37687ef70/resourceGroups/MC-AKS-JPN/providers/Microsoft.Network/virtualNetworks/AKS-VNET/subnets/AKS-Subnet"***

```
$ az mysql server vnet-rule create  \
  -n k8sRule   \
  -g MySQL-RG  \
  -s my-mysqlserver \
  --subnet /subscriptions/f77aafe8-****-****-****-d0c37687ef70/resourceGroups/MC-AKS-JPN/providers/Microsoft.Network/virtualNetworks/AKS-VNET/subnets/AKS-Subnet 

##### Following is the sample result of the above command.
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

After you executed the above command, you can see the following configuration in the Azure Portal.

![015](https://c2.staticflickr.com/2/1808/28218832987_02997c3499_c.jpg)

## 9.5 Access to MySQL from k8s POD (Evaluation)

Now, you configured all to connect to MySQL from the AKS by using private VNet. So please evaluate whether your can connect to the MySQL Server or not by following step?  

1. Create Ubuntu POD in AKS(k8s)
2. Login to the POD in AKS
3. Install MySQL Client Command
4. Connect to MySQL Server from MySQL Client



### 9.5.1 Create Ubuntu POD in AKS(k8s)

At first please create the Deployment YAML file? It will run the Ubuntu on k8s. 


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

In order to apply the above file, please execute the following command?

```
$ kubectl apply -f ubuntu.yaml 
deployment.apps "ubuntu" created
```


### 9.5.2 Login to the POD in AKS

Then you can confirm the ubuntu pod is Runninng on your AKS environment.

```
$ kubectl get po
NAME                      READY     STATUS    RESTARTS   AGE
ubuntu-69cd8cd996-lf4wh   1/1       Running   0          42s
```

In order to login to the Ubuntu, please execute following command?

```
$ kubectl exec -it ubuntu-69cd8cd996-lf4wh /bin/bash
root@ubuntu-69cd8cd996-lf4wh:/#
```

### 9.5.3 Install MySQL Client Command

After you login to the Ubuntu, please install the MySQL Client command as follows?
At first, please update the list of repository?

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

After that, please install the MySQL Client command?

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

### 9.5.4 Connect to MySQL Server from MySQL Client

After you installed the MySQL Client command, please confirm the MySQL Server FQDN address? If you access to the Azure Portal Screen, you can confirm both login user name like ***myloginuser@my-mysqlserve*** and the Server address like ***YOUR_SEVER_NAME.mysql.database.azure.com***.

![013](https://c1.staticflickr.com/1/915/42183213555_a28b39a021_c.jpg)

Then you can connect to the MySQL Server like follows.


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