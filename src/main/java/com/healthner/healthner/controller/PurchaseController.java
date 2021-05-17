package com.healthner.healthner.controller;

import com.healthner.healthner.controller.dto.PurchaseDto;
import com.healthner.healthner.controller.dto.UserDto;
import com.healthner.healthner.controller.model.Message;
import com.healthner.healthner.domain.ProductType;
import com.healthner.healthner.interceptor.Auth;
import com.healthner.healthner.interceptor.Role;
import com.healthner.healthner.service.CheckListService;
import com.healthner.healthner.service.ProductService;
import com.healthner.healthner.service.PurchaseService;
import com.healthner.healthner.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final ProductService productService;
    private final UserService userService;
    private final CheckListService checkListService;

    @GetMapping("/{productId}")
    @Auth(role = Role.USER)
    public String purchase(@PathVariable("productId") Long productId, Model model, HttpSession httpSession) throws InterruptedException {
        UserDto.Response user = (UserDto.Response) httpSession.getAttribute("userInfo");
        if (purchaseService.existsByUserIdAndProductId(user.getId(), productId)) {
            model.addAttribute("data",
                    new Message("이미 구매한 상품입니다", "/home"));
            return "common/message";
        }
        try {
            PurchaseDto.Request request = new PurchaseDto.Request();
            //NORMAL상품일 경우
            if (purchaseService.findType(productId) == ProductType.NORMAL) {
                request.setPeriod(LocalDateTime.now().plusMonths(productService.getProduct(productId).getPeriod()));
            }
            //PT 상품일 경우
            else if (purchaseService.findType(productId) == ProductType.PT) {
                request.setCount(productService.getProduct(productId).getCount());
            }
            //구매 진행
            purchaseService.save(request.toEntity(userService.findById(user.getId()), productService.getProduct(productId).getGym(),
                    productService.getProduct(productId).getTrainer(), productService.getProduct(productId)));
            //출석객체 생성 후 결석으로 세팅
            checkListService.put(userService.findById(user.getId()), productService.getProduct(productId).getGym());
            Thread.sleep(2000);
            model.addAttribute("data",
                    new Message("구매가 완료되었습니다.", "/user/my-page"));

        } catch (IllegalArgumentException e) {
            model.addAttribute("data",
                    new Message("옳바르지 않은 상품입니다.", "/home"));
            return "common/message";
        }
        return "common/message";
    }
}
