package com.itec.application.repository;

import com.itec.application.entities.BugEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BugRepository extends JpaRepository<BugEntity, String> {
    Optional<BugEntity> findByEmail(String email);
    Optional<BugEntity> findByBugUUID(String bugUUID);
    Optional<List<BugEntity>> findAllByApplicationUUID(String applicationUUID);
    boolean existsByBugUUID(String bugUUID);
    boolean existsByEmail(String email);
}
