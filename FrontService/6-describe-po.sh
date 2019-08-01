$ kubectl describe deployment spring-front-service 

$ kubectl get deployment spring-front-service 
NAME                   READY   UP-TO-DATE   AVAILABLE   AGE
spring-front-service   2/2     2            2           70m

$ kubectl get po
NAME                                   READY   STATUS    RESTARTS   AGE
spring-front-service-c65c977fd-68jkf   1/1     Running   0          26m
spring-front-service-c65c977fd-kshzc   1/1     Running   0          27m
ubuntu-768fc94655-l65tf                1/1     Running   2          13d

$ kubectl describe po spring-front-service-c65c977fd-68jkf 

