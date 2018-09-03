[Previous Page](Kubernetes-Workshop9.md) / [Top Page](README.md)
---
# 10. Kubernetes Java Client API Sample

This is the sample Application of [Kubernetes Java Client API (3.0.0-beta2)](https://github.com/kubernetes-client/java) sample.

At first, please install the k8s Java Client libraries by using following step?

```
git clone --recursive https://github.com/kubernetes-client/java
cd java
mvn install
```
After installed the k8s libraries, please add the dependency in your pom.xml file as follows?

```
<dependency>
    <groupId>io.kubernetes</groupId>
    <artifactId>client-java</artifactId>
    <version>3.0.0-beta2-SNAPSHOT</version>
    <scope>compile</scope>
</dependency>
<dependency>
    <groupId>io.kubernetes</groupId>
    <artifactId>client-java-api</artifactId>
    <version>3.0.0-beta2-SNAPSHOT</version>
    <scope>compile</scope>
</dependency>
```


I uploaded all of the code to [kubernetes-client-sample](kubernetes-client-sample). So please refer to it?


```
package com.yoshio3;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.apis.ExtensionsV1beta1Api;
import io.kubernetes.client.models.ExtensionsV1beta1Deployment;
import io.kubernetes.client.models.ExtensionsV1beta1DeploymentList;
import io.kubernetes.client.models.ExtensionsV1beta1DeploymentSpec;
import io.kubernetes.client.models.V1NamespaceList;
import io.kubernetes.client.models.V1PodList;
import io.kubernetes.client.models.V1ServiceList;
import io.kubernetes.client.util.Config;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * A simple example of how to use the Java API
 *
 * <p>
 * Easiest way to run this: mvn exec:java
 * -Dexec.mainClass="io.kubernetes.client.examples.Example"
 *
 * <p>
 * From inside $REPO_DIR/examples
 */
public class Example {

    private final CoreV1Api corev1Api;
    private final static String DEFAULT_NAME_SPACE = "default";

    /*
    For API_SERVER_NAME, you can get the server name as follows.
    $ kubectl cluster-info|grep master
    Kubernetes master is running at https://*****************.hcp.japaneast.azmk8s.io:443
    */
    private final static String API_SERVER_NAME = "https://*****************.hcp.japaneast.azmk8s.io";
    /*
    For ACCESS_TOKEN , you can get the token as follows
    $ kubectl describe secret $(kubectl get secrets | grep default | cut -f1 -d ' ') | grep -E '^token' | cut -f2 -d':' | tr -d '\t'
    */
    private static final String ACCESS_TOKEN = "********************************";

    private final static Logger LOGGER = Logger.getLogger(Example.class.getName());

    /**
     * Constructor
     */
    public Example() {
        ApiClient client = Config.fromToken(API_SERVER_NAME, ACCESS_TOKEN, false);
        Configuration.setDefaultApiClient(client);
        corev1Api = new CoreV1Api(client);
    }

    /**
     * Main method
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            Example operation = new Example();
            operation.executeCommand();
        } catch (ApiException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void executeCommand() throws ApiException {

        //ScaleUp/ScaleDown the Deployment pod
        System.out.println("----- Scale Deployment Start -----");
        scaleDeployment("account-service", 5);
        System.out.println("----- Scale Deployment End -----");

        //List all of the namaspaces and pods
        List<String> nameSpaces = getAllNameSapces();
        nameSpaces.stream().forEach(namespace -> {
            try {
                System.out.println("----- " + namespace + " -----");
                getNamespacedPod(namespace).stream().forEach(System.out::println);
            } catch (ApiException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });

        //Print all of the Services
        System.out.println("----- Print list all Services Start -----");
        List<String> services = getServices();
        services.stream().forEach(System.out::println);
        System.out.println("----- Print list all Services End -----");

        //Print log of specific pod
        System.out.println("----- Print Log of Specific Pod Start -----");
        printLog(DEFAULT_NAME_SPACE, "account-service-cbd44cc8-tq5hb");
        System.out.println("----- Print Log of Specific Pod End -----");
    }

    /**
     * Get all namespaces in k8s cluster
     *
     * @return
     * @throws ApiException
     */
    public List<String> getAllNameSapces() throws ApiException {
        V1NamespaceList listNamespace = corev1Api.listNamespace("true", null, null, Boolean.FALSE, null, Integer.MAX_VALUE, null, Integer.MAX_VALUE, Boolean.FALSE);
        List<String> list = listNamespace.getItems()
                .stream()
                .map(v1Namespace -> v1Namespace.getMetadata().getName())
                .collect(Collectors.toList());
        return list;
    }

    /**
     * List all pod names in all namespaces in k8s cluster
     *
     * @return
     * @throws ApiException
     */
    public List<String> getPods() throws ApiException {
        V1PodList v1podList = corev1Api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null);
        List<String> podList = v1podList.getItems()
                .stream()
                .map(v1Pod -> v1Pod.getMetadata().getName())
                .collect(Collectors.toList());
        return podList;
    }

    /**
     * List all pod in the default namespace
     *
     * @return
     * @throws ApiException
     */
    public List<String> getNamespacedPod() throws ApiException {
        return getNamespacedPod(DEFAULT_NAME_SPACE, null);
    }

    /**
     * List pod in specific namespace
     *
     * @param namespace
     * @return
     * @throws ApiException
     */
    public List<String> getNamespacedPod(String namespace) throws ApiException {
        return getNamespacedPod(namespace, null);
    }

    /**
     * List pod in specific namespace with label
     *
     * @param namespace
     * @param label
     * @return
     * @throws ApiException
     */
    public List<String> getNamespacedPod(String namespace, String label) throws ApiException {
        V1PodList listNamespacedPod = corev1Api.listNamespacedPod(namespace, null, null, null, Boolean.FALSE, label, Integer.MAX_VALUE, null, Integer.MAX_VALUE, Boolean.FALSE);
        List<String> listPods = listNamespacedPod.getItems()
                .stream()
                .map(v1pod -> v1pod.getMetadata().getName())
                .collect(Collectors.toList());
        return listPods;
    }

    /**
     * List all Services in default namespace
     *
     * @return
     * @throws ApiException
     */
    public List<String> getServices() throws ApiException {
        V1ServiceList listNamespacedService = corev1Api.listNamespacedService(DEFAULT_NAME_SPACE, null, null, null, Boolean.FALSE, null, Integer.MAX_VALUE, null, Integer.MAX_VALUE, Boolean.FALSE);
        return listNamespacedService.getItems().stream().map(v1service -> v1service.getMetadata().getName()).collect(Collectors.toList());
    }

    /**
     * Scale up/down the number of pod in Deployment
     *
     * @param deploymentName
     * @param numberOfReplicas
     * @throws ApiException
     */
    public void scaleDeployment(String deploymentName, int numberOfReplicas) throws ApiException {
        ExtensionsV1beta1Api extensionV1Api = new ExtensionsV1beta1Api();
        extensionV1Api.setApiClient(corev1Api.getApiClient());
        ExtensionsV1beta1DeploymentList listNamespacedDeployment = extensionV1Api.listNamespacedDeployment(DEFAULT_NAME_SPACE,null,null,null,Boolean.FALSE,null,null,null,null,Boolean.FALSE);

        List<ExtensionsV1beta1Deployment> extensionsV1beta1DeploymentItems = listNamespacedDeployment.getItems();
        Optional<ExtensionsV1beta1Deployment> findedDeployment = extensionsV1beta1DeploymentItems.stream()
                .filter((ExtensionsV1beta1Deployment deployment) -> deployment.getMetadata().getName().equals(deploymentName))
                .findFirst();
        findedDeployment.ifPresent((ExtensionsV1beta1Deployment deploy) -> {
            try {
                ExtensionsV1beta1DeploymentSpec newSpec = deploy.getSpec().replicas(numberOfReplicas);
                ExtensionsV1beta1Deployment newDeploy = deploy.spec(newSpec);
                extensionV1Api.replaceNamespacedDeployment(deploymentName, DEFAULT_NAME_SPACE, newDeploy, null);
            } catch (ApiException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    /**
     * Print out the Log for specific Pods
     *
     * @param namespace
     * @param podName
     * @throws ApiException
     */
    public void printLog(String namespace, String podName) throws ApiException {
        //https://github.com/kubernetes-client/java/blob/master/kubernetes/docs/CoreV1Api.md#readNamespacedPodLog
        String readNamespacedPodLog = corev1Api.readNamespacedPodLog(podName, namespace, null, Boolean.FALSE, Integer.MAX_VALUE, null, Boolean.FALSE, Integer.MAX_VALUE, 40, Boolean.FALSE);
        System.out.println(readNamespacedPodLog);
    }
}
```

If you executed the program, you will be able to see following result.

```
------------------------------------------------------------------------
Building client-java-examples 3.0.0-beta2-SNAPSHOT
------------------------------------------------------------------------

--- exec-maven-plugin:1.2.1:exec (default-cli) @ client-java-examples ---
----- Scale Deployment Start -----
----- Scale Deployment End -----
----- azure-system -----
----- default -----
account-service-cbd44cc8-44sq8
account-service-cbd44cc8-67bm6
account-service-cbd44cc8-cnsjw
account-service-cbd44cc8-dch2r
account-service-cbd44cc8-tq5hb
nginx-768979984b-cmmvg
ubuntu-69cd8cd996-lf4wh
----- istio-system -----
istio-citadel-7bdc7775c7-7qj7v
istio-cleanup-old-ca-cd4gt
istio-egressgateway-795fc9b47-84rlf
istio-ingress-84659cf44c-9lt4w
istio-ingressgateway-7d89dbf85f-4z5nr
istio-mixer-post-install-92r9x
istio-pilot-66f4dd866c-47jgr
istio-policy-76c8896799-55wdn
istio-statsd-prom-bridge-949999c4c-9m9xj
istio-telemetry-6554768879-btp24
prometheus-86cb6dd77c-g78bl
----- kube-public -----
----- kube-system -----
addon-http-application-routing-default-http-backend-b8f5bcpdgjb
addon-http-application-routing-external-dns-864b78b57f-lskpp
addon-http-application-routing-nginx-ingress-controller-86fnr6d
heapster-d7c9d9b7f-4sq79
kube-dns-v20-58bc8dcd9f-jhtsg
kube-dns-v20-58bc8dcd9f-w7vhw
kube-proxy-2hx7j
kube-proxy-55767
kube-proxy-9942b
kube-proxy-mf28z
kube-proxy-x5zpn
kube-svc-redirect-cgknw
kube-svc-redirect-cxfwk
kube-svc-redirect-d2krc
kube-svc-redirect-gpg4q
kube-svc-redirect-k57bz
kubernetes-dashboard-5495445b97-gbcfw
omsagent-8xps2
omsagent-crnq5
omsagent-kt6cg
omsagent-p8qsq
omsagent-rs-5cc6876c77-rtpzz
omsagent-shsww
tunnelfront-88c97f77c-q5fsr
----- Print list all Services Start -----
account-service
kubernetes
----- Print list all Services End -----
----- Print Log of Specific Pod Start -----
[2018-07-07T02:41:42.768+0000] [] [INFO] [] [org.glassfish.soteria.servlet.SamRegistrationInstaller] [tid: _ThreadID=1 _ThreadName=main] [timeMillis: 1530931302768] [levelValue: 800] Initializing Soteria 1.0 for context '/app'

[2018-07-07T02:41:42.865+0000] [] [WARN] [] [org.jboss.weld.Servlet] [tid: _ThreadID=1 _ThreadName=main] [timeMillis: 1530931302865] [levelValue: 900] WELD-000718: No EEModuleDescriptor defined for bean archive with ID: app.war. @Initialized and @Destroyed events for ApplicationScoped may be fired twice.

[2018-07-07T02:41:43.080+0000] [] [INFO] [] [] [tid: _ThreadID=1 _ThreadName=main] [timeMillis: 1530931303080] [levelValue: 800] Cannot find the resource bundle for the name com.sun.logging.enterprise.system.core.naming for class org.glassfish.concurrent.runtime.deployer.ConcurrentObjectFactory using fish.payara.micro.boot.loader.ExplodedURLClassloader@548e7350

[2018-07-07T02:41:43.084+0000] [] [INFO] [] [fish.payara.micro.cdi.extension.ClusteredCDIEventBusImpl] [tid: _ThreadID=1 _ThreadName=main] [timeMillis: 1530931303084] [levelValue: 800] Clustered CDI Event bus initialized for /app

[2018-07-07T02:41:44.574+0000] [] [INFO] [AS-WEB-GLUE-00172] [javax.enterprise.web] [tid: _ThreadID=1 _ThreadName=main] [timeMillis: 1530931304574] [levelValue: 800] Loading application [app] at [/app]

[2018-07-07T02:41:45.086+0000] [] [INFO] [] [javax.enterprise.system.core] [tid: _ThreadID=1 _ThreadName=main] [timeMillis: 1530931305086] [levelValue: 800] app was successfully deployed in 19,899 milliseconds.

[2018-07-07T02:41:45.093+0000] [] [INFO] [] [PayaraMicro] [tid: _ThreadID=1 _ThreadName=main] [timeMillis: 1530931305093] [levelValue: 800] Deployed 1 archive(s)

[2018-07-07T02:41:45.099+0000] [] [INFO] [] [PayaraMicro] [tid: _ThreadID=1 _ThreadName=main] [timeMillis: 1530931305099] [levelValue: 800] [[
  
Instance Configuration
Host: account-service-cbd44cc8-tq5hb
HTTP Port(s): 8080 
HTTPS Port(s): 
Instance Name: Poor-Scorpionfish
Instance Group: MicroShoal
Hazelcast Member UUID d72e0317-f2aa-4de0-9b27-7feaf5816c16
Deployed: app ( app war /app )

]]

[2018-07-07T02:41:45.121+0000] [] [INFO] [] [PayaraMicro] [tid: _ThreadID=1 _ThreadName=main] [timeMillis: 1530931305121] [levelValue: 800] [[
  
Payara Micro URLs
http://account-service-cbd44cc8-tq5hb:8080/app

'app' REST Endpoints
GET	/app/account/rest/hello
GET	/app/account/rest/listUsers

]]

[2018-07-07T02:41:45.128+0000] [] [INFO] [] [PayaraMicro] [tid: _ThreadID=1 _ThreadName=main] [timeMillis: 1530931305128] [levelValue: 800] Payara Micro  4.1.2.174 #badassmicrofish (build 192) ready in 29,390 (ms)


----- Print Log of Specific Pod End -----
------------------------------------------------------------------------
BUILD SUCCESS
------------------------------------------------------------------------
Total time: 5.438s
Finished at: Sat Sep 01 23:32:18 JST 2018
Final Memory: 14M/437M
------------------------------------------------------------------------
```

---
[Previous Page](Kubernetes-Workshop9.md) / [Top Page](README.md)
