[Previous Page](Kubernetes-Workshop7.md) / [Top Page](README.md)
---
***Note: This contents is Beta version!!***

---
* [Azure 無料アカウントのご取得はコチラから](https://aka.ms/jjug_mar2)  
***Azure の無料アカウントで料金は発生しますか？  
いいえ。無料で開始でき、最初の 30 日間に使用する ¥22,500 クレジットを取得します。サービスの利用を開始した後でも、アップグレードするまでは無料で利用できます。***  

---

# 8. Useful Tool for k8s managing and monitoring 

## 8.1 Development & Deploy

### 8.1.1 Helm (package management)
[Helm](https://github.com/kubernetes/helm) is a package manager for managing Kubernetes. Charts are packages of pre-configured Kubernetes resources.

Default available package is as follows.

```
$ helm search
NAME                                   	VERSION	DESCRIPTION                                       
stable/acs-engine-autoscaler           	2.1.1  	Scales worker nodes within agent pools            
stable/aerospike                       	0.1.5  	A Helm chart for Aerospike in Kubernetes          
stable/artifactory                     	6.2.4  	Universal Repository Manager supporting all maj...
stable/aws-cluster-autoscaler          	0.3.2  	Scales worker nodes within autoscaling groups.    
stable/buildkite                       	0.2.0  	Agent for Buildkite                               
stable/centrifugo                      	2.0.0  	Centrifugo is a real-time messaging server.       
stable/chaoskube                       	0.6.2  	Chaoskube periodically kills random pods in you...
stable/chronograf                      	0.4.0  	Open-source web application written in Go and R...
stable/cluster-autoscaler              	0.3.1  	Scales worker nodes within autoscaling groups.    
stable/cockroachdb                     	0.5.4  	CockroachDB is a scalable, survivable, strongly...
stable/concourse                       	0.10.8 	Concourse is a simple and scalable CI system.     
stable/consul                          	1.1.3  	Highly available and distributed service discov...
stable/coredns                         	0.8.0  	CoreDNS is a DNS server that chains plugins and...
stable/coscale                         	0.2.0  	CoScale Agent                                     
stable/dask-distributed                	2.0.0  	Distributed computation in Python                 
stable/datadog                         	0.10.3 	DataDog Agent                                     
stable/docker-registry                 	1.0.0  	A Helm chart for Docker Registry                  
stable/dokuwiki                        	0.2.1  	DokuWiki is a standards-compliant, simple to us...
stable/drupal                          	0.11.3 	One of the most versatile open source content m...
stable/elastalert                      	0.1.1  	ElastAlert is a simple framework for alerting o...
stable/etcd-operator                   	0.6.2  	CoreOS etcd-operator Helm chart for Kubernetes    
stable/external-dns                    	0.4.4  	Configure external DNS servers (AWS Route53, Go...
stable/factorio                        	0.3.0  	Factorio dedicated server.                        
stable/fluent-bit                      	0.2.4  	Fast and Lightweight Log/Data Forwarder for Lin...
stable/g2                              	0.2.0  	G2 by AppsCode - Gearman in Golang                
stable/gcloud-endpoints                	0.1.0  	Develop, deploy, protect and monitor your APIs ...
stable/gcloud-sqlproxy                 	0.2.2  	Google Cloud SQL Proxy                            
stable/ghost                           	2.1.7  	A simple, powerful publishing platform that all...
stable/gitlab-ce                       	0.2.1  	GitLab Community Edition                          
stable/gitlab-ee                       	0.2.1  	GitLab Enterprise Edition                         
stable/grafana                         	0.5.4  	The leading tool for querying and visualizing t...
stable/hadoop                          	1.0.1  	The Apache Hadoop software library is a framewo...
stable/heapster                        	0.2.4  	Heapster enables Container Cluster Monitoring a...
stable/influxdb                        	0.8.0  	Scalable datastore for metrics, events, and rea...
stable/ipfs                            	0.2.0  	A Helm chart for the Interplanetary File System   
stable/jasperreports                   	0.2.3  	The JasperReports server can be used as a stand...
stable/jenkins                         	0.12.0 	Open source continuous integration server. It s...
stable/joomla                          	0.5.4  	PHP content management system (CMS) for publish...
stable/kapacitor                       	0.5.0  	InfluxDB's native data processing engine. It ca...
stable/keel                            	0.2.0  	Open source, tool for automating Kubernetes dep...
stable/kibana                          	0.2.0  	Kibana is an open source data visualization plu...
stable/kube-lego                       	0.3.0  	Automatically requests certificates from Let's ...
stable/kube-ops-view                   	0.4.1  	Kubernetes Operational View - read-only system ...
stable/kube-state-metrics              	0.5.1  	Install kube-state-metrics to generate and expo...
stable/kube2iam                        	0.6.1  	Provide IAM credentials to pods based on annota...
stable/kubed                           	0.2.0  	Kubed by AppsCode - Kubernetes daemon             
stable/kubernetes-dashboard            	0.4.3  	General-purpose web UI for Kubernetes clusters    
stable/lamp                            	0.1.0  	Modular and transparent LAMP stack chart suppor...
stable/linkerd                         	0.4.0  	Service mesh for cloud native apps                
stable/locust                          	0.1.2  	A modern load testing framework                   
stable/magento                         	0.5.2  	A feature-rich flexible e-commerce solution. It...
stable/mailhog                         	2.1.0  	An e-mail testing tool for developers             
stable/mariadb                         	2.1.3  	Fast, reliable, scalable, and easy to use open-...
stable/mcrouter                        	0.1.0  	Mcrouter is a memcached protocol router for sca...
stable/mediawiki                       	0.6.1  	Extremely powerful, scalable software and a fea...
stable/memcached                       	2.0.1  	Free & open source, high-performance, distribut...
stable/metabase                        	0.3.0  	The easy, open source way for everyone in your ...
stable/minecraft                       	0.2.0  	Minecraft server                                  
stable/minio                           	0.4.3  	Distributed object storage server built for clo...
stable/mongodb                         	0.4.24 	NoSQL document-oriented database that stores JS...
stable/mongodb-replicaset              	2.1.4  	NoSQL document-oriented database that stores JS...
stable/moodle                          	0.4.2  	Moodle is a learning platform designed to provi...
stable/msoms                           	0.1.1  	A chart for deploying omsagent as a daemonset K...
stable/mysql                           	0.3.4  	Fast, reliable, scalable, and easy to use open-...
stable/namerd                          	0.2.0  	Service that manages routing for multiple linke...
stable/neo4j                           	0.5.0  	Neo4j is the world's leading graph database       
stable/newrelic-infrastructure         	0.0.1  	A Helm chart to deploy the New Relic Infrastruc...
stable/nginx-ingress                   	0.8.23 	An nginx Ingress controller that uses ConfigMap...
stable/nginx-lego                      	0.3.0  	Chart for nginx-ingress-controller and kube-lego  
stable/odoo                            	0.7.0  	A suite of web based open source business apps.   
stable/opencart                        	0.6.0  	A free and open source e-commerce platform for ...
stable/openvpn                         	2.0.2  	A Helm chart to install an openvpn server insid...
stable/orangehrm                       	0.5.0  	OrangeHRM is a free HR management system that o...
stable/osclass                         	0.5.0  	Osclass is a php script that allows you to quic...
stable/owncloud                        	0.5.3  	A file sharing server that puts the control and...
stable/pachyderm                       	0.1.1  	Pachyderm is a large-scale container-based work...
stable/parse                           	0.3.2  	Parse is a platform that enables users to add a...
stable/percona                         	0.3.0  	free, fully compatible, enhanced, open source d...
stable/phabricator                     	0.5.5  	Collection of open source web applications that...
stable/phpbb                           	0.6.1  	Community forum that supports the notion of use...
stable/postgresql                      	0.8.5  	Object-relational database management system (O...
stable/prestashop                      	0.5.3  	A popular open source ecommerce solution. Profe...
stable/prometheus                      	4.6.17 	Prometheus is a monitoring system and time seri...
stable/prometheus-to-sd                	0.1.0  	Scrape metrics stored in prometheus format and ...
stable/rabbitmq                        	0.6.15 	Open source message broker software that implem...
stable/rabbitmq-ha                     	0.1.1  	Highly available RabbitMQ cluster, the open sou...
stable/redis                           	1.1.7  	Open source, advanced key-value store. It is of...
stable/redis-ha                        	2.0.0  	Highly available Redis cluster with multiple se...
stable/redmine                         	2.0.2  	A flexible project management web application.    
stable/rethinkdb                       	0.1.0  	The open-source database for the realtime web     
stable/risk-advisor                    	2.0.0  	Risk Advisor add-on module for Kubernetes         
stable/rocketchat                      	0.1.2  	Prepare to take off with the ultimate chat plat...
stable/sapho                           	0.2.1  	A micro application development and integration...
stable/searchlight                     	0.2.0  	Searchlight by AppsCode - Alerts for Kubernetes   
stable/selenium                        	0.2.5  	Chart for selenium grid                           
stable/sensu                           	0.2.0  	Sensu monitoring framework backed by the Redis ...
stable/sentry                          	0.1.7  	Sentry is a cross-platform crash reporting and ...
stable/sonarqube                       	0.3.2  	Sonarqube is an open sourced code quality scann...
stable/sonatype-nexus                  	0.1.6  	Sonatype Nexus is an open source repository man...
stable/spark                           	0.1.7  	Fast and general-purpose cluster computing system.
stable/spartakus                       	1.1.3  	Collect information about Kubernetes clusters t...
stable/spinnaker                       	0.3.10 	Open source, multi-cloud continuous delivery pl...
stable/spotify-docker-gc               	0.1.1  	A simple Docker container and image garbage col...
stable/stash                           	0.2.0  	Stash by AppsCode - Backup your Kubernetes Volumes
stable/sugarcrm                        	0.2.2  	SugarCRM enables businesses to create extraordi...
stable/suitecrm                        	0.3.2  	SuiteCRM is a completely open source enterprise...
stable/sumokube                        	0.1.1  	Sumologic Log Collector                           
stable/sumologic-fluentd               	0.2.1  	Sumologic Log Collector                           
stable/swift                           	0.3.0  	swift by AppsCode - Ajax friendly Helm Tiller P...
stable/sysdig                          	0.4.0  	Sysdig Monitor and Secure agent                   
stable/telegraf                        	0.3.1  	Telegraf is an agent written in Go for collecti...
stable/testlink                        	0.4.16 	Web-based test management system that facilitat...
stable/traefik                         	1.15.2 	A Traefik based Kubernetes ingress controller w...
stable/uchiwa                          	0.2.2  	Dashboard for the Sensu monitoring framework      
stable/verdaccio                       	0.1.2  	A lightweight private npm proxy registry (sinop...
stable/voyager                         	2.1.0  	Voyager by AppsCode - Secure Ingress Controller...
stable/weave-cloud                     	0.1.2  	Weave Cloud is a add-on to Kubernetes which pro...
stable/weave-scope                     	0.9.1  	A Helm chart for the Weave Scope cluster visual...
stable/wordpress                       	0.7.10 	Web publishing platform for building blogs and ...
stable/zeppelin                        	1.0.0  	Web-based notebook that enables data-driven, in...
stable/zetcd                           	0.1.4  	CoreOS zetcd Helm chart for Kubernetes            
```

For example, if you would like to install some package, you can execute following command.

```
$ helm install stable/jenkins --name jenkins --namespace default
NAME:   jenkins
LAST DEPLOYED: Wed Feb 28 03:20:38 2018
NAMESPACE: default
STATUS: DEPLOYED

RESOURCES:
==> v1/Secret
NAME             TYPE    DATA  AGE
jenkins-jenkins  Opaque  2     5s

==> v1/ConfigMap
NAME                   DATA  AGE
jenkins-jenkins        3     5s
jenkins-jenkins-tests  1     5s

==> v1/PersistentVolumeClaim
NAME             STATUS   VOLUME   CAPACITY  ACCESS MODES  STORAGECLASS  AGE
jenkins-jenkins  Pending  default  5s

==> v1/Service
NAME                   TYPE          CLUSTER-IP    EXTERNAL-IP  PORT(S)         AGE
jenkins-jenkins-agent  ClusterIP     10.0.177.193  <none>       50000/TCP       5s
jenkins-jenkins        LoadBalancer  10.0.24.138   <pending>    8080:32012/TCP  5s

==> v1beta1/Deployment
NAME             DESIRED  CURRENT  UP-TO-DATE  AVAILABLE  AGE
jenkins-jenkins  1        1        1           0          5s

==> v1/Pod(related)
NAME                             READY  STATUS   RESTARTS  AGE
jenkins-jenkins-5454775b5-d9tw8  0/1    Pending  0         5s


NOTES:
1. Get your 'admin' user password by running:
  printf $(kubectl get secret --namespace default jenkins-jenkins -o jsonpath="{.data.jenkins-admin-password}" | base64 --decode);echo
2. Get the Jenkins URL to visit by running these commands in the same shell:
  NOTE: It may take a few minutes for the LoadBalancer IP to be available.
        You can watch the status of by running 'kubectl get svc --namespace default -w jenkins-jenkins'
  export SERVICE_IP=$(kubectl get svc --namespace default jenkins-jenkins --template "{{ range (index .status.loadBalancer.ingress 0) }}{{ . }}{{ end }}")
  echo http://$SERVICE_IP:8080/login

3. Login with the password from step 1 and the username: admin

For more information on running Jenkins on Kubernetes, visit:
https://cloud.google.com/solutions/jenkins-on-container-engine
```

According to the above explanation, automatically it created the ***secret***. So I can confirm the default value for jenkins admin and password as follows.

```
$ kubectl get secret jenkins-jenkins -n default -o yaml
apiVersion: v1
data:
  jenkins-admin-password: S0JYbk9DYm9yNg==
  jenkins-admin-user: YWRtaW4=
kind: Secret
metadata:
  creationTimestamp: 2018-02-27T18:20:40Z
  labels:
    app: jenkins-jenkins
    chart: jenkins-0.12.0
    heritage: Tiller
    release: jenkins
  name: jenkins-jenkins
  namespace: default
  resourceVersion: "6912775"
  selfLink: /api/v1/namespaces/default/secrets/jenkins-jenkins
  uid: ea36bba2-1bea-11e8-aa36-0a58ac1f25b8
type: Opaque

$ echo "YWRtaW4="| base64 --decode
admin

$ echo "S0JYbk9DYm9yNg==" |base64 --decode
KBXnOCbor6
```

### [8.1.2 Draft (Dev & Deploy)](https://github.com/Azure/draft/)
[Draft](https://github.com/Azure/draft/) is Streamlined Kubernetes Development Tool.Draft makes it easy to build applications that run on Kubernetes. It automatically detect which programing languages is used such like Java, Python and so on.

1. draft create to containerize your application based on Draft packs
2. draft up to deploy your application to a Kubernetes dev sandbox, accessible via a public URL
3. Use a local editor to modify the application, with changes deployed to Kubernetes in seconds

```
$ mvn clean package
Picked up JAVA_TOOL_OPTIONS: -Dfile.encoding=UTF-8
[INFO] Scanning for projects...
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] Building helloworld 1.0
[INFO] ------------------------------------------------------------------------
....
[INFO] org/eclipse/jetty/ already added, skipping
[INFO] about.html already added, skipping
[INFO] META-INF/maven/ already added, skipping
[INFO] META-INF/maven/org.eclipse.jetty/ already added, skipping
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 28.994 s
[INFO] Finished at: 2018-02-28T03:32:24+09:00
[INFO] Final Memory: 25M/260M
[INFO] ------------------------------------------------------------------------

$ ls
pom.xml	src	 target

$ draft create
--> Draft detected Java (82.553681%)
--> Ready to sail

$ ls
Dockerfile	charts		draft.toml	pom.xml		src		target

```

In the above, after executed the ***draft create*** command, automatically Dockerfile, charts directory and draft.toml file was created.  

```
$ draft up
Draft Up Started: 'example-java'
example-java: Building Docker Image: SUCCESS ⚓  (45.1565s)
example-java: Pushing Docker Image: SUCCESS ⚓  (12.1099s)
example-java: Releasing Application: SUCCESS ⚓  (0.9900s)
example-java: Build ID: 01C7A166E3BCS514PCARGY0BF4
```

After executed the ***draft up*** command, the command automatically build, push and release the image to k8s environment.

```
$ kubectl get deployment,svc,po -n default
NAME                              DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
deploy/example-java-java          2         2         2            2           4m

NAME                           TYPE           CLUSTER-IP     EXTERNAL-IP     PORT(S)          AGE
svc/example-java-java          ClusterIP      10.0.253.2     <none>          80/TCP           4m
svc/kubernetes                 ClusterIP      10.0.0.1       <none>          443/TCP          47d

NAME                                           READY     STATUS    RESTARTS   AGE
po/example-java-java-bdc8f457f-qnwdx           1/1       Running   0          4m
po/example-java-java-bdc8f457f-tbnn7           1/1       Running   0          4m
```

After running the pod, you can execute ***draft connect*** command. 
It automaticaly configure the ***port forwarding*** configuration.

```
$ draft connect
Connecting to your app...SUCCESS...Connect to your app on localhost:63699
Starting log streaming...
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
== Spark has ignited ...
>> Listening on 0.0.0.0:4567
```

Then you can access to the service by local invocation.

```
$ curl http://localhost:63699
Hello World, I'm Java!
```


### [8.1.3 Spinnaker Release Pipeline](https://www.spinnaker.io/)

[Spinnaker](https://www.spinnaker.io/) is an open source, multi-cloud continuous delivery platform for releasing software changes with high velocity and confidence.

## 8.2 Monitoring on Server
### [8.2.1 Prometheus](https://prometheus.io/)
[Prometheus](https://prometheus.io/) is Monitoring system & time series database.

### [8.2.2 Grafana](https://grafana.com/)
[Grafana](https://grafana.com/) is analytics platform.
Grafana allows you to query, visualize, alert on and understand your metrics no matter where they are stored. Create, explore, and share dashboards with your team and foster a data driven culture.

### [8.2.3 fluentd](https://docs.fluentd.org/v0.12/articles/kubernetes-fluentd) with [ELK Stack](https://www.elastic.co/jp/elk-stack) 
[fluentd](https://docs.fluentd.org/v0.12/articles/kubernetes-fluentd) is an open source data collector for unified logging layer. Kubernetes provides two logging end-points for applications and cluster logs: Behind the scenes there is a logging agent that take cares of log collection, parsing and distribution: Fluentd. And the [Fluentd is possible to connect ElasticSearch](https://github.com/fluent/fluentd-kubernetes-daemonset/blob/master/fluentd-daemonset-elasticsearch.yaml).


### [8.2.4 Searchlight](https://github.com/appscode/searchlight)

 If you are running production workloads in Kubernetes, you probably want to be alerted when things go wrong. Icinga periodically runs various checks on a Kubernetes cluster and sends notifications if detects an issue. It also nicely supplements whitebox monitoring tools like, Prometheus with blackbox monitoring can catch problems that are otherwise invisible, and also serves as a fallback in case internal systems completely fail. Searchlight is a CRD controller for Kubernetes built around Icinga to address these issues. Searchlight can do the following things for you:


* Periodically run various checks on a Kubernetes cluster and its nodes or pods.  
* Includes a suite of check commands written specifically for Kubernetes.
* Searchlight can send notifications via Email, SMS or Chat.
* Supplements the whitebox monitoring tools like Prometheus.


### [8.2.5 Weave Scope](https://github.com/weaveworks/scope)

***This is awsome tool !!***  
I recommend you to install it.
Weave Scope automatically generates a map of your application, enabling you to intuitively understand, monitor, and control your containerized, microservices based application.

***Install the Weave***
```
$ kubectl apply -f "https://cloud.weave.works/k8s/scope.yaml?k8s-version=$(kubectl version | base64 | tr -d '\n')"
```

After installed the weave, you can confirm the installation as follows.

```
$ kubectl get deployment,svc,po -n weave
NAME                     DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
deploy/weave-scope-app   1         1         1            1           7d

NAME                  TYPE        CLUSTER-IP     EXTERNAL-IP   PORT(S)   AGE
svc/weave-scope-app   ClusterIP   10.0.202.246   <none>        80/TCP    7d

NAME                                  READY     STATUS    RESTARTS   AGE
po/weave-scope-agent-bw82t            1/1       Running   0          1d
po/weave-scope-agent-d5wkc            1/1       Running   0          1d
po/weave-scope-agent-p4vpr            1/1       Running   0          1d
po/weave-scope-agent-pcvdn            1/1       Running   0          1d
po/weave-scope-agent-wjlbr            1/1       Running   0          1d
po/weave-scope-app-1197481323-62brp   1/1       Running   0          1d
```

In order to access to the Weave GUI console, please execute the following. 

```
kubectl port-forward -n weave "$(kubectl get -n weave pod --selector=weave-scope-component=app -o jsonpath='{.items..metadata.name}')" 4040
```

After that, you can confirm the detail of k8s environment in the Browser.  
Please access to [http://localhost:4040/](http://localhost:4040/)? Then you can see look like follwing screen.

![](https://c1.staticflickr.com/5/4753/26651017328_72d095cf1c_z.jpg)

And also, it is possible to console login from GUI too.

![](https://c1.staticflickr.com/5/4655/26651017458_f07e2205f9_z.jpg)


## 8.3 Monotoring on Desktop
### [8.3.1 Kubernetic](https://kubernetic.com/)

***This is awsome tool !!***  
[Kubernetic](https://kubernetic.com/) is Desktop tool for Kubernetes.

In your Desktop environment, you can confirm the k8s status.

![](https://c1.staticflickr.com/5/4617/40479708482_8fddcd5b62_z.jpg)

![](https://c1.staticflickr.com/5/4714/26651168948_0e9d80d123_z.jpg)


### [8.3.2 Kubetail](https://github.com/johanhaleby/kubetail)
[Kubetail](https://github.com/johanhaleby/kubetail)
Bash script that enables you to aggregate (tail/follow) logs from multiple pods into one stream. This is the same as running "kubectl logs -f " but for multiple pods.

For example, Following pod is running in my environment.

```
$ kubectl get po
NAME                                          READY     STATUS             RESTARTS   AGE
account-service-7c7cbf499c-7xqp6              2/2       Running            0          3h
account-service-7c7cbf499c-p7d77              1/2       Running            0         3h
customer-management-service-872683917-46z6b   2/2       Running            0          1d
```

In this situation, I killed the pod of "account-service-7c7cbf499c-p7d77". Then new pod is creating and started to show the log messages like follows.

```
$ kubetail account-service
Will tail 6 logs...
account-service-7c7cbf499c-7xqp6 account-service
account-service-7c7cbf499c-7xqp6 istio-proxy
account-service-7c7cbf499c-fxss2 account-service
account-service-7c7cbf499c-fxss2 istio-proxy
account-service-7c7cbf499c-p7d77 account-service
account-service-7c7cbf499c-p7d77 istio-proxy
[account-service-7c7cbf499c-fxss2 account-service] [2018-02-27T19:30:26.156+0000] [] [INFO] [] [fish.payara.micro.boot.runtime.PayaraMicroRuntimeBuilder] [tid: _ThreadID=1 _ThreadName=main] [timeMillis: 1519759826156] [levelValue: 800] Built Payara Micro Runtime 
[account-service-7c7cbf499c-fxss2 account-service]  
[account-service-7c7cbf499c-fxss2 account-service] [2018-02-27T19:30:33.427+0000] [] [INFO] [NCLS-CORE-00046] [javax.enterprise.system.core] [tid: _ThreadID=1 _ThreadName=main] [timeMillis: 1519759833427] [levelValue: 800] Cannot find javadb client jar file, derby jdbc driver will not be available by default. 
[account-service-7c7cbf499c-fxss2 account-service]  
[account-service-7c7cbf499c-fxss2 account-service] [2018-02-27T19:30:34.700+0000] [] [INFO] [SEC-SVCS-00100] [javax.enterprise.security.services] [tid: _ThreadID=1 _ThreadName=main] [timeMillis: 1519759834700] [levelValue: 800] Authorization Service has successfully initialized. 
[account-service-7c7cbf499c-fxss2 account-service]  
[account-service-7c7cbf499c-fxss2 account-service] [2018-02-27T19:30:40.549+0000] [] [INFO] [NCLS-CORE-00087] [javax.enterprise.system.core] [tid: _ThreadID=1 _ThreadName=main] [timeMillis: 1519759840549] [levelValue: 800] Grizzly Framework 2.3.31 started in: 439ms - bound to [/0.0.0.0:8080] 
[account-service-7c7cbf499c-fxss2 account-service]  
[account-service-7c7cbf499c-fxss2 account-service] [2018-02-27T19:30:40.556+0000] [] [INFO] [NCLS-CORE-00058] [javax.enterprise.system.core] [tid: _ThreadID=1 _ThreadName=main] [timeMillis: 1519759840556] [levelValue: 800] Network listener https-listener on port 8443 disabled per domain.xml 
[account-service-7c7cbf499c-fxss2 account-service]  
[account-service-7c7cbf499c-fxss2 account-service] [2018-02-27T19:30:41.455+0000] [] [INFO] [] [org.glassfish.ha.store.spi.BackingStoreFactoryRegistry] [tid: _ThreadID=1 _ThreadName=main] [timeMillis: 1519759841455] [levelValue: 800] Registered fish.payara.ha.hazelcast.store.HazelcastBackingStoreFactoryProxy for persistence-type = hazelcast in BackingStoreFactoryRegistry 
[account-service-7c7cbf499c-fxss2 account-service]  
[account-service-7c7cbf499c-fxss2 account-service] [2018-02-27T19:30:41.457+0000] [] [INFO] [] [fish.payara.ha.hazelcast.store.HazelcastBackingStoreFactory] [tid: _ThreadID=1 _ThreadName=main] [timeMillis: 1519759841457] [levelValue: 800] Registered Hazelcast BackingStoreFactory with persistence-type = hazelcast 
[account-service-7c7cbf499c-fxss2 account-service]  
[account-service-7c7cbf499c-fxss2 account-service] [2018-02-27T19:30:41.947+0000] [] [INFO] [] [com.hazelcast.config.UrlXmlConfig] [tid: _ThreadID=1 _ThreadName=main] [timeMillis: 1519759841947] [levelValue: 800] Configuring Hazelcast from 'file:/tmp/payaramicro-rt1679584438491554132tmp/config/hazelcast-default.xml'. 
[account-service-7c7cbf499c-fxss2 account-service]  
```


## 8.4 Management
### [8.4.1 Heptio Ark (Backup & Restore)](https://github.com/heptio/ark)
[Heptio Ark](https://github.com/heptio/ark) is a utility for managing disaster recovery, specifically for your Kubernetes cluster resources and persistent volumes. Brought to you by Heptio. https://www.heptio.com

[YouTube Video](https://www.youtube.com/watch?v=qRPNuT080Hk)

### 8.4.2 [Chaos Monkey for k8s(kube-monkey)](https://github.com/asobti/kube-monkey)

[kube-monkey](https://github.com/asobti/kube-monkey) is an implementation of Netflix's Chaos Monkey for Kubernetes clusters. It randomly deletes Kubernetes pods in the cluster encouraging and validating the development of failure-resilient services.

### [8.4.3 Open Policy Agent](http://www.openpolicyagent.org/)

[Kubernetes Admission Control](http://www.openpolicyagent.org/docs/kubernetes-admission-control.html)
In Kubernetes, Admission Controllers enforce semantic validation of objects during create, update, and delete operations. With OPA you can enforce custom policies on Kubernetes objects without recompiling or reconfiguring the Kubernetes API server.

### [8.4.4 Ksonnet (instead of YAML)](https://ksonnet.io/)

A CLI-supported framework for extensible Kubernetes configurations
Built on the JSON templating language Jsonnet, ksonnet simplifies defining an application configuration, updating the configuration over time, and specializing it for different clusters and environments.

### [8.4.5 Open Service Broker & Service Catalog](https://docs.microsoft.com/en-us/azure/aks/integrate-azure)

[Kubernetes Service Catalog](https://kubernetes.io/docs/concepts/service-catalog/)  
***"Service Catalog"*** provides a way to list, provision, and bind with external Managed Services from Service Brokers without needing detailed knowledge about how those services are created or managed.

[Open Service Broker API](https://www.openservicebrokerapi.org/)  
[Open Service Broker API Specification](https://github.com/openservicebrokerapi/servicebroker/blob/master/spec.md)


For Azure, There is an "Open Service Broker for Azure (OSBA)" is now developing. The functionality is Alpha Status and now proceeding the development day by day. So no assurances are made about backwards compatibility or stability until Open Service Broker for Azure has reached v1.

Please refer to the following install documents for Azure? 
 
1. [https://docs.microsoft.com/en-us/azure/aks/integrate-azure](https://docs.microsoft.com/en-us/azure/aks/integrate-azure)  
2. [https://github.com/Azure/service-catalog-cli](https://github.com/Azure/service-catalog-cli)

At first please add the helm repository?

```
helm repo add svc-cat https://svc-catalog-charts.storage.googleapis.com
```

Then you can install the "Service Catalog" by using following command.

```
$ helm install svc-cat/catalog --name catalog --namespace catalog --set rbacEnable=false
NAME:   catalog
LAST DEPLOYED: Wed Feb 28 15:41:17 2018
NAMESPACE: catalog
STATUS: DEPLOYED

RESOURCES:
==> v1beta1/APIService
NAME                           AGE
v1beta1.servicecatalog.k8s.io  2s

==> v1/ServiceAccount
NAME                                SECRETS  AGE
service-catalog-apiserver           1        2s
service-catalog-controller-manager  1        2s

==> v1/Pod(related)
NAME                                                 READY  STATUS             RESTARTS  AGE
catalog-catalog-apiserver-5fd69cccbd-s22f6           0/2    ContainerCreating  0         1s
catalog-catalog-controller-manager-6497b4bb67-lxwck  0/1    ContainerCreating  0         1s

==> v1/Secret
NAME                            TYPE    DATA  AGE
catalog-catalog-apiserver-cert  Opaque  2     2s

==> v1/Service
NAME                       TYPE      CLUSTER-IP   EXTERNAL-IP  PORT(S)        AGE
catalog-catalog-apiserver  NodePort  10.0.149.23  <none>       443:30443/TCP  2s

==> v1beta1/Deployment
NAME                                DESIRED  CURRENT  UP-TO-DATE  AVAILABLE  AGE
catalog-catalog-apiserver           1        1        1           0          2s
catalog-catalog-controller-manager  1        1        1           0          2s
```

After installed the above "Service Catalog", please confirm API server? Then you can see the additional server as ***v1beta1.servicecatalog.k8s.io***.


```
$ kubectl get apiservice
NAME                                AGE
v1.                                 47d
v1.authentication.k8s.io            47d
v1.authorization.k8s.io             47d
v1.autoscaling                      47d
v1.batch                            47d
v1.networking.k8s.io                47d
v1.rbac.authorization.k8s.io        1d
v1.storage.k8s.io                   47d
v1alpha2.config.istio.io            47d
v1beta1.apiextensions.k8s.io        47d
v1beta1.apps                        47d
v1beta1.authentication.k8s.io       47d
v1beta1.authorization.k8s.io        47d
v1beta1.batch                       1d
v1beta1.certificates.k8s.io         47d
v1beta1.extensions                  47d
v1beta1.policy                      47d
v1beta1.rbac.authorization.k8s.io   47d
v1beta1.servicecatalog.k8s.io       2m
v1beta1.storage.k8s.io              47d
v1beta2.apps                        1d
v2beta1.autoscaling                 1d
```

In order to install Open Service Broker for Azure (OSBA), please add aditional repository as follows?

```
$ helm repo add azure https://kubernetescharts.blob.core.windows.net/azure
"azure" has been added to your repositories
```

In order to install the OSBA, please execute following command? Then you can configure the needed data to environment values.

```
SERVICE_PRINCIPAL=$(az ad sp create-for-rbac)
AZURE_CLIENT_ID=$(echo $SERVICE_PRINCIPAL | cut -d '"' -f 4)
AZURE_CLIENT_SECRET=$(echo $SERVICE_PRINCIPAL | cut -d '"' -f 16)
AZURE_TENANT_ID=$(echo $SERVICE_PRINCIPAL | cut -d '"' -f 20)
AZURE_SUBSCRIPTION_ID=$(az account show --query id --output tsv)
```

After executed the above please confirm the individul environment value as follows.

```
$ echo $SERVICE_PRINCIPAL
{ "appId": "64024bb9-****-****-****-9165e1060b16", "displayName": "azure-cli-2018-02-28-06-52-12", "name": "http://azure-cli-2018-02-28-06-52-12", "password": "ed80a0eb-****-****-****-afdc9998685f", "tenant": "72f988bf-****-****-****-2d7cd011db47" }

$ echo $AZURE_CLIENT_ID
64024bb9-****-****-****-9165e1060b16
$ echo $AZURE_CLIENT_SECRET
ed80a0eb-****-****-****-afdc9998685f
$ echo $AZURE_TENANT_ID
72f988bf-****-****-****-2d7cd011db47
$ echo $AZURE_SUBSCRIPTION_ID
f77aafe8-****-****-****-d0c37687ef70
```

Then you can install the OSBA by using following command.

```
$ helm install azure/open-service-broker-azure \
  --name osba --namespace osba \
  --set azure.subscriptionId=$AZURE_SUBSCRIPTION_ID \
  --set azure.tenantId=$AZURE_TENANT_ID \
  --set azure.clientId=$AZURE_CLIENT_ID \
  --set azure.clientSecret=$AZURE_CLIENT_SECRET
NAME:   osba
LAST DEPLOYED: Wed Feb 28 15:55:31 2018
NAMESPACE: osba
STATUS: DEPLOYED

RESOURCES:
==> v1beta1/Deployment
NAME                            DESIRED  CURRENT  UP-TO-DATE  AVAILABLE  AGE
osba-redis                      1        1        1           0          2s
osba-open-service-broker-azure  1        1        1           0          2s

==> v1beta1/ClusterServiceBroker
NAME  AGE
osba  2s

==> v1/Pod(related)
NAME                                             READY  STATUS             RESTARTS  AGE
osba-redis-59656f567c-95vjh                      0/1    Pending            0         2s
osba-open-service-broker-azure-5bd49895d6-tb69c  0/1    ContainerCreating  0         2s

==> v1/Secret
NAME                                 TYPE    DATA  AGE
osba-redis                           Opaque  1     2s
osba-open-service-broker-azure-auth  Opaque  2     2s
osba-open-service-broker-azure       Opaque  4     2s

==> v1/PersistentVolumeClaim
NAME        STATUS   VOLUME   CAPACITY  ACCESS MODES  STORAGECLASS  AGE
osba-redis  Pending  default  2s

==> v1/Service
NAME                            TYPE       CLUSTER-IP   EXTERNAL-IP  PORT(S)   AGE
osba-redis                      ClusterIP  10.0.50.239  <none>       6379/TCP  2s
osba-open-service-broker-azure  ClusterIP  10.0.78.15   <none>       80/TCP    2s


NOTES:
```

After installed the OSBA, in order to manage the OSBA and Service Catalog, please get the "svcat" command?

```
$ curl -sLO https://servicecatalogcli.blob.core.windows.net/cli/latest/$(uname -s)/$(uname -m)/svcat
$ chmod +x ./svcat
$ mv svcat /usr/local/bin/
```

If you failed the above command, you will be able to get the binary from following.

```
MacOS: https://servicecatalogcli.blob.core.windows.net/cli/latest/Darwin/x86_64/svcat
Windows: https://servicecatalogcli.blob.core.windows.net/cli/latest/Windows/x86_64/svcat.exe
Linux: https://servicecatalogcli.blob.core.windows.net/cli/latest/Linux/x86_64/svcat
```

After got the svcat command, please list the installed service brokers?

```
$ svcat get brokers
  NAME                               URL                                STATUS  
+------+--------------------------------------------------------------+--------+
  osba   http://osba-open-service-broker-azure.osba.svc.cluster.local   Ready   
```

After that, please confirm the classes which shows the available service.

```
$ svcat get classes
            NAME                      DESCRIPTION                             UUID                  
+--------------------------+--------------------------------+--------------------------------------+
  azure-rediscache           Azure Redis Cache                0346088a-****-****-****-f18e295ec1d9  
                             (Experimental)                                                         
  azure-sqldb-db-only        Azure SQL Database Only          2bbc160c-****-****-****-4c0a4822d0aa  
                             (Experimental)                                                         
  azure-storage              Azure Storage (Experimental)     2e2fc314-****-****-****-8f9ee8b33fea  
  azure-aci                  Azure Container Instance         451d5d19-****-****-****-116f705ecc95  
                             (Experimental)                                                         
  azure-cosmos-document-db   Azure DocumentDB                 6330de6f-****-****-****-b99f44d183e6  
                             (Experimental) provided by                                             
                             CosmosDB and accessible via                                            
                             SQL (DocumentDB), Gremlin                                              
                             (Graph), and Table (Key-Value)                                         
                             APIs                                                                   
  azure-servicebus           Azure Service Bus                6dc44338-****-****-****-5b1b3c5462d3  
                             (Experimental)                                                         
  azure-eventhubs            Azure Event Hubs                 7bade660-****-****-****-d416d975170b  
                             (Experimental)                                                         
  azure-cosmos-mongo-db      MongoDB on Azure                 8797a079-****-****-****-b7d5ea5c0e3a  
                             (Experimental) provided by                                             
                             CosmosDB                                                               
  azure-mysqldb              Azure Database for MySQL         997b8372-****-****-****-758b4a5075a5  
                             (Experimental)                                                         
  azure-sqldb-vm-only        Azure SQL Server VM              a7454e0e-****-****-****-8c4278117525  
                             (Experimental)                                                         
  azure-postgresqldb         Azure Database for PostgreSQL    b43b4bba-****-****-****-17dc5cee0175  
                             (Experimental)                                                         
  azuresearch                Azure Search (Experimental)      c54902aa-****-****-****-5b3d3b452f7f  
  azure-keyvault             Azure Key Vault (Experimental)   d90c881e-****-****-****-fcfe87e03276  
  azure-sqldb                Azure SQL Database               fb9bc99e-****-****-****-000d3a002ed5  
                             (Experimental)                                                         
```

You can also confirm the available service plan as follows. You can install  with the appropriate plan.


```
$ svcat get plans
               NAME                          CLASS                      DESCRIPTION                             UUID                  
+---------------------------------+--------------------------+--------------------------------+--------------------------------------+
  premium                           azure-rediscache           Premium Tier, 6GB Cache          b1057a8f-****-****-****-e168d5c04cf0  
  basic                             azure-rediscache           Basic Tier, 250MB Cache          362b3d1b-****-****-****-4a15a760c29c  
  standard                          azure-rediscache           Standard Tier, 1GB Cache         4af8bbd1-****-****-****-f72d1d959d87  
  premium-p4                        azure-sqldb-db-only        PremiumP4 Tier, 500 DTUs,        feb25d68-****-****-****-28a747bc2c2e  
                                                               500GB, 35 days point-in-time                                           
                                                               restore                                                                
  premium-p6                        azure-sqldb-db-only        PremiumP6 Tier, 1000 DTUs,       19487202-****-****-****-7bbf1486dbca  
                                                               500GB, 35 days point-in-time                                           
                                                               restore                                                                
  standard-s0                       azure-sqldb-db-only        Standard Tier, 10 DTUs, 250GB,   9d36b6b3-****-****-****-5cc13b785409  
                                                               35 days point-in-time restore                                          
  premium-p1                        azure-sqldb-db-only        PremiumP1 Tier, 125 DTUs,        220e922a-****-****-****-fe45a32bbf31  
                                                               500GB, 35 days point-in-time                                           
                                                               restore                                                                
  premium-p2                        azure-sqldb-db-only        PremiumP2 Tier, 250 DTUs,        e7eb13df-****-****-****-00dd0db1c85d  
                                                               500GB, 35 days point-in-time                                           
                                                               restore                                                                
  basic                             azure-sqldb-db-only        Basic Tier, 5 DTUs, 2GB, 7       8fa8d759-****-****-****-b93482ddc04a  
                                                               days point-in-time restore                                             
  data-warehouse-100                azure-sqldb-db-only        DataWarehouse100 Tier, 100       7a466f47-****-****-****-c5cbe724b874  
                                                               DWUs, 1024GB                                                           
  standard-s1                       azure-sqldb-db-only        StandardS1 Tier, 20 DTUs,        01c397f8-****-****-****-654cd8cae5fd  
                                                               250GB, 35 days point-in-time                                           
                                                               restore                                                                
  data-warehouse-1200               azure-sqldb-db-only        DataWarehouse1200 Tier, 1200     2717d839-****-****-****-47adf0e6ff15  
                                                               DWUs, 1024GB                                                           
  standard-s3                       azure-sqldb-db-only        StandardS3 Tier, 100 DTUs,       624828a9-****-****-****-ea41cfc75853  
                                                               250GB, 35 days point-in-time                                           
                                                               restore                                                                
  standard-s2                       azure-sqldb-db-only        StandardS2 Tier, 50 DTUs,        9cd114a0-****-****-****-2e685e5a29f3  
                                                               250GB, 35 days point-in-time                                           
                                                               restore                                                                
  premium-p11                       azure-sqldb-db-only        PremiumP11 Tier, 1750 DTUs,      a561c45a-****-****-****-411c1d7035da  
                                                               1024GB, 35 days point-in-time                                          
                                                               restore                                                                
  blob-container                    azure-storage              A specialized Azure storage      189d3b8f-****-****-****-03d069237f70  
                                                               account for storing block                                              
                                                               blobs and append blobs;                                                
                                                               automatically provisions a                                             
                                                               blob container  within the                                             
                                                               account                                                                
  blob-storage-account              azure-storage              Specialized Azure storage        800a17e1-****-****-****-20516052f647  
                                                               account for storing block                                              
                                                               blobs and append blobs; create                                         
                                                               your own blob containers                                               
                                                               within this account                                                    
  general-purpose-storage-account   azure-storage              Azure general-purpose storage    6ddf6b41-****-****-****-8ecc4896b3cf  
                                                               account; create your own                                               
                                                               containers, files, and tables                                          
                                                               within this account                                                    
  aci                               azure-aci                  Azure Container Instances        d48798e2-****-****-****-aa6f0ff08f6c  
  document-db                       azure-cosmos-document-db   Azure DocumentDB provided by     71168d1a-****-****-****-214dd3d6f8eb  
                                                               CosmosDB and accessible via                                            
                                                               SQL (DocumentDB), Gremlin                                              
                                                               (Graph), and Table (Key-Value)                                         
                                                               APIs                                                                   
  standard                          azure-servicebus           Standard Tier, Shared            6be0d8b5-****-****-****-a131425d3835  
                                                               Capacity, Topics, 12.5M                                                
                                                               Messaging Operations/Month,                                            
                                                               Variable Pricing                                                       
  basic                             azure-servicebus           Basic Tier, Shared Capacity      d06817b1-****-****-****-14b1d060206a  
  premium                           azure-servicebus           Premium Tier, Dedicated          cec378a7-****-****-****-d34898edbadc  
                                                               Capacity, Recommended For                                              
                                                               Production Workloads, Fixed                                            
                                                               Pricing                                                                
  standard                          azure-eventhubs            Standard Tier, 20 Consumer       264ab981-****-****-****-2d0fe3e80565  
                                                               groups, 1000 Brokered                                                  
                                                               connections, Additional                                                
                                                               Storage, Publisher Policies                                            
  basic                             azure-eventhubs            Basic Tier, 1 Consumer group,    80756db5-****-****-****-62cf7d196a3c  
                                                               100 Brokered connections                                               
  mongo-db                          azure-cosmos-mongo-db      MongoDB                          86fdda05-****-****-****-1325928e7b02  
  basic50                           azure-mysqldb              Basic Tier, 50 DTUs.             427559f1-****-****-****-32374a3e58aa  
  standard400                       azure-mysqldb              Standard Tier, 400 DTUs          ae3cd3dd-****-****-****-62c3b130944e  
  standard800                       azure-mysqldb              Standard Tier, 800 DTUs          08e4b43a-****-****-****-8202b13e339c  
  standard100                       azure-mysqldb              Standard Tier, 100 DTUs          edc2badc-****-****-****-da2f1c8c3e1c  
  basic100                          azure-mysqldb              Basic Tier, 100 DTUs             1a538e06-****-****-****-966cbf85bf36  
  standard200                       azure-mysqldb              Standard Tier, 200 DTUs          9995c891-****-****-****-83595c1f443f  
  sqldb-vm-only                     azure-sqldb-vm-only        Azure SQL Server VM Only         24f0f42e-****-****-****-b943b2c48eee  
  basic100                          azure-postgresqldb         Basic Tier, 100 DTUs             843d7d03-****-****-****-25ccc4ac30d7  
  basic50                           azure-postgresqldb         Basic Tier, 50 DTUs              b2ed210f-****-****-****-964e6b6fad62  
  standard-s1                       azuresearch                S1 Tier. Max 50 Indexes, 25GB    65e89af2-****-****-****-8dd2dd8fdd40  
                                                               Storage/Partition                                                      
  free                              azuresearch                Free Tier. Max 3 Indexes, 50MB   35bd6e80-****-****-****-338aee9321e4  
                                                               Storage/Partition                                                      
  basic                             azuresearch                Basic Tier. Max 5 Indexes, 2GB   4a50e008-****-****-****-d8b3ad43f7eb  
                                                               Storage/Partition                                                      
  premium                           azure-keyvault             Premium Tier                     6893b1de-****-****-****-1636c4b81f75  
  standard                          azure-keyvault             Standard Tier                    3577ee4a-****-****-****-9d33d52ef486  
  data-warehouse-100                azure-sqldb                DataWarehouse100 Tier, 100       b69af389-****-****-****-c1ffdc2620d9  
                                                               DWUs, 1024GB                                                           
  premium-p6                        azure-sqldb                PremiumP6 Tier, 1000 DTUs,       af3dc76f-****-****-****-a9e756640a57  
                                                               500GB, 35 days point-in-time                                           
                                                               restore                                                                
  standard-s3                       azure-sqldb                StandardS3 Tier, 100 DTUs,       26cf84bf-****-****-****-cbfa9c319d5f  
                                                               250GB, 35 days point-in-time                                           
                                                               restore                                                                
  premium-p2                        azure-sqldb                PremiumP2 Tier, 250 DTUs,        2bbbcc59-****-****-****-2833cb417d43  
                                                               500GB, 35 days point-in-time                                           
                                                               restore                                                                
  premium-p4                        azure-sqldb                PremiumP4 Tier, 500 DTUs,        85d54d69-****-****-****-66bc96ecf9e7  
                                                               500GB, 35 days point-in-time                                           
                                                               restore                                                                
  standard-s1                       azure-sqldb                StandardS1 Tier, 20 DTUs,        17725188-****-****-****-49f146766eeb  
                                                               250GB, 35 days point-in-time                                           
                                                               restore                                                                
  standard-s2                       azure-sqldb                StandardS2 Tier, 50 DTUs,        a5537f8e-****-****-****-a13811944bdd  
                                                               250GB, 35 days point-in-time                                           
                                                               restore                                                                
  data-warehouse-1200               azure-sqldb                DataWarehouse1200 Tier, 1200     470a869b-****-****-****-10ca0ea488df  
                                                               DWUs, 1024GB                                                           
  premium-p11                       azure-sqldb                PremiumP11 Tier, 1750 DTUs,      408f5f35-****-****-****-9e10c1abc4e5  
                                                               1024GB, 35 days point-in-time                                          
                                                               restore                                                                
  basic                             azure-sqldb                Basic Tier, 5 DTUs, 2GB, 7       3819fdfa-****-****-****-000d3a002ed5  
                                                               days point-in-time restore                                             
  premium-p1                        azure-sqldb                PremiumP1 Tier, 125 DTUs,        f9a3cc8e-****-****-****-9837ea3dfcaa  
                                                               500GB, 35 days point-in-time                                           
                                                               restore                                                                
  standard-s0                       azure-sqldb                Standard Tier, 10 DTUs, 250GB,   2497b7f3-****-****-****-d4a48c005e19  
                                                               35 days point-in-time restore                                          
```

Then you got all of the informations to install the managed service.  

For example:  
If you would like to create managed MySQL instance from the "Service Catalog", you can execute following command.

```
$ svcat provision mysql-instance-for-jsr --class azure-mysqldb \
 --plan standard800 -p location=eastus \
 -p resourceGroup=MC_MCACS-AKS_esakscluster_eastus 
 -p sslEnforcement=disabled -p firewallStartIPAddress=0.0.0.0 \
 -p firewallEndIPAddress=255.255.255.255 -n order-system-production
```

For OSBA, it provide some sample Helm Chart like WordPress with MySQL. So please install the sample Helm Chart with managed MySQL by using following command?

[Refer to: install the WordPress Helm Charts for Kubernetes Service Catalog on Azure](https://github.com/Azure/helm-charts/tree/master/wordpress)

```
$ helm install azure/wordpress --name wordpress \
  --namespace wordpress --set resources.requests.cpu=0
NAME:   wordpress
LAST DEPLOYED: Wed Feb 28 16:11:01 2018
NAMESPACE: wordpress
STATUS: DEPLOYED

RESOURCES:
==> v1beta1/ServiceInstance
NAME                                AGE
wordpress-wordpress-mysql-instance  2s

==> v1/Pod(related)
NAME                                  READY  STATUS   RESTARTS  AGE
wordpress-wordpress-557796bfb9-9c6br  0/1    Pending  0         2s

==> v1/Secret
NAME                 TYPE    DATA  AGE
wordpress-wordpress  Opaque  2     2s

==> v1/PersistentVolumeClaim
NAME                 STATUS   VOLUME   CAPACITY  ACCESS MODES  STORAGECLASS  AGE
wordpress-wordpress  Pending  default  2s

==> v1/Service
NAME                 TYPE          CLUSTER-IP  EXTERNAL-IP  PORT(S)                     AGE
wordpress-wordpress  LoadBalancer  10.0.42.80  <pending>    80:31171/TCP,443:31432/TCP  2s

==> v1beta1/Deployment
NAME                 DESIRED  CURRENT  UP-TO-DATE  AVAILABLE  AGE
wordpress-wordpress  1        1        1           0          2s

==> v1beta1/ServiceBinding
NAME                               AGE
wordpress-wordpress-mysql-binding  2s


NOTES:
1. Get the WordPress URL:

  NOTE: It may take a few minutes for the LoadBalancer IP to be available.
        Watch the status with: 'kubectl get svc --namespace wordpress -w wordpress-wordpress'

  export SERVICE_IP=$(kubectl get svc --namespace wordpress wordpress-wordpress -o jsonpath='{.status.loadBalancer.ingress[0].ip}')
  echo http://$SERVICE_IP/admin

2. Login with the following credentials to see your blog

  echo Username: user
  echo Password: $(kubectl get secret --namespace wordpress wordpress-wordpress -o jsonpath="{.data.wordpress-password}" | base64 --decode)
```

After executed the above command, you can confirm that the managed MySQL instance was created as follow.

```
$ svcat get instances -n wordpress
                 NAME                  NAMESPACE       CLASS          PLAN       STATUS  
+------------------------------------+-----------+---------------+-------------+--------+
  wordpress-wordpress-mysql-instance   wordpress   azure-mysqldb   standard100   Ready   
```

And you can confirm the binding service as follows. Please note that it will take a time to become Ready. About few minutes, please wait a moment?

```
$ svcat get bindings -n wordpress
                NAME                  NAMESPACE                INSTANCE                STATUS  
+-----------------------------------+-----------+------------------------------------+--------+
  wordpress-wordpress-mysql-binding   wordpress   wordpress-wordpress-mysql-instance   Ready   
```

After binded the service, you can confirm the detail information for instance as follows.

```
$ svcat describe instance wordpress-wordpress-mysql-instance --traverse -n wordpress
  Name:        wordpress-wordpress-mysql-instance                                                 
  Namespace:   wordpress                                                                          
  Status:      Ready - The instance was provisioned successfully @ 2018-02-28 07:15:16 +0000 UTC  
  Class:       azure-mysqldb                                                                      
  Plan:        standard100                                                                        

Bindings:
                NAME                  STATUS  
+-----------------------------------+--------+
  wordpress-wordpress-mysql-binding   Ready   

Class:
  Name:     azure-mysqldb                         
  UUID:     997b8372-****-****-****-758b4a5075a5  
  Status:   Active                                

Plan:
  Name:     standard100                           
  UUID:     edc2badc-****-****-****-da2f1c8c3e1c  
  Status:   Active                                

Broker:
  Name:     osba   
  Status:   Ready  
```

Also you can confirm the detail information for pod as follows.

```
$ kubectl describe po wordpress-wordpress-557796bfb9-9c6br -n wordpress
Name:           wordpress-wordpress-557796bfb9-9c6br
Namespace:      wordpress
Node:           aks-nodepool1-19381275-5/10.240.0.9
Start Time:     Wed, 28 Feb 2018 16:12:05 +0900
Labels:         app=wordpress-wordpress
                pod-template-hash=1133526965
Annotations:    kubernetes.io/created-by={"kind":"SerializedReference","apiVersion":"v1","reference":{"kind":"ReplicaSet","namespace":"wordpress","name":"wordpress-wordpress-557796bfb9","uid":"8914fd8f-1c56-11e8-aa36...
Status:         Running
IP:             10.244.5.24
Controlled By:  ReplicaSet/wordpress-wordpress-557796bfb9
Containers:
  wordpress-wordpress:
    Container ID:   docker://04891fa6240a47540d5874d8b1fd6a5725f52bf9927019375ffdba2d0ca68c7c
    Image:          bitnami/wordpress:4.9.2-r0
    Image ID:       docker-pullable://bitnami/wordpress@sha256:5e291e49aee4e1a1b5e535aa480e7275c8a3f9f834f677de628ad34ca9c2559b
    Ports:          80/TCP, 443/TCP
    State:          Running
      Started:      Wed, 28 Feb 2018 16:15:19 +0900
    Ready:          True
    Restart Count:  0
    Requests:
      cpu:      0
      memory:   512Mi
    Liveness:   http-get http://:http/wp-login.php delay=120s timeout=5s period=10s #success=1 #failure=6
    Readiness:  http-get http://:http/wp-login.php delay=30s timeout=5s period=10s #success=1 #failure=6
    Environment:
      MARIADB_HOST:                 <set to the key 'host' in secret 'wordpress-wordpress-mysql-secret'>      Optional: false
      MARIADB_PORT_NUMBER:          <set to the key 'port' in secret 'wordpress-wordpress-mysql-secret'>      Optional: false
      WORDPRESS_DATABASE_NAME:      <set to the key 'database' in secret 'wordpress-wordpress-mysql-secret'>  Optional: false
      WORDPRESS_DATABASE_USER:      <set to the key 'username' in secret 'wordpress-wordpress-mysql-secret'>  Optional: false
      WORDPRESS_DATABASE_PASSWORD:  <set to the key 'password' in secret 'wordpress-wordpress-mysql-secret'>  Optional: false
      WORDPRESS_USERNAME:           user
      WORDPRESS_PASSWORD:           <set to the key 'wordpress-password' in secret 'wordpress-wordpress'>  Optional: false
      WORDPRESS_EMAIL:              user@example.com
      WORDPRESS_FIRST_NAME:         FirstName
      WORDPRESS_LAST_NAME:          LastName
      WORDPRESS_BLOG_NAME:          User's Blog!
      SMTP_HOST:                    
      SMTP_PORT:                    
      SMTP_USER:                    
      SMTP_PASSWORD:                <set to the key 'smtp-password' in secret 'wordpress-wordpress'>  Optional: false
      SMTP_USERNAME:                
      SMTP_PROTOCOL:                
    Mounts:
      /bitnami/apache from wordpress-data (rw)
      /bitnami/php from wordpress-data (rw)
      /bitnami/wordpress from wordpress-data (rw)
      /var/run/secrets/kubernetes.io/serviceaccount from default-token-jvdq2 (ro)
Conditions:
  Type           Status
  Initialized    True 
  Ready          True 
  PodScheduled   True 
Volumes:
  wordpress-data:
    Type:       PersistentVolumeClaim (a reference to a PersistentVolumeClaim in the same namespace)
    ClaimName:  wordpress-wordpress
    ReadOnly:   false
  default-token-jvdq2:
    Type:        Secret (a volume populated by a Secret)
    SecretName:  default-token-jvdq2
    Optional:    false
QoS Class:       Burstable
Node-Selectors:  <none>
Tolerations:     node.alpha.kubernetes.io/notReady:NoExecute for 300s
                 node.alpha.kubernetes.io/unreachable:NoExecute for 300s
Events:
  Type     Reason                 Age                From                               Message
  ----     ------                 ----               ----                               -------
  Warning  FailedScheduling       10m (x7 over 11m)  default-scheduler                  PersistentVolumeClaim is not bound: "wordpress-wordpress" (repeated 5 times)
  Normal   Scheduled              10m                default-scheduler                  Successfully assigned wordpress-wordpress-557796bfb9-9c6br to aks-nodepool1-19381275-5
  Normal   SuccessfulMountVolume  10m                kubelet, aks-nodepool1-19381275-5  MountVolume.SetUp succeeded for volume "default-token-jvdq2"
  Normal   SuccessfulMountVolume  9m                 kubelet, aks-nodepool1-19381275-5  MountVolume.SetUp succeeded for volume "pvc-8903f50f-1c56-11e8-aa36-0a58ac1f25b8"
  Normal   Pulling                9m                 kubelet, aks-nodepool1-19381275-5  pulling image "bitnami/wordpress:4.9.2-r0"
  Normal   Pulled                 8m                 kubelet, aks-nodepool1-19381275-5  Successfully pulled image "bitnami/wordpress:4.9.2-r0"
  Warning  Failed                 7m (x7 over 8m)    kubelet, aks-nodepool1-19381275-5  Error: secrets "wordpress-wordpress-mysql-secret" not found
  Warning  FailedSync             7m (x7 over 8m)    kubelet, aks-nodepool1-19381275-5  Error syncing pod
  Normal   Pulled                 7m (x7 over 8m)    kubelet, aks-nodepool1-19381275-5  Container image "bitnami/wordpress:4.9.2-r0" already present on machine
  Warning  Unhealthy              5m (x9 over 6m)    kubelet, aks-nodepool1-19381275-5  Readiness probe failed: Get http://10.244.5.24:80/wp-login.php: dial tcp 10.244.5.24:80: getsockopt: connection refused
$ kubectl get po -n wordpress
NAME                                   READY     STATUS    RESTARTS   AGE
wordpress-wordpress-557796bfb9-9c6br   1/1       Running   0          11m
```

According to the above, some environment value was configured on secret as wordpress-wordpress-mysql-secret.  
***set to the key 'host' in secret 'wordpress-wordpress-mysql-secret'***

So please confirm the secret? Then you can confirm the access informations for MySQL as follows.

```
$ kubectl get secret wordpress-wordpress-mysql-secret -n wordpress -o yaml
apiVersion: v1
data:
  database: eTh********obw==
  host: ZjU1ZjRj*****************************************************RhdGFiYXNlLmF6dXJlLmNvbQ==
  password: d1Y***************hJbw==
  port: MzMwNg==
  username: dWJveXhy************************************************DMzZDk=
kind: Secret
metadata:
  creationTimestamp: 2018-02-28T07:15:18Z
  name: wordpress-wordpress-mysql-secret
  namespace: wordpress
  ownerReferences:
  - apiVersion: servicecatalog.k8s.io/v1beta1
    blockOwnerDeletion: true
    controller: true
    kind: ServiceBinding
    name: wordpress-wordpress-mysql-binding
    uid: 891b69d8-****-****-****-0a580af40915
  resourceVersion: "6995100"
  selfLink: /api/v1/namespaces/wordpress/secrets/wordpress-wordpress-mysql-secret
  uid: 21b6c697-****-****-****-0a58ac1f25b8
type: Opaque

```

The access information was stored on secret. In fact, in order to access the MySQL, please get the actual value not base64 encode as follows.

```
# Host name check
$ echo "ZjU1ZjRj*****************************************************RhdGFiYXNlLmF6dXJlLmNvbQ=="|base64 --decode
f55f4c08-****-****-****-ed2cb52833d9.mysql.database.azure.com

# DB Name Check
$ echo "eTh********obw=="|base64 --decode
y8********ho

# User login name Check
$ echo "dWJveXhy************************************************DMzZDk="|base64 --decode
foo-bar@f55f4c08-****-****-****-ed2cb52833d9$ 

# Password Check
$ echo "d1Y***************hJbw=="|base64 --decode
wV**********j8Io

# Port Number check
$ echo "MzMwNg=="|base64 --decode
3306
```


You can get the Service Insance by using following command.

ServiceInstance: A provisioned instance of a ClusterServiceClass. These are created by cluster operators to make a specific instance of a managed service available for use by one or more in-cluster applications. When a new ServiceInstance resource is created, the Service Catalog controller connects to the appropriate service broker and instruct it to provision the service instance.

```
$ kubectl get serviceinstance -n wordpress wordpress-wordpress-mysql-instance  -o yaml
apiVersion: servicecatalog.k8s.io/v1beta1
kind: ServiceInstance
metadata:
  creationTimestamp: 2018-02-28T07:11:02Z
  finalizers:
  - kubernetes-incubator/service-catalog
  generation: 1
  labels:
    app: wordpress-wordpress
    chart: wordpress-0.8.0
    heritage: Tiller
    release: wordpress
  name: wordpress-wordpress-mysql-instance
  namespace: wordpress
  resourceVersion: "85"
  selfLink: /apis/servicecatalog.k8s.io/v1beta1/namespaces/wordpress/serviceinstances/wordpress-wordpress-mysql-instance
  uid: 892ce073-****-****-****-0a580af40915
spec:
  clusterServiceClassExternalName: azure-mysqldb
  clusterServiceClassRef:
    name: 997b8372-****-****-****-758b4a5075a5
  clusterServicePlanExternalName: standard100
  clusterServicePlanRef:
    name: edc2badc-****-****-****-da2f1c8c3e1c
  externalID: 90ad5ea0-****-****-****-7094fbfc9735
  parameters:
    firewallEndIPAddress: 255.255.255.255
    firewallStartIPAddress: 0.0.0.0
    location: eastus
    resourceGroup: wordpress
    sslEnforcement: disabled
  updateRequests: 0
status:
  asyncOpInProgress: false
  conditions:
  - lastTransitionTime: 2018-02-28T07:15:16Z
    message: The instance was provisioned successfully
    reason: ProvisionedSuccessfully
    status: "True"
    type: Ready
  deprovisionStatus: Required
  externalProperties:
    clusterServicePlanExternalID: edc2badc-****-****-****-da2f1c8c3e1c
    clusterServicePlanExternalName: standard100
    parameterChecksum: b7763780b8248e*********************341162786f23ddcf97f5
    parameters:
      firewallEndIPAddress: 255.255.255.255
      firewallStartIPAddress: 0.0.0.0
      location: eastus
      resourceGroup: wordpress
      sslEnforcement: disabled
  orphanMitigationInProgress: false
  reconciledGeneration: 1
```


You can get the "Service Binding" for MySQL.  

ServiceBinding: Access credentials to a ServiceInstance. These are created by cluster operators who want their applications to make use of a ServiceInstance. Upon creation, the Service Catalog controller creates a Kubernetes Secret containing connection details and credentials for the Service Instance, which can be mounted into Pods.


```
$ kubectl get servicebinding wordpress-wordpress-mysql-binding -n wordpress -o yaml
apiVersion: servicecatalog.k8s.io/v1beta1
kind: ServiceBinding
metadata:
  creationTimestamp: 2018-02-28T07:11:02Z
  finalizers:
  - kubernetes-incubator/service-catalog
  generation: 1
  labels:
    app: wordpress-wordpress
    chart: wordpress-0.8.0
    heritage: Tiller
    release: wordpress
  name: wordpress-wordpress-mysql-binding
  namespace: wordpress
  resourceVersion: "87"
  selfLink: /apis/servicecatalog.k8s.io/v1beta1/namespaces/wordpress/servicebindings/wordpress-wordpress-mysql-binding
  uid: 891b69d8-****-****-****-0a580af40915
spec:
  externalID: e36cf0b9-****-****-bc6a-b3f298db40d4
  instanceRef:
    name: wordpress-wordpress-mysql-instance
  secretName: wordpress-wordpress-mysql-secret
status:
  asyncOpInProgress: false
  conditions:
  - lastTransitionTime: 2018-02-28T07:15:18Z
    message: Injected bind result
    reason: InjectedBindResult
    status: "True"
    type: Ready
  externalProperties: {}
  orphanMitigationInProgress: false
  reconciledGeneration: 1
  unbindStatus: Required
```

### [8.4.6 Azure Log Analytics : Container Monitoring Solution]()



### [8.4.7 Clair (Static analysis of vulnerabilities)](https://github.com/coreos/clair)

[Clair](https://github.com/coreos/clair) is an open source project for the static analysis of vulnerabilities in application containers (currently including appc and docker).

1. In regular intervals, Clair ingests vulnerability metadata from a configured set of sources and stores it in the database.

2. Clients use the Clair API to index their container images; this creates a list of features present in the image and stores them in the database.

3. Clients use the Clair API to query the database for vulnerabilities of a particular image; correlating vulnerabilities and features is done for each request, avoiding the need to rescan images.

4. When updates to vulnerability metadata occur, a notification can be sent to alert systems that a change has occured.



---
[Previous Page](Kubernetes-Workshop7.md) / [Top Page](README.md)
