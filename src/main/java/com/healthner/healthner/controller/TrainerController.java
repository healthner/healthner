package com.healthner.healthner.controller;

import com.healthner.healthner.controller.dto.GymDto;
import com.healthner.healthner.controller.dto.ProductDto;
import com.healthner.healthner.controller.dto.ReservationDto;
import com.healthner.healthner.controller.dto.UserDto;
import com.healthner.healthner.controller.model.Message;
import com.healthner.healthner.controller.dto.TrainerDto;
import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.ProductType;
import com.healthner.healthner.interceptor.Auth;
import com.healthner.healthner.interceptor.Role;
import com.healthner.healthner.service.GymService;
import com.healthner.healthner.service.ProductService;
import com.healthner.healthner.service.ReservationService;
import com.healthner.healthner.service.TrainerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/trainer")
public class TrainerController {

    private final TrainerService trainerService;
    private final GymService gymService;
    private final ReservationService reservationService;
    private final ProductService productService;

    @Auth(role = Role.USER)
    @GetMapping("new")
    public String getTrainer(Model model, HttpSession session) {
        UserDto.Response userInfo = (UserDto.Response) session.getAttribute("userInfo");

        TrainerDto.Form findForm = trainerService.findByUserId(userInfo.getId());
        if (findForm != null) {
            model.addAttribute("data", new Message("이미 트레이너를 등록하였습니다.", "/home"));
            return "common/message";
        }

        model.addAttribute("trainerForm", new TrainerDto.Form());
        return "trainer/trainer-form";
    }

    @Auth(role = Role.USER)
    @PostMapping("new")
    public String save(@ModelAttribute("trainerForm") TrainerDto.Form form, HttpSession session, Model model) {
        UserDto.Response userInfo = (UserDto.Response) session.getAttribute("userInfo");

        TrainerDto.Form findForm = trainerService.findByUserId(userInfo.getId());
        if (findForm != null) {
            model.addAttribute("data", new Message("이미 트레이너를 등록하였습니다.", "/home"));
            return "common/message";
        }

        try {
            trainerService.save(form, userInfo.getId());
        } catch (IllegalArgumentException e) {
            model.addAttribute("data", new Message("트레이너 등록에 실패하였습니다.", "/home"));
            return "common/message";
        }

        model.addAttribute("data", new Message("트레이너에 등록되었습니다.", "/trainer/my-page"));
        return "common/message";
    }

    @Auth(role = Role.USER)
    @GetMapping("update")
    public String getUpdate(Model model, HttpSession session) {
        UserDto.Response userInfo = (UserDto.Response) session.getAttribute("userInfo");
        TrainerDto.Form trainerForm = trainerService.findByUserId(userInfo.getId());
        model.addAttribute("trainerForm", trainerForm);
        return "trainer/trainer-form";
    }

    @Auth(role = Role.USER)
    @PostMapping("update")
    public String update(@ModelAttribute("trainerForm") TrainerDto.Form form,
                         HttpSession session, Model model) {
        UserDto.Response userInfo = (UserDto.Response) session.getAttribute("userInfo");
        TrainerDto.Form trainerForm = trainerService.findByUserId(userInfo.getId());

        try {
            trainerService.update(trainerForm.getId(), userInfo.getId(), form);
        } catch (IllegalArgumentException e) {
            model.addAttribute("data",
                    new Message("존재하지 않는 트레이너로 인하여 수정을 실패하였습니다.", "/home"));
            return "common/message";
        }

        model.addAttribute("data", new Message("수정이 완료되었습니다.", "/trainer/my-page"));
        return "common/message";
    }

    @Auth(role = Role.USER)
    @GetMapping("my-page")
    public String myPage(HttpSession session, Model model) {
        UserDto.Response userInfo = (UserDto.Response) session.getAttribute("userInfo");
        TrainerDto.Form findForm = trainerService.findByUserId(userInfo.getId());

        if (findForm == null) {
            model.addAttribute("data", new Message("트레이너를 등록하지 않았습니다.", "/home"));
            return "common/message";
        }
        model.addAttribute("trainer", findForm);

        GymDto.Form gymForm = new GymDto.Form(gymService.findById(findForm.getGymId()));
        model.addAttribute("gym", gymForm);

        List<ReservationDto.ResponseToTrainer> reservations = reservationService.findByTrainerId(findForm.getId());
        model.addAttribute("reservations", reservations);

        List<ProductDto.Response> products = productService
                .findByTrainerIdAndDeleteStatus(findForm.getId(), false);
        model.addAttribute("products", products);

        List<ProductDto.Response> deleteProducts = productService
                .findByTrainerIdAndDeleteStatus(findForm.getId(), true);
        model.addAttribute("deleteProducts", deleteProducts);

        return "trainer/my-page";
    }

    @Auth(role = Role.USER)
    @GetMapping("product/new")
    public String getProduct(Model model) {
        model.addAttribute("product", new ProductDto.Request());

        return "trainer/product-form";
    }

    @Auth(role = Role.USER)
    @PostMapping("product/new")
    public String postProduct(@ModelAttribute("product") ProductDto.Request request,
                              HttpSession session, Model model) {
        UserDto.Response userInfo = (UserDto.Response) session.getAttribute("userInfo");

        TrainerDto.Form trainer = trainerService.findByUserId(userInfo.getId());
        Gym gym = gymService.findById(trainer.getGymId());

        request.setTrainerId(trainer.getId());
        request.setGymId(gym.getId());
        request.setProductType(ProductType.PT);

        productService.save(request);

        model.addAttribute("data", new Message("상품이 등록되었습니다.", "/trainer/my-page"));
        return "common/message";
    }

    @Auth(role = Role.USER)
    @GetMapping("product/{productId}/update")
    public String getUpdateProduct(@PathVariable("productId") Long productId, HttpSession session, Model model) {
        UserDto.Response userInfo = (UserDto.Response) session.getAttribute("userInfo");

        TrainerDto.Form trainer = trainerService.findByUserId(userInfo.getId());

        if (!productService.existsByTrainerId(trainer.getId())) {
            model.addAttribute("data", new Message("수정할 수 없습니다.", "/home"));
            return "common/message";
        }

        ProductDto.Response product = productService.findById(productId);
        model.addAttribute("product", product.toRequest());

        return "trainer/product-form";
    }

    @Auth(role = Role.USER)
    @PostMapping("product/{productId}/update")
    public String postUpdateProduct(@PathVariable("productId") Long productId,
                                    @ModelAttribute("product") ProductDto.Request update,
                                    HttpSession session, Model model) {
        UserDto.Response userInfo = (UserDto.Response) session.getAttribute("userInfo");

        TrainerDto.Form trainer = trainerService.findByUserId(userInfo.getId());

        if (!productService.existsByTrainerId(trainer.getId())) {
            model.addAttribute("data", new Message("수정할 수 없습니다.", "/home"));
            return "common/message";
        }

        Gym gym = gymService.findById(trainer.getGymId());

        update.setTrainerId(trainer.getId());
        update.setGymId(gym.getId());
        update.setProductType(ProductType.PT);

        productService.update(productId, update);

        model.addAttribute("data", new Message("상품이 수정되었습니다.", "/trainer/my-page"));
        return "common/message";
    }

    @Auth(role = Role.USER)
    @GetMapping("product/{productId}/delete")
    public String deleteProduct(@PathVariable("productId") Long productId, HttpSession session, Model model) {
        UserDto.Response userInfo = (UserDto.Response) session.getAttribute("userInfo");

        TrainerDto.Form trainer = trainerService.findByUserId(userInfo.getId());

        if (!productService.existsByTrainerId(trainer.getId())) {
            model.addAttribute("data", new Message("수정할 수 없습니다.", "/home"));
            return "common/message";
        }

        productService.changeDeleteStatus(productId);

        model.addAttribute("data", new Message("상품의 상태가 변경 되었습니다.", "/trainer/my-page"));
        return "common/message";
    }
}