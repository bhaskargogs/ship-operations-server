package com.operations.ship.repository;

import com.operations.ship.model.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipRepository extends JpaRepository<Ship, String>, JpaSpecificationExecutor<Ship> {
}
