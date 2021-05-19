package com.healthner.healthner.controller.dto;

import com.healthner.healthner.domain.CheckList;
import com.healthner.healthner.domain.CheckListStatus;
import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class CheckListDto {
    @Data
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    @ToString
    public static class Request {
        private CheckListStatus status;
        private Long userId;
        private Long gymId;
        private String email;

        public CheckList toEntity(User user, Gym gym) {
            return CheckList.createCheckList(this.getStatus(), user, gym);
        }
    }

    @Data
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    public static class Response {
        private CheckListStatus status;
        private Long userId;
        private Long gymId;

        public Response(CheckList checkList) {
            this.userId = checkList.getUser().getId();
            this.gymId = checkList.getGym().getId();
        }
    }
}
