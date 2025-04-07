package com.itec.application.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "bugs")
public class BugEntity {
    @Id
    @Column(name = "bug_uuid")
    private String bugUUID;

    @Column(name = "application_uuid")
    private String applicationUUID;

    @Column(name = "email")
    private String email;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    public BugEntity() {}
    public BugEntity(String bugUUID, String applicationUUID, String email, String title, String type, String description) {
        this.bugUUID = bugUUID;
        this.applicationUUID = applicationUUID;
        this.email = email;
        this.title = title;
        this.type = type;
        this.description = description;
    }

    public String getBugUUID() {
        return bugUUID;
    }

    public String getApplicationUUID() {
        return applicationUUID;
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
