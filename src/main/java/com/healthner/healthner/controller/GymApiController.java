package com.healthner.healthner.controller;

import com.healthner.healthner.controller.dto.GymDto;
import com.healthner.healthner.service.GymService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GymApiController {

    private final GymService gymService;

    @GetMapping("gyms/address/{addressKeyword}")
    public ResponseEntity<Result> list(@PathVariable("addressKeyword") String addressKeyword) {
        List<GymDto.Response> gyms = gymService.findByAddress(addressKeyword);
        return new ResponseEntity<>(new Result(gyms), HttpStatus.OK);
    }

    @Getter
    @AllArgsConstructor
    public class Result<T> {
        private T data;
    }
}