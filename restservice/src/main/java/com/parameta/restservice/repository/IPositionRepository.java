package com.parameta.restservice.repository;

import com.parameta.restservice.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPositionRepository extends JpaRepository<Position, Integer> {
    Optional<Position> findByName(String name);
}
