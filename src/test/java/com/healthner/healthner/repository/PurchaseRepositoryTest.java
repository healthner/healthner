package com.healthner.healthner.repository;

import com.healthner.healthner.domain.ProductType;
import com.healthner.healthner.domain.Purchase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PurchaseRepositoryTest {

    @Autowired
    PurchaseRepository purchaseRepository;

    @Test
    void findByUserIdAndProductType() {
        List<Purchase> purchases = purchaseRepository.findByUserIdAndProductType(10L, ProductType.PT);

        for (Purchase purchase : purchases) {
            System.out.println(purchase.getCount());
        }
    }
}