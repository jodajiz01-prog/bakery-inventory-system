package com.novatech.bakeryinventorysystem.repository;

import com.novatech.bakeryinventorysystem.model.InventoryMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, Long> {
    List<InventoryMovement> findByProductIdOrderByDateDesc(Long productId);
    List<InventoryMovement> findAllByOrderByDateDesc();
    List<InventoryMovement> findByProduct_IdAndDateBetweenOrderByDateDesc(Long productId, LocalDateTime start, LocalDateTime end);
    List<InventoryMovement> findByDateBetweenOrderByDateDesc(LocalDateTime start, LocalDateTime end);
}