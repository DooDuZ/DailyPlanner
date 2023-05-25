package com.planner.domain.entity.type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeRepository extends JpaRepository<TypeEntity, Integer> {
    Optional<TypeEntity> findBytName(String typeName);
}
