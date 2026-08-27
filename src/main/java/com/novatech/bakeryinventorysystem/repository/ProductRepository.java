package com.novatech.bakeryinventorysystem.repository;

import com.novatech.bakeryinventorysystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}