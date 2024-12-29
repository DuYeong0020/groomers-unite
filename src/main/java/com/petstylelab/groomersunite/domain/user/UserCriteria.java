package com.petstylelab.groomersunite.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class UserCriteria {

    @Getter
    @ToString
    public class FindUserCriteria {
        private String email;

        @Builder
        public FindUserCriteria(String email) {
            this.email = email;
        }
    }
}
