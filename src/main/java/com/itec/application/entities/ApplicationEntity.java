package com.itec.application.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="applications")
public class ApplicationEntity {
    @Id
    @Column(name = "application_id")
    private String applicationUUID;

    @Column(name = "owner_id")
    private String ownerID;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "application_name")
    private String applicationName;

    @Column(name = "application_photo")
    private String applicationPhoto;

    @Column(name = "application_description")
    private String applicationDescription;

    public ApplicationEntity() {}
    public ApplicationEntity(String applicationUUID, String ownerID, String ownerName, String applicationName, String applicationPhoto, String applicationDescription) {
        this.applicationUUID = applicationUUID;
        this.ownerID = ownerID;
        this.ownerName = ownerName;
        this.applicationName = applicationName;
        this.applicationPhoto = applicationPhoto;
        this.applicationDescription = applicationDescription;
    }

    public String getApplicationUUID() {
        return applicationUUID;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getApplicationPhoto() {
        return this.applicationPhoto;
    }

    public String getApplicationDescription() {
        return applicationDescription;
    }
}
