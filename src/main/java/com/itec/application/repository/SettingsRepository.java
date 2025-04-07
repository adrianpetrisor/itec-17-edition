package com.itec.application.repository;

import com.itec.application.entities.SettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettingsRepository extends JpaRepository<SettingsEntity, String> {
    Optional<SettingsEntity> findByApplicationUUID(String applicationUUID);
}
