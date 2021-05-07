package com.healthner.healthner.service;

import com.healthner.healthner.controller.dto.CheckListDto;
import com.healthner.healthner.controller.dto.GymDto;
import com.healthner.healthner.domain.CheckList;
import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.User;
import com.healthner.healthner.repository.GymRepository;
import com.healthner.healthner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GymService {

    private final UserRepository userRepository;
    private final GymRepository gymRepository;

    public List<GymDto.Response> findByAddress(String searchKeyword) {
        return gymRepository.findByAddressContaining(searchKeyword)
                .stream()
                .map(gym -> new GymDto.Response(gym))
                .collect(Collectors.toList());
    }

    public GymDto.Form findById(Long gymId) {
        Gym gym = gymRepository.findById(gymId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 gym id 입니다. id=" + gymId)
        );

        return new GymDto.Form(gym);
    }

    //Gym 등록
    @Transactional
    public Long register(GymDto.Request gym, Long ceoId) {
        User ceo = userRepository.findById(ceoId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저 Id"));

        Gym haveGym = gymRepository.findByCeoId(ceoId).orElse(null);
        // 기존의 보유하고 있는 기관이 있는지 검증
        if (haveGym != null) {
            return haveGym.getId(); // 보유하고 있다면 보유 기관 id 리턴
        }
        // 없다면 새롭게 생성하고 새로운 기관 id 리턴
        Gym newGym = gym.toEntity(ceo);
        Long saveId = gymRepository.save(newGym).getId();
        System.out.println(saveId);
        return saveId;
    }

    @Transactional
    public void update(Long gymId, GymDto.Request modifyGym) {
        Gym gym = gymRepository.findById(gymId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시설 Id"));
        gym.updateGym(modifyGym.toEntity(gym.getCeo()));
    }

    @Transactional
    public void delete(Long gymId) {
        gymRepository.deleteById(gymId);
    }

    public GymDto.Form findByCeoId(Long ceoId){
        Gym gym = gymRepository.findByCeoId(ceoId).orElseThrow(() -> new IllegalArgumentException("등록되지 않은 기관입니다."));
        return new GymDto.Form(gym);
    }

    //총 인원 리턴
    public Long checkTotal(CheckList checkList){
        return new CheckListDto.Response(checkList).getTotal();
    }

}