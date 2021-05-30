package com.healthner.healthner.repository;

import com.healthner.healthner.domain.CheckList;
import com.healthner.healthner.domain.CheckListStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CheckListRepository extends JpaRepository<CheckList, Long> {

    Long countByGymId(Long id);

    Long countByGymIdAndStatus(Long id, CheckListStatus status);

    Optional<CheckList> findByGymIdAndUserPhoneNumber(@Param("gymId") Long gymId,
                                                      @Param("phoneNumber") String phoneNumber);

    Boolean existsByUserId(Long userId);

    Boolean existsByGymIdAndUserPhoneNumber(Long gymId, String phoneNumber);

    List<CheckList> findByUserId(Long userId);
}