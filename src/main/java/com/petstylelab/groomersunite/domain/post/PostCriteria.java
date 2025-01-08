package com.petstylelab.groomersunite.domain.post;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Pageable;

public class PostCriteria {

    @Getter
    @ToString
    public static class GetPosts {

        private final Pageable pageable;
        private final String keyword;

        @Builder
        public GetPosts(Pageable pageable, String keyword) {
            this.pageable = pageable;
            this.keyword = keyword;
        }
    }
}
