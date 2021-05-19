package com.healthner.healthner.controller;

import com.healthner.healthner.controller.dto.CheckListDto;
import com.healthner.healthner.controller.dto.GymDto;
import com.healthner.healthner.controller.dto.ProductDto;
import com.healthner.healthner.controller.dto.UserDto;
import com.healthner.healthner.controller.model.Message;
import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.ProductType;
import com.healthner.healthner.domain.User;
import com.healthner.healthner.interceptor.Auth;
import com.healthner.healthner.interceptor.Role;
import com.healthner.healthner.service.CheckListService;
import com.healthner.healthner.service.GymService;
import com.healthner.healthner.service.ProductService;
import com.healthner.healthner.service.PurchaseService;
import com.healthner.healthner.service.TrainerService;
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
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/gym")
public class GymController {

    private final GymService gymService;
    private final UserService userService;
    private final PurchaseService purchaseService;
    private final CheckListService checkListService;
    private final ProductService productService;
    private final TrainerService trainerService;


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
        gymService.register(gymDto, ceoId);
        return "redirect:/gym/mypage";
    }

    @GetMapping("/update")
    @Auth(role = Role.USER)
    public String modify(HttpSession httpSession, Model model) {
        GymDto.Form thisgym = gymService.findByCeoId(((UserDto.Response) httpSession.getAttribute("userInfo")).getId());
        Long thisGymId = thisgym.getId();
        Gym gymEntity = gymService.findById(thisGymId);
        GymDto.Form gym = new GymDto.Form(gymEntity);
        model.addAttribute("gym", gym);
        return "/gym/create-gym";
    }

    @PostMapping("/update")
    @Auth(role = Role.USER)
    public String modify(HttpSession httpSession, @ModelAttribute("gym") GymDto.Request gymDto) {
        GymDto.Form thisgym = gymService.findByCeoId(((UserDto.Response) httpSession.getAttribute("userInfo")).getId());
        Long gymId = thisgym.getId();
        gymService.update(gymId, gymDto);
        return "redirect:/gym/mypage";
    }

    @GetMapping("search")
    public String getSearch() {
        return "gym/search";
    }

    @GetMapping("/detail/{gymId}")
    @Auth(role = Role.USER)
    public String detail(@PathVariable("gymId") Long gymId, Model model) {
        Gym gym;
        try {
            gym = gymService.findById(gymId);
            GymDto.Response response = new GymDto.Response(gym);

            model.addAttribute("gym", response);
            model.addAttribute("normalProducts", productService.findByGymIdAndTypeNormal(gymId, ProductType.NORMAL));
            model.addAttribute("ptProducts", productService.findByGymIdAndTypePT(gymId, ProductType.PT));
            model.addAttribute("trainers", trainerService.findByGymId(gymId));

        } catch (IllegalArgumentException e) {
            model.addAttribute("data", new Message("존재하지 않는 기관입니다.", "/gym/search"));
            return "common/message";
        }

        return "gym/detail";
    }

    @GetMapping("/mypage")
    @Auth(role = Role.USER)
    public String getGym(HttpSession httpSession, Model model) {
        GymDto.Form gym = gymService.findByCeoId(((UserDto.Response) httpSession.getAttribute("userInfo")).getId());
        List<ProductDto.ResponseNormal> products = productService.findByGymId(gym.getId())
                .stream().filter((product) -> product.getDeleteStatus() == false)
                .collect(Collectors.toList());
        List<ProductDto.ResponseNormal> deletedProducts = productService.findByGymId(gym.getId())
                .stream().filter((product) -> product.getDeleteStatus() == true)
                .collect(Collectors.toList());
        model.addAttribute("gym", gym);
        model.addAttribute("products", products);
        model.addAttribute("deletedProducts", deletedProducts);

        return "gym/my-page";
    }

    //출석체크
    @GetMapping(value = "/check")
    @Auth(role = Role.USER)
    public String getCheck(HttpSession httpSession, Model model) {
        UserDto.Response user = (UserDto.Response) httpSession.getAttribute("userInfo");
        GymDto.Form gym = gymService.findByCeoId(user.getId());
        if (gym == null) {
            return "등록되지 않은 기관입니다.";
        } else {
            model.addAttribute("checkListDto", new CheckListDto.Request());
        }
        Long gymId = gym.getId();
        //인원 현황
        Long total = checkListService.countByGymId(gymId);
        model.addAttribute("total", total);

        return "check";
    }

    @Auth(role = Role.USER)
    @PostMapping("/check")
    public String postCheck(HttpSession httpSession, CheckListDto.Request request, Model model) {
        //로그인된 사람이 gym의 ceo가 맞는지
        GymDto.Form thisgym = gymService.findByCeoId(((UserDto.Response) httpSession.getAttribute("userInfo")).getId());
        Long thisGymId = thisgym.getId();
        //해당 기관의 gym객체
        Gym gym = gymService.findById(thisGymId);

        // 출석체크 할 유저에 구매내역 리스트에 해당 gym이 있는지 확인
        Long userId = userService.findByEmail(request.getEmail()).getId();
        Long checkUser = purchaseService.findByGymIdAndUserId(userId, thisGymId);

        if (checkUser == userId) { // 유저의 구매내역에 해당 gym이 있으면
            User user = userService.findById(userId);//출석 체크할 유저
            checkListService.put(user, gym);
        } else {
            return "해당 기관의 회원이 아닙니다";
        }
        //인원 현황
        Long total = checkListService.countByGymId(thisGymId);
        model.addAttribute("total", total);

        return "redirect:/gym/mypage";
    }

    @GetMapping("/product/new")
    @Auth(role = Role.USER)
    public String getProduct(HttpSession httpSession, Model model) {
        GymDto.Form thisgym = gymService.findByCeoId(((UserDto.Response) httpSession.getAttribute("userInfo")).getId());
        model.addAttribute("product", new ProductDto.Request());

        return "/gym/product-form";
    }

    @PostMapping("/product/new")
    @Auth(role = Role.USER)
    public String PostProduct(@ModelAttribute("product") ProductDto.Request request,
                              HttpSession httpSession) {
        GymDto.Form thisgym = gymService.findByCeoId(((UserDto.Response) httpSession.getAttribute("userInfo")).getId());
        Long thisGymId = thisgym.getId();
        Gym gym = gymService.findById(thisGymId);
        gymService.putProduct(request, gym);
        return "redirect:/gym/mypage";
    }

    @GetMapping("/product/update/{productId}")
    @Auth(role = Role.USER)
    public String FindUpdateProduct(HttpSession httpSession, @PathVariable Long productId, Model model) {
        GymDto.Form thisgym = gymService.findByCeoId(((UserDto.Response) httpSession.getAttribute("userInfo")).getId());
        model.addAttribute("product", productService.findByIdToNormal(productId));

        return "/gym/product-form";
    }

    @PostMapping("/product/update/{productId}")
    @Auth(role = Role.USER)
    public String UpdateProduct(HttpSession httpSession, @PathVariable Long productId, @ModelAttribute("product") ProductDto.Request request, Model model) {
        GymDto.Form thisgym = gymService.findByCeoId(((UserDto.Response) httpSession.getAttribute("userInfo")).getId());
        request.setProductType(ProductType.NORMAL);
        model.addAttribute("product", productService.updateNormal(productId, request));

        return "redirect:/gym/mypage";
    }

    @GetMapping("/product/delete/{productId}")
    @Auth(role = Role.USER)
    public String delete(@PathVariable("productId") Long productId) {
        productService.delete(productId);
        return "redirect:/gym/mypage";
    }
}
