package com.healthner.healthner.controller;

import com.healthner.healthner.controller.dto.CheckListDto;
import com.healthner.healthner.controller.dto.GymDto;
import com.healthner.healthner.controller.dto.UserDto;
import com.healthner.healthner.interceptor.Auth;
import com.healthner.healthner.interceptor.Role;
import com.healthner.healthner.repository.CheckListRepository;
import com.healthner.healthner.service.GymService;
import com.healthner.healthner.service.PurchaseService;
import com.healthner.healthner.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/gym")
public class GymController {

    private final GymService gymService;
    private final UserService userService;
    private final PurchaseService purchaseService;

    //서비스 만들어서 레포 참조 지우기********************************
    private final CheckListRepository checkListRepository;


    @GetMapping("/new")
    @Auth(role = Role.USER)
    public String register(Model model) {
        model.addAttribute("gym", new GymDto.Request());
        return "/gym/create-gym";
    }

    @PostMapping("/new")
    @Auth(role = Role.USER)
    public String register(@ModelAttribute("gym") GymDto.Request gymDto, HttpSession session) {
        UserDto.Response user = (UserDto.Response) session.getAttribute("userInfo");
        Long ceoId = user.getId();
        Long gymId = gymService.register(gymDto, ceoId);
        return "redirect:/gym/" + gymId + "/mypage";
    }

    @GetMapping("/{gymId}/update")
    @Auth(role = Role.USER)
    public String modify(@PathVariable Long gymId, Model model) {
        GymDto.Form gym = gymService.findById(gymId);
        model.addAttribute("gym", gym);
        return "/gym/create-gym";
    }

    @PostMapping("/{gymId}/update")
    @Auth(role = Role.USER)
    public String modify(@PathVariable Long gymId, @ModelAttribute("gym") GymDto.Request gymDto) {
        gymService.update(gymId, gymDto);
        return "redirect:/gym/" + gymId + "/mypage";
    }

    @GetMapping("search")
    public String getSearch() {
        return "gym/search";
    }

    @GetMapping("{gymId}/detail")
    public String detail(@PathVariable("gymId") Long id) {
        return "gym/detail";
    }

    @GetMapping("/{gymId}/mypage")
    @Auth(role = Role.USER)
    public String getGym(@PathVariable Long gymId, Model model) {
        GymDto.Form gym = gymService.findById(gymId);
        model.addAttribute("gym", gym);
        return "/gym/mypage";
    }

    //출석체크
    @GetMapping(value = "/check")
    @Auth(role = Role.USER)
    public String getCheck(HttpSession httpSession, Model model){
        UserDto.Response user = (UserDto.Response) httpSession.getAttribute("userInfo");
        GymDto.Form gym = gymService.findByCeoId(user.getId());
        if(gym == null){
            return "등록되지 않은 기관입니다.";
        }else {
            model.addAttribute("checkListDto", new CheckListDto.Request());
        }
        Long gymId = gym.getId();
        Long total = checkListRepository.findByGymId(gymId);
        model.addAttribute("total", total);

        return "check";
    }

//    @Auth(role = Role.USER)
//    @PostMapping("/check")
//    public String postCheck(HttpSession httpSession, CheckListDto.Request request, Model model) {
//        //로그인된 사람이 gym의 ceo가 맞는지
//        GymDto.Form thisgym = gymService.findByCeoId(((UserDto.Response) httpSession.getAttribute("userInfo")).getId());
//        Long thisGymId = thisgym.getId();
//        Gym gym = new GymDto.Request().toEntity((User) httpSession.getAttribute("userInfo"));
//        if(thisgym != null){
//            // 출석체크 할 유저에 구매내역 리스트에 해당 gym이 있는지 확인
//            Long userId = userService.findByEmail(request.getEmail()).getId();
//            if(purchaseService.existsByGymIdAndUserId(userId, thisGymId)){ // 유저의 구매내역에 해당 gym이 있으면
//                User user = userService.findById(userId);
//
//                CheckList checkList = new CheckListDto.Request().checkList(user, gym);
//                model.addAttribute("total", gymService.checkTotal(checkList));
//            }else{
//                return "해당 기관의 회원이 아닙니다";
//            }
//        }else {
//            return "등록되지 않은 기관입니다.";
//        }
//        Long total = checkListRepository.findByGymId(thisGymId);
//        model.addAttribute("total", total);
//
//        return "redirect:/gym/check";
//    }

}
