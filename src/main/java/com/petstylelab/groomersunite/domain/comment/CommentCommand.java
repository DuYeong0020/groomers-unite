package com.petstylelab.groomersunite.domain.comment;

import com.petstylelab.groomersunite.domain.comment.rating.Rating;
import com.petstylelab.groomersunite.domain.post.Post;
import com.petstylelab.groomersunite.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public class CommentCommand {

    @Getter
    @ToString
    public static class CreateCommentRequest {
        private final String loginId;
        private final Long postId;
        private final String content;
        private final List<MultipartFile> imageFiles;
        private final RatingRequest rating;

        @Builder
        public CreateCommentRequest(String loginId, Long postId, String content, List<MultipartFile> imageFiles, RatingRequest rating) {
            this.loginId = loginId;
            this.postId = postId;
            this.content = content;
            this.imageFiles = imageFiles;
            this.rating = rating;
        }

        Comment toEntity(Post post, User user) {
            return Comment.builder()
                    .user(user)
                    .post(post)
                    .content(content)
                    .rating(rating == null ? null : rating.toEntity())
                    .build();
        }

        @Getter
        @ToString
        public static class RatingRequest {
            private final BigDecimal completeness;
            private final BigDecimal finish;
            private final BigDecimal symmetry;
            private final BigDecimal balance;

            @Builder
            public RatingRequest(BigDecimal completeness, BigDecimal finish, BigDecimal symmetry, BigDecimal balance) {
                this.completeness = completeness;
                this.finish = finish;
                this.symmetry = symmetry;
                this.balance = balance;
            }

            Rating toEntity() {
                return Rating.builder()
                        .completeness(completeness)
                        .finish(finish)
                        .symmetry(symmetry)
                        .balance(balance)
                        .build();
            }
        }
    }
    @Getter
    @ToString
    public static class UpdateCommentRequest {
        private final String loginId;
        private final Long postId;
        private final Long commentId;
        private final String content;
        private final List<String> deleteImageNames;
        private final List<MultipartFile> newImages;
        private final RatingRequest rating;

        @Builder
        public UpdateCommentRequest(String loginId, Long postId, long commentId, String content, List<String> deleteImageNames, List<MultipartFile> newImages, RatingRequest rating) {
            this.loginId = loginId;
            this.postId = postId;
            this.commentId = commentId;
            this.content = content;
            this.deleteImageNames = deleteImageNames;
            this.newImages = newImages;
            this.rating = rating;
        }

        @Getter
        @ToString
        public static class RatingRequest {
            private final BigDecimal completeness;
            private final BigDecimal finish;
            private final BigDecimal symmetry;
            private final BigDecimal balance;

            @Builder
            public RatingRequest(BigDecimal completeness, BigDecimal finish, BigDecimal symmetry, BigDecimal balance) {
                this.completeness = completeness;
                this.finish = finish;
                this.symmetry = symmetry;
                this.balance = balance;
            }

            Rating toEntity() {
                return Rating.builder()
                        .completeness(completeness)
                        .finish(finish)
                        .symmetry(symmetry)
                        .balance(balance)
                        .build();
            }
        }
    }
}
