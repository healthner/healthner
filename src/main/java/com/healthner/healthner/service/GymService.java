package com.healthner.healthner.service;

import com.healthner.healthner.controller.dto.GymDto;
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

    public GymDto.Response findById(Long gymId) {
        Gym gym = gymRepository.findById(gymId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 gym id 입니다. id=" + gymId)
        );

        return new GymDto.Response(gym);
    }
//    user 와 1:1 관계이므로 List가 될 수 없다.
//    public List<GymDto.Response> findByCeoId(Long ceoId) {
//        List<Gym> gyms = gymRepository.findByCeoId(ceoId);
//        List<GymDto.Response> gymList = gyms.stream()
//                .map(gym -> new GymDto.Response(gym))
//                .collect(Collectors.toList());
//        return gymList;
//    }

    //Gym 등록
    @Transactional
    public Long register(GymDto.Request gym, Long ceoId) {
        User ceo = userRepository.findById(ceoId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저 Id"));
        Gym newGym = gym.toEntity(ceo);
        Long saveId = gymRepository.save(newGym).getId();
        System.out.println(saveId);
        return saveId;
    }

    @Transactional
    public void update(Long gymId, GymDto.Request modifyGym) {
        Gym gym = gymRepository.findById(gymId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시설 Id"));
        gym.updateGym(modifyGym);
    }

    @Transactional
    public void delete(Long gymId) {
        gymRepository.deleteById(gymId);
    }

}