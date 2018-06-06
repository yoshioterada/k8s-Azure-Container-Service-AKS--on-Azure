# 3.6 Persistence Volume Mount with Azure Storage 

In order to mount the volume from container, you can select one of the following way.
In fact, which one do you want to select?  

1. Dynamic Blob Storage 
2. Static Blob Storage
3. Dynamic File Storage(Samba)
4. Static File Storage(Samba)


## 1. Dynamic Blob Storage 

Before create the Persistence Volume, please confirm that you can use the Storage Classs of Azure like follow?  

```
$ kubectl get storageclass 
NAME                PROVISIONER                AGE
default (default)   kubernetes.io/azure-disk   34d
managed-premium     kubernetes.io/azure-disk   34d
```

As you can see, you can use 




## 2. Static Blob Storage
## 3. Dynamic File Storage(Samba)
## 4. Static File Storage(Samba)
