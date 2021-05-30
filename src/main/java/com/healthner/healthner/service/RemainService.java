package com.healthner.healthner.service;

import com.healthner.healthner.controller.dto.RemainDto;
import com.healthner.healthner.domain.Remain;
import com.healthner.healthner.repository.RemainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RemainService {
    private final RemainRepository remainRepository;

    public List<RemainDto.Response> findByUserId(Long userId) {
        return remainRepository.findByUserId(userId)
                .stream()
                .map(remain -> new RemainDto.Response(remain))
                .filter(remain -> remain.getRemainCount() > 0)
                .collect(Collectors.toList());
    }

    @Transactional
    public void minusCount(Long remainId) {
        Remain remain = remainRepository.findById(remainId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 remain입니다."));

        if (remain.getRemainCount() != 0) {
            remain.minusRemainCount();
        }
    }

    @Transactional
    public void plusCount(Long remainId) {
        Remain remain = remainRepository.findById(remainId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 remain입니다."));
        remain.plusRemainCount();
    }

    public Remain findById(Long remainId) {
        return remainRepository.findById(remainId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 remain id 입니다." + remainId)
        );
    }

    public Remain findByPurchaseId(Long purchaseId) {
        return remainRepository.findByPurchaseId(purchaseId).orElseThrow(() ->
                new IllegalArgumentException("잔여 내역이 존재하지 않습니다."));
    }

    @Transactional
    public void save(Remain remain) {
        remainRepository.save(remain);
    }
}
