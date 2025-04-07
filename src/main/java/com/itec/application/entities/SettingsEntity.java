package com.itec.application.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "settings")
public class SettingsEntity {
    @Id
    @Column(name = "application_uuid")
    private String applicationUUID;

    @Column(name = "statistics_type")
    private String statisticsType;

    @Column(name = "statistics_interval")
    private int statisticsInterval;

    @Column(name = "call_interval")
    private int callInterval;

    public SettingsEntity() {}

    public SettingsEntity(String applicationUUID, String statisticsType, int statisticsInterval, int callInterval) {
        this.applicationUUID = applicationUUID;
        this.statisticsType = statisticsType;
        this.statisticsInterval = statisticsInterval;
        this.callInterval = callInterval;
    }

    public String getApplicationUUID() {
        return applicationUUID;
    }

    public String getStatisticsType() {
        return statisticsType;
    }

    public int getStatisticsInterval() {
        return statisticsInterval;
    }

    public int getCallInterval() {
        return callInterval;
    }
}
