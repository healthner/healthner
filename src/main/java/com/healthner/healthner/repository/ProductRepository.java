package com.healthner.healthner.repository;

import com.healthner.healthner.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByTrainerId(Long id);
}