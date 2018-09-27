package com.microsoft.azure.samples.microprofilehelloazure.monitor;

import fish.payara.micro.PayaraMicro;
import fish.payara.micro.PayaraMicroRuntime;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class ApplicationUsageMonitor {

    /**
     * This method will show look like followign log for both CPU and Heap size usage.
     * 
     * [2018-09-04T22:52:28.139+0900] [] [INFO] [] [fish.payara.nucleus.notification.log.LogNotifierService] [tid: _ThreadID=183 _ThreadName=healthcheck-service-30] [timeMillis: 1536069148139] [levelValue: 800] Health Check notification with severity level: 
     * INFO - CPU:Health Check Result:[[status=GOOD, message='CPU%: .36, Time CPU used: 18 milliseconds'']']
     * 
     * [2018-09-04T22:52:28.139+0900] [] [INFO] [] [fish.payara.nucleus.notification.log.LogNotifierService] [tid: _ThreadID=182 _ThreadName=healthcheck-service-28] [timeMillis: 1536069148139] [levelValue: 800] Health Check notification with severity level: 
     * INFO - HEAP:Health Check Result:[[status=GOOD, message='heap: init: 256.00 Mb, used: 216.13 Mb, committed: 1.00 Gb, max.: 3.56 Gbheap%: 5.0%'']']
     * 
     * @param object
     */
    public void postConstruct(@Observes @Initialized(ApplicationScoped.class) Object object) {
        final PayaraMicroRuntime payaraRuntime = PayaraMicro.getInstance().getRuntime();

        payaraRuntime.run("healthcheck-configure", "--enabled=true", "--dynamic=true");
        payaraRuntime.run("healthcheck-configure-service", "--serviceName=healthcheck-cpu", "--enabled=true", "--time=5", "--unit=SECONDS", "--dynamic=true");
        payaraRuntime.run("healthcheck-configure-service-threshold", "--serviceName=healthcheck-cpu", "--thresholdCritical=90", "--thresholdWarning=50", "--thresholdGood=0", "--dynamic=true");
        payaraRuntime.run("healthcheck-configure-service", "--serviceName=healthcheck-heap", "--enabled=true", "--dynamic=true", "--time=5", "--unit=SECONDS");
        payaraRuntime.run("healthcheck-configure-service-threshold", "--serviceName=healthcheck-heap", "--thresholdCritical=90", "--thresholdWarning=50", "--thresholdGood=0", "--dynamic=true");
    }
}
