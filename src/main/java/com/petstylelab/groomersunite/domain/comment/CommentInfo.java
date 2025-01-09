package com.petstylelab.groomersunite.domain.comment;

import com.petstylelab.groomersunite.domain.comment.rating.Rating;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class CommentInfo {

    private final Long id;
    private final Long postId;
    private final String content;
    private final Long userId;
    private final List<String> imageUrls;
    private final RatingInfo rating;

    public CommentInfo(Comment comment) {
        id = comment.getId();
        postId = comment.getPost().getId();
        content = comment.getContent();
        userId = comment.getUser().getId();
        imageUrls = Optional.ofNullable(comment.getImages())
                .orElse(Collections.emptyList())
                .stream()
                .map(CommentImage::getUrl)
                .collect(Collectors.toList());
        rating = Optional.ofNullable(comment.getRating())
                .map(RatingInfo::new)
                .orElse(null);
    }

    @Getter
    public static class RatingInfo {
        private final BigDecimal completeness;

        private final BigDecimal finish;

        private final BigDecimal symmetry;

        private final BigDecimal balance;

        public RatingInfo(Rating rating) {
            this.completeness = rating.getCompleteness();
            this.finish = rating.getFinish();
            this.symmetry = rating.getSymmetry();
            this.balance = rating.getBalance();
        }
    }

}
