package com.itec.application.repository;

import com.itec.application.entities.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationsRepository extends JpaRepository<ApplicationEntity, String> {
    Optional<ApplicationEntity> findByApplicationName(String applicationName);
    Optional<ApplicationEntity> findByOwnerID(String ownerID);
    Optional<ApplicationEntity> findByApplicationUUID(String applicationUUID);
    Optional<List<ApplicationEntity>> findAllByOwnerID(String ownerID);
    boolean existsByApplicationName(String applicationName);
    boolean existsByApplicationUUID(String applicationUUID);
}
