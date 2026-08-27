package com.novatech.bakeryinventorysystem.service;

import com.novatech.bakeryinventorysystem.model.InventoryMovement;
import com.novatech.bakeryinventorysystem.model.MovementType;
import com.novatech.bakeryinventorysystem.model.Product;
import com.novatech.bakeryinventorysystem.repository.InventoryMovementRepository;
import com.novatech.bakeryinventorysystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryMovementRepository movementRepository;

    @Autowired
    private ProductRepository productRepository;

    public void registerMovement(Long productId, MovementType type, Integer quantity, String username, String note) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (type == MovementType.ENTRADA) {
            product.setStockQuantity(product.getStockQuantity() + quantity);
        } else {
            if (product.getStockQuantity() < quantity) {
                throw new RuntimeException("No hay suficiente stock para esta salida");
            }
            product.setStockQuantity(product.getStockQuantity() - quantity);
        }
        productRepository.save(product);

        InventoryMovement movement = new InventoryMovement();
        movement.setProduct(product);
        movement.setType(type);
        movement.setQuantity(quantity);
        movement.setDate(LocalDateTime.now());
        movement.setUsername(username);
        movement.setNote(note);
        movementRepository.save(movement);
    }

    public List<InventoryMovement> getMovementsForProduct(Long productId) {
        return movementRepository.findByProductIdOrderByDateDesc(productId);
    }

    public List<InventoryMovement> getAllMovements() {
        return movementRepository.findAllByOrderByDateDesc();
    }
    public List<InventoryMovement> getFilteredMovements(Long productId, LocalDateTime start, LocalDateTime end) {
        if (productId != null) {
            return movementRepository.findByProduct_IdAndDateBetweenOrderByDateDesc(productId, start, end);
        }
        return movementRepository.findByDateBetweenOrderByDateDesc(start, end);
    }
}