package com.petstylelab.groomersunite.domain.comment;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;
@Getter
public class CommentDetail {

    private final Long id;
    private final String content;
    private final Long userId;
    private final String imageUrls;
    private final CommentDetail.RatingDetail rating;

    @QueryProjection
    public CommentDetail(Long id, String content, Long userId, String imageUrls, RatingDetail rating) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.imageUrls = imageUrls;
        this.rating = rating;
    }

    @Getter
    public static class RatingDetail {

        private final BigDecimal completeness;
        private final BigDecimal finish;
        private final BigDecimal symmetry;
        private final BigDecimal balance;

        @QueryProjection
        public RatingDetail(BigDecimal completeness, BigDecimal finish, BigDecimal symmetry, BigDecimal balance) {
            this.completeness = completeness;
            this.finish = finish;
            this.symmetry = symmetry;
            this.balance = balance;
        }
    }
}
