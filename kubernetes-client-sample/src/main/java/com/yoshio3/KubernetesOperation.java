/*
Copyright 2017 The Kubernetes Authors.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
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

public class KubernetesOperation {

    private final CoreV1Api corev1Api;
    private final static String DEFAULT_NAME_SPACE = "default";

    /*
        $ kubectl cluster-info|grep master
         Kubernetes master is running at https://*****************.hcp.japaneast.azmk8s.io:443
        $ kubectl describe secret $(kubectl get secrets | grep default | cut -f1 -d ' ') | grep -E '^token' | cut -f2 -d':' | tr -d '\t'
        ********************************.
**********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************.*********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************-********************-******************************************************************************************************************************************************************************************************************************************************************************
-*********************
    */
    
    private final static String API_SERVER_NAME = "https://yoshio-aks-4f011808.hcp.japaneast.azmk8s.io";
    private final static String ACCESS_TOKEN = "eyJhbGciOiJSUzI1NiIsImtpZCI6IiJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6ImRlZmF1bHQtdG9rZW4tODR4d2oiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC5uYW1lIjoiZGVmYXVsdCIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50LnVpZCI6IjlkZjYzNDBkLTdiOGEtMTFlOC04MzUwLTk2OGNiM2RkZWNhZCIsInN1YiI6InN5c3RlbTpzZXJ2aWNlYWNjb3VudDpkZWZhdWx0OmRlZmF1bHQifQ.RxhPiPoTR2rs6s5WNeUSKbskawVx5w6acITlp5z8vgyuA8XQrTItH0qffZI4WRtWbObswIjBY5TjVaBZONLADgob87nGAlCdakjae2VoUUsCARpJsFTEFYCkeGVcwYyz3suw7CMqbbd161MqfUJMHctmzYprHcp7ibRtD1DZvPnEQyzDS2HxIRqohXmzVaQEjLWjiFwraNsA6EPD1SGzevgUPyIGxR8MnmIk7vrKGCyK_iRZEotsZKf3jSvMpv2518fQd9OUTN00llHwcf9TIZ46EMG97JTbAvBn8ccmpFBW7rp8h6sWE8EUdS2a8rKGZHn54m_DVZ_uYU9ntI7PPVGqZzvecq7ZFkF5BlqH8J1kSiJMa-3_eF1hppoP3z_I4ZkXxV-EkCkyO9uTxr4NqgYeEIlzSNaM5_xUdHLM_ClMoO0qDyOmznROMTnUKzs0XPTuqbZDLMaCbuEGfy1FU6SsDR9DgvZbFe7b8xrDQMUAAtSq6VqEL414lbKehIHndMOGbwIbd7uyAFlwW8jCjPCEMoRpTcDxuMpKkoAeZH6OoFfOtYk2NbluJDMHtQ33xVSMb2yBmEhKu3HhyDjRad8Zzt7O6hcPTU_YFxG89vtf3FGqbQv7kZRfDUA__Z9hAP3wX_ZN16HZXcJ3YzHfW-8_rjNvSMMSadW4yiakLVQ";

    private final static Logger LOGGER = Logger.getLogger(KubernetesOperation.class.getName());

    /**
     * Constructor
     */
    public KubernetesOperation() {
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
            KubernetesOperation operation = new KubernetesOperation();
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
        V1NamespaceList listNamespace = corev1Api.listNamespace("true", null, null, Boolean.FALSE, null, Integer.SIZE, null, Integer.BYTES, Boolean.FALSE);
        List<String> list = listNamespace.getItems().stream().map(v1Namespace -> v1Namespace.getMetadata().getName()).collect(Collectors.toList());
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
        List<String> listPods = corev1Api.listNamespacedPod(namespace, //namespace - object name and auth scope, such as for teams and projects (required)
                null, //pretty - If 'true', then the output is pretty printed. (optional)
                null, //_continue - The continue option should be set when retrieving more results from the server. Since this value is server defined, clients may only use the continue value from a previous query result with identical query parameters (except for the value of continue) and the server may reject a continue value it does not recognize. If the specified continue value is no longer valid whether due to expiration (generally five to fifteen minutes) or a configuration change on the server the server will respond with a 410 ResourceExpired error indicating the client must restart their list without the continue field. This field is not supported when watch is true. Clients may start a watch from the last resourceVersion value returned by the server and not miss any modifications. (optional)
                null, //fieldSelector - A selector to restrict the list of returned objects by their fields. Defaults to everything. (optional)
                Boolean.FALSE, //includeUninitialized - If true, partially initialized resources are included in the response. (optional)
                label, //labelSelector - A selector to restrict the list of returned objects by their labels. Defaults to everything. (optional)
                Integer.SIZE, //limit - limit is a maximum number of responses to return for a list call. If more items exist, the server will set the `continue` field on the list metadata to a value that can be used with the same initial query to retrieve the next set of results. Setting a limit may return fewer than the requested amount of items (up to zero items) in the event all requested objects are filtered out and clients should only use the presence of the continue field to determine whether more results are available. Servers may choose not to support the limit argument and will return all of the available results. If limit is specified and the continue field is empty, clients may assume that no more results are available. This field is not supported if watch is true. The server guarantees that the objects returned when using continue will be identical to issuing a single list call without a limit - that is, no objects created, modified, or deleted after the first request is issued will be included in any subsequent continued requests. This is sometimes referred to as a consistent snapshot, and ensures that a client that is using limit to receive smaller chunks of a very large result can ensure they see all possible objects. If objects are updated during a chunked list the version of the object that was present at the time the first list result was calculated is returned. (optional)
                null, //resourceVersion - When specified with a watch call, shows changes that occur after that particular version of a resource. Defaults to changes from the beginning of history. When specified for list: - if unset, then the result is returned from remote storage based on quorum-read flag; - if it's 0, then we simply return what we currently have in cache, no guarantee; - if set to non zero, then the result is at least as fresh as given rv. (optional)
                Integer.BYTES, //timeoutSeconds  - Timeout for the list/watch call. This limits the duration of the call, regardless of any activity or inactivity. (optional)
                Boolean.FALSE).getItems().stream().map(v1pod -> v1pod.getMetadata().getName()).collect(Collectors.toList());//watch           - Watch for changes to the described resources and return them as a stream of add, update, and remove notifications. Specify resourceVersion. (optional)
        return listPods;
    }

    /**
     * List all Services in default namespace
     *
     * @return
     * @throws ApiException
     */
    public List<String> getServices() throws ApiException {
        V1ServiceList listNamespacedService = corev1Api.listNamespacedService(DEFAULT_NAME_SPACE, null, null, null, Boolean.FALSE, null, Integer.SIZE, null, Integer.BYTES, Boolean.FALSE);
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
        ExtensionsV1beta1DeploymentList listNamespacedDeployment = extensionV1Api.listNamespacedDeployment(
                DEFAULT_NAME_SPACE, //namespace - object name and auth scope, such as for teams and projects (required)
                null, //pretty - If 'true', then the output is pretty printed. (optional)
                null, //_continue - The continue option should be set when retrieving more results from the server. Since this value is server defined, clients may only use the continue value from a previous query result with identical query parameters (except for the value of continue) and the server may reject a continue value it does not recognize. If the specified continue value is no longer valid whether due to expiration (generally five to fifteen minutes) or a configuration change on the server the server will respond with a 410 ResourceExpired error indicating the client must restart their list without the continue field. This field is not supported when watch is true. Clients may start a watch from the last resourceVersion value returned by the server and not miss any modifications. (optional)
                null, //fieldSelector - A selector to restrict the list of returned objects by their fields. Defaults to everything. (optional) 
                Boolean.FALSE, //includeUninitialized - If true, partially initialized resources are included in the response. (optional)
                null, //labelSelector - A selector to restrict the list of returned objects by their labels. Defaults to everything. (optional)
                null, //limit - limit is a maximum number of responses to return for a list call. If more items exist, the server will set the `continue` field on the list metadata to a value that can be used with the same initial query to retrieve the next set of results. Setting a limit may return fewer than the requested amount of items (up to zero items) in the event all requested objects are filtered out and clients should only use the presence of the continue field to determine whether more results are available. Servers may choose not to support the limit argument and will return all of the available results. If limit is specified and the continue field is empty, clients may assume that no more results are available. This field is not supported if watch is true. The server guarantees that the objects returned when using continue will be identical to issuing a single list call without a limit - that is, no objects created, modified, or deleted after the first request is issued will be included in any subsequent continued requests. This is sometimes referred to as a consistent snapshot, and ensures that a client that is using limit to receive smaller chunks of a very large result can ensure they see all possible objects. If objects are updated during a chunked list the version of the object that was present at the time the first list result was calculated is returned. (optional)
                null, //resourceVersion - When specified with a watch call, shows changes that occur after that particular version of a resource. Defaults to changes from the beginning of history. When specified for list: - if unset, then the result is returned from remote storage based on quorum-read flag; - if it's 0, then we simply return what we currently have in cache, no guarantee; - if set to non zero, then the result is at least as fresh as given rv. (optional)
                null, //timeoutSeconds - Timeout for the list/watch call. This limits the duration of the call, regardless of any activity or inactivity. (optional)
                Boolean.FALSE); //watch - Watch for changes to the described resources and return them as a stream of add, update, and remove notifications. Specify resourceVersion. (optional)

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
