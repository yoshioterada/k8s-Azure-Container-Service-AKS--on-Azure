package com.microsoft.azure.samples.microprofilehelloazure.api;

import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

// From
// http://adambien.blog/roller/abien/entry/simplest_possible_microprofile_liveness_check
@Health
@ApplicationScoped
public class LivenessCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder healthBuilder = HealthCheckResponse.named("DB-Health-Check");
        //DB Connection Check
        try {
            checkDBAvailability();
            healthBuilder.up().withData("db", "lives");
        } catch (Exception e) {
            healthBuilder.down().withData("db", "dead" + e.getMessage());
        }
        return healthBuilder.build();
    }

    private void checkDBAvailability() throws Exception {
        //Some DB Connection Test
    }
}
