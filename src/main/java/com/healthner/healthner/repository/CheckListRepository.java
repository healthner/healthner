package com.healthner.healthner.repository;

import com.healthner.healthner.domain.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CheckListRepository extends JpaRepository<CheckList, Long> {

    Long countByGymId(Long id);

    Long countByGymInAndStatus_In(Long id);

    Boolean existsByGymId(Long id);

    Optional<CheckList> findByUserPhoneNumberAndGymId(String phone, Long GymId);

    Optional<CheckList> findByUserPhoneNumber(String phone);

    Boolean existsByUserId(Long userId);
}
