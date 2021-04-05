package com.healthner.healthner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealthnerController {

    @GetMapping(value = "/")
    public String home(){
        return "home";
    }

    @GetMapping(value = "/search")
    public String searchGym(){
        return "search";
    }

    @GetMapping(value = "/purchase")
    public String purchase(){
        return "purchase";
    }

    @GetMapping(value = "/gymdetail")
    public String gymDetail(){
        return "gymdetail";
    }

    @GetMapping(value = "/reservation")
    public String reservation(){
        return "reservation";
    }




    //출석체크
    @GetMapping(value = "/gym-mypage/check")
    public String check(){
        return "check";
    }


    //마이페이지
    @GetMapping(value = "/trainer-mypage")
    public String trainerMypage(){
        return "trainer-mypage";
    }

    @GetMapping(value = "/user-mypage")
    public String userMypage(){
        return "user-mypage";
    }

    @GetMapping(value = "/gym-mypage")
    public String gymMypage(){
        return "gym-mypage";
    }




    //등록*생성 (시설,트레이너,상품)
    @GetMapping(value = "/user-mypage/new-gym")
    public String newGym(){
        return "new-gym";
    }

    @GetMapping(value = "/user-mypage/new-trainer")
    public String newTrainer(){
        return "new-trainer";
    }

    @GetMapping(value = "/gym-mypage/new-product")
    public String newProductByGym(){
        return "new-products";
    }

    @GetMapping(value = "/trainer-mypage/new-product")
    public String newProductByTrainer(){
        return "new-products";
    }
}