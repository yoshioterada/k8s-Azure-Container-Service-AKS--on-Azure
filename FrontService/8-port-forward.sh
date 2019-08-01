$ kubectl get po
NAME                                   READY   STATUS    RESTARTS   AGE
spring-front-service-c65c977fd-68jkf   1/1     Running   0          29m
spring-front-service-c65c977fd-kshzc   1/1     Running   0          29m
ubuntu-768fc94655-l65tf                1/1     Running   2          13d
$ kubectl port-forward spring-front-service-c65c977fd-68jkf 8080:8080
Forwarding from 127.0.0.1:8080 -> 8080
Forwarding from [::1]:8080 -> 8080


$ curl http://localhost:8080/sample/hello
front-hello-v1
