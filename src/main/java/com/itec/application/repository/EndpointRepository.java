package com.itec.application.repository;

import com.itec.application.entities.EndpointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EndpointRepository extends JpaRepository<EndpointEntity, String> {
    Optional<EndpointEntity> findByEndpointName(String endpointName);
    Optional<EndpointEntity> findByEndpointUUID(String endpointUUID);
    Optional<List<EndpointEntity>> findAllByApplicationUUID(String applicationUUID);
    boolean existsByEndpointName(String endpointName);
    boolean existsByEndpointUUID(String endpointUUID);
}
