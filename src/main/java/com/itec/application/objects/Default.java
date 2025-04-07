package com.itec.application.objects;

import com.itec.application.entities.SettingsEntity;

public class Default {
    public static SettingsEntity getDefaultSettings(String applicationUUID) {
        return new SettingsEntity(applicationUUID, "minutes", 1, 30);
    }
}
