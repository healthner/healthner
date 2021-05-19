package com.healthner.healthner.service;

import com.healthner.healthner.controller.dto.PurchaseDto;
import com.healthner.healthner.domain.ProductType;
import com.healthner.healthner.domain.Purchase;
import com.healthner.healthner.repository.ProductRepository;
import com.healthner.healthner.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;

    //구매내역 리스트PT
    public List<PurchaseDto.ResponsePT> findByUserIdAndPT(Long userId) {
        return purchaseRepository.findByUserIdAndProductType(userId, ProductType.PT)
                .stream()
                .map(purchase -> new PurchaseDto.ResponsePT(purchase))
                .filter(purchase -> purchase.getCount() != 0)
                .collect(Collectors.toList());
    }

    //구매내역 리스트Normal
    public List<PurchaseDto.ResponseNormal> findByUserIdAndNormal(Long userId) {
        return purchaseRepository.findByUserIdAndProductType(userId, ProductType.NORMAL)
                .stream()
                .map(purchase -> new PurchaseDto.ResponseNormal(purchase))
                .filter(purchase -> purchase.getPeriod().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    //이미 구매된 상품인지 확인
    public Boolean existsByUserIdAndProductId(Long userId, Long productId) {
        return purchaseRepository.existsByUserIdAndProductId(userId, productId);
    }

    public Long findByGymIdAndUserId(Long userId, Long thisGymId) {
        return purchaseRepository.findByGymIdAndUserId(userId, thisGymId);
    }

    //상품의 타입 확인
    public ProductType findType(Long productId) {
        return productRepository.findWhatType(productId);
    }

    //유효기간이 만료된 PT상품
    public List<PurchaseDto.ResponsePT> endPt(Long userId) {
        return purchaseRepository.findByUserIdAndProductType(userId, ProductType.PT)
                .stream()
                .map(purchase -> new PurchaseDto.ResponsePT(purchase))
                .filter(purchase -> purchase.getCount() == 0)
                .collect(Collectors.toList());
    }

    //유효기간이 만료된 Normal상품
    public List<PurchaseDto.ResponseNormal> endNormal(Long userId) {
        return purchaseRepository.findByUserIdAndProductType(userId, ProductType.NORMAL)
                .stream()
                .map(purchase -> new PurchaseDto.ResponseNormal(purchase))
                .filter(purchase -> purchase.getPeriod().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(Purchase purchase) {
        purchaseRepository.save(purchase);
    }
}