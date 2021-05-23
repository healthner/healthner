package com.healthner.healthner.repository;

import com.healthner.healthner.domain.CheckList;
import com.healthner.healthner.domain.CheckListStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheckListRepository extends JpaRepository<CheckList, Long> {

    Long countByGymId(Long id);

    Long countByGymIdAndStatus(Long id, CheckListStatus status);

    Boolean existsByGymId(Long id);

    Optional<CheckList> findByUserPhoneNumberAndGymId(String phone, Long GymId);

    Optional<CheckList> findByUserPhoneNumber(String phone);

    Boolean existsByUserId(Long userId);
}
