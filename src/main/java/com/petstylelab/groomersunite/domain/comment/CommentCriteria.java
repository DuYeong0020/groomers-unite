package com.petstylelab.groomersunite.domain.comment;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

public class CommentCriteria {
    @Getter
    @Setter
    public static class GetComments {

        private final Long postId;
        private final Pageable pageable;

        public GetComments(Long postId, Pageable pageable) {
            this.postId = postId;
            this.pageable = pageable;
        }
    }
}
