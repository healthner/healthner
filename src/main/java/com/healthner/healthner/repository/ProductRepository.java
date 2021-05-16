package com.healthner.healthner.repository;

import com.healthner.healthner.domain.Product;
import com.healthner.healthner.domain.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByTrainerId(Long id);
    List<Product> findByTrainerIdAndDeleteStatus(Long trainerId, Boolean deleteStatus);
    boolean existsByTrainerId(Long trainerId);
    List<Product> findByGymId(Long id);
    List<Product> findByGymIdAndType(Long gymId, ProductType type);
}