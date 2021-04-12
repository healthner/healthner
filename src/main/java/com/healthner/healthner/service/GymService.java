package com.healthner.healthner.service;

import com.healthner.healthner.controller.dto.GymDto;
import com.healthner.healthner.repository.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GymService {

    private final GymRepository gymRepository;

    public List<GymDto.Response> findByAddress(String searchKeyword) {
        return gymRepository.findByAddressContaining(searchKeyword)
                .stream()
                .map(gym -> new GymDto.Response(gym))
                .collect(Collectors.toList());
    }
}