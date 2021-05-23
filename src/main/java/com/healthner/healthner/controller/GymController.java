package com.healthner.healthner.controller;

import com.healthner.healthner.controller.dto.CheckListDto;
import com.healthner.healthner.controller.dto.GymDto;
import com.healthner.healthner.controller.dto.ProductDto;
import com.healthner.healthner.controller.dto.UserDto;
import com.healthner.healthner.controller.model.Message;
import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.ProductType;
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
        return "redirect:/gym/my-page";
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
        return "redirect:/gym/my-page";
    }

    @GetMapping("search")
    public String getSearch() {
        return "gym/search";
    }

    @GetMapping("/detail/{gymId}")
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

    @GetMapping("/my-page")
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
        UserDto.Response ceo = (UserDto.Response) httpSession.getAttribute("userInfo");
        GymDto.Form gym = gymService.findByCeoId(ceo.getId());
        Long total = checkListService.total(gym.getId());
        Long users = checkListService.users(gym.getId());
        
        model.addAttribute("total", total);
        model.addAttribute("users", users);

        return "check";
    }

    @Auth(role = Role.USER)
    @PostMapping("/check")
    public String postCheck(HttpSession httpSession, CheckListDto.Request check, Model model) {
        UserDto.Response ceo = (UserDto.Response) httpSession.getAttribute("userInfo");
        GymDto.Form gym = gymService.findByCeoId(ceo.getId());

        //입장 or 퇴장
        if (true/*조건문 회의 후 결정*/) {
            checkListService.checkIn(check.getPhoneNumber(), gym.getId());
        }
        else {
            checkListService.checkOut(check.getPhoneNumber(), gym.getId());
        }

        //인원 현황
        Long total = checkListService.total(gym.getId());
        Long users = checkListService.users(gym.getId());

        model.addAttribute("total", total);
        model.addAttribute("users", users);

        return "redirect:/gym/check";
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
        return "redirect:/gym/my-page";
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
    public String UpdateProduct(HttpSession httpSession, @PathVariable Long productId, @ModelAttribute("product") ProductDto.Request request, Model model){
        GymDto.Form thisgym = gymService.findByCeoId(((UserDto.Response) httpSession.getAttribute("userInfo")).getId());
        request.setProductType(ProductType.NORMAL);
        model.addAttribute("product", productService.updateNormal(productId, request));

        return "redirect:/gym/my-page";
    }

    @GetMapping("/product/delete/{productId}")
    @Auth(role = Role.USER)
    public String delete(@PathVariable("productId") Long productId) {
        productService.delete(productId);
        return "redirect:/gym/my-page";
    }

}
