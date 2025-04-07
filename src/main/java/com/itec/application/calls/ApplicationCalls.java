package com.itec.application.calls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class ApplicationCalls {
    private Logger logger = LoggerFactory.getLogger(EndpointCalls.class);
    private String applicationUUID;
    private HashMap<String, EndpointCalls> endpoints = new HashMap<>();
    public ApplicationCalls(String applicationUUID) {
        this.applicationUUID = applicationUUID;
    }

    public void addEndpointCalls(String endpointUUID, String endpointType, String endpointValue) {
        EndpointCalls endpointCalls = new EndpointCalls(applicationUUID, endpointUUID, endpointType, endpointValue, 3);
    }

    public void scanForEndpoints() {

    }

    public EndpointCalls getEndpointCalls(String endpointUUID) {
        return this.endpoints.get(endpointUUID);
    }
}
