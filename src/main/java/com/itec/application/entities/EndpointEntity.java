package com.itec.application.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "endpoints")
public class EndpointEntity {
    @Id
    @Column(name = "endpoint_uuid")
    private String endpointUUID;

    @Column(name = "application_uuid")
    private String applicationUUID;

    @Column(name = "endpointName")
    private String endpointName;

    @Column(name = "endpoint_type")
    private String endpointType;

    @Column(name = "endpoint_value")
    private String endpointValue;

    public EndpointEntity() {}
    public EndpointEntity(String endpointUUID, String applicationUUID, String endpointName, String endpointType, String endpointValue) {
        this.endpointUUID = endpointUUID;
        this.applicationUUID = applicationUUID;
        this.endpointName = endpointName;
        this.endpointType = endpointType;
        this.endpointValue = endpointValue;
    }

    public String getEndpointUUID() {
        return endpointUUID;
    }

    public String getApplicationUUID() {
        return applicationUUID;
    }

    public String getEndpointName() {
        return endpointName;
    }

    public String getEndpointType() {
        return endpointType;
    }

    public String getEndpointValue() {
        return endpointValue;
    }
}
