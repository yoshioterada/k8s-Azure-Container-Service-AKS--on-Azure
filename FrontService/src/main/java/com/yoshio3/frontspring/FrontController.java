/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoshio3.frontspring;

import com.microsoft.applicationinsights.TelemetryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.microsoft.applicationinsights.telemetry.Duration;
import java.util.Map;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/sample")
public class FrontController {

    @Autowired
    TelemetryClient telemetryClient;

    @GetMapping("/hello")
    public String hello(@RequestHeader Map<String, String> headers) {
        headers.forEach((key, value) -> {
            System.out.println(String.format("Header '%s' = %s", key, value));
        });
        //track a custom event  
        //telemetryClient.trackEvent("Sending a custom event...");

        //trace a custom trace
        //telemetryClient.trackTrace("Sending a custom trace....");

        //track a custom metric
        //telemetryClient.trackMetric("custom metric", 1.0);

        //track a custom dependency
        //telemetryClient.trackDependency("SQL", "Insert", new Duration(0, 0, 1, 1, 1), true);
	/*
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

        String resourceUrl
                = "http://localhost:18080/sample/hello";
        
        restTemplate.exchange(resourceUrl, HttpMethod.GET, null, String.class).getBody();
        
        ResponseEntity<String> response
                = restTemplate.getForEntity(resourceUrl, String.class);
        return "front-hello + " +response.getBody();
	*/
        return "front-hello-v1";
    }
}
