package com.healthner.healthner.controller.api;

import com.healthner.healthner.service.CheckListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CheckListApiController {

    private final CheckListService checkListService;
    
    @GetMapping("check-list/gyms/{gymId}")
    public Long getCheckList(@PathVariable("gymId") Long gymId) {
        return checkListService.users(gymId);
    }
}