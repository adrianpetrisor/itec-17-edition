package com.itec.application.calls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class EndpointCalls {
    private Logger logger = LoggerFactory.getLogger(EndpointCalls.class);
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> task;

    private String applicationUUID;
    private String endpointUUID;
    private String endpointType;
    private String endpointValue;
    private int callInterval;
    private HashMap<LocalDateTime, Integer> calls = new HashMap<>();

    public EndpointCalls(String applicationUUID, String endpointUUID, String endpointType, String endpointValue, int callInterval) {
        this.applicationUUID = applicationUUID;
        this.endpointUUID = endpointUUID;
        this.endpointType = endpointType;
        this.endpointValue = endpointValue;
        this.callInterval = callInterval;
    }

    public void startCalls() {
        logger.info("Started calls for endpoint " + endpointUUID + " of application " + applicationUUID + ".");
        this.task = scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                logger.info("This is a simple call.");
            }
        }, 0, callInterval, TimeUnit.SECONDS);
    }

    public void stopCalls() {

    }

    public String getApplicationUUID() {
        return this.applicationUUID;
    }

    public String getEndpointUUID() {
        return this.endpointUUID;
    }

    public String getEndpointType() {
        return this.endpointType;
    }

    public String getEndpointValue() {
        return this.endpointValue;
    }
}
