package com.healthner.healthner.service;

import com.healthner.healthner.controller.dto.PurchaseDto;
import com.healthner.healthner.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;

    //구매내역 리스트
    public List<PurchaseDto.Response>findByUserId(Long userId){
        return purchaseRepository.findByUserId(userId)
                .stream()
                .map(purchase -> new PurchaseDto.Response(purchase))
                .collect(Collectors.toList());
    }

    public Long findByGymIdAndUserId(Long userId, Long thisGymId){
        return purchaseRepository.findByGymIdAndUserId(userId, thisGymId);
    }
}