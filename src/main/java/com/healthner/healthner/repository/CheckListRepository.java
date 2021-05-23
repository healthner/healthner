package com.healthner.healthner.repository;

import com.healthner.healthner.domain.CheckList;
import com.healthner.healthner.domain.CheckListStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CheckListRepository extends JpaRepository<CheckList, Long> {

    Long countByGymId(Long id);

    Long countByGymIdAndStatus(Long id, CheckListStatus status);

    @Query("select c from CheckList c join c.user u where c.gym.id = :gymId and u.phoneNumber = :phoneNumber")
    Optional<CheckList> findByGymIdAndUserPhoneNumber(Long gymId, String phoneNumber);

    Boolean existsByUserId(Long userId);

    Boolean existsByGymIdAndUserPhoneNumber(Long gymId, String phoneNumber);
}