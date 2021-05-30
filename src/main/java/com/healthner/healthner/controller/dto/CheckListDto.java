package com.healthner.healthner.controller.dto;

import com.healthner.healthner.domain.CheckList;
import com.healthner.healthner.domain.CheckListStatus;
import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.healthner.healthner.domain.CheckListStatus.OUT;


public class CheckListDto {
    @Data
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    @ToString
    public static class Request {
        private CheckListStatus status;
        private Long userId;
        private Long gymId;
        private String phoneNumber;

        public CheckList toEntity(User user, Gym gym) {
            return CheckList.createCheckList(OUT, user, gym);
        }
    }

    @Data
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    public static class Response {
        private CheckListStatus status;
        private UserDto.Response user;
        private GymDto.Response gym;
        private Long userCount;
        private Long userTotal;

        public Response(CheckList checkList) {
            this.status = checkList.getStatus();
            this.user = new UserDto.Response(checkList.getUser());
            this.gym = new GymDto.Response(checkList.getGym());
        }

        public Response(CheckList checkList, Long userCount, Long userTotal) {
            this.status = checkList.getStatus();
            this.user = new UserDto.Response(checkList.getUser());
            this.gym = new GymDto.Response(checkList.getGym());
            this.userCount = userCount;
            this.userTotal = userTotal;
        }
    }
}