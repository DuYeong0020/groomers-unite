package com.petstylelab.groomersunite.interfaces.comment;

import com.petstylelab.groomersunite.domain.comment.CommentCommand;
import com.petstylelab.groomersunite.domain.comment.CommentInfo;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentDto {

    @Getter
    @Setter
    @ToString
    public static class CreateCommentRequest {

        @NotBlank(message = "loginId는 필수값입니다.")
        @Size(min = 5, max = 15, message = "loginId는 최소 5자, 최대 15자이어야 합니다.")
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "loginId는 영문자와 숫자만 허용됩니다.")
        private String loginId;

        @NotBlank(message = "content는 필수값입니다.")
        @Size(max = 5000, message = "content는 최대 5000자까지 작성 가능합니다.")
        private String content;

        @Size(max = 5, message = "최대 5개의 이미지만 업로드할 수 있습니다.")
        private List<MultipartFile> images;

        private RatingRequest rating;

        public CommentCommand.CreateCommentRequest toCommand(Long postId) {
            return CommentCommand.CreateCommentRequest.builder()
                    .loginId(loginId)
                    .postId(postId)
                    .content(content)
                    .imageFiles(images == null ? new ArrayList<>() : images)
                    .rating(rating == null ? null : rating.toCommand())
                    .build();
        }
        @Getter
        @Setter
        @ToString
        public static class RatingRequest {

            @DecimalMin(value = "0.0", inclusive = true, message = "completeness는 최소 0.0점이어야 합니다.")
            @DecimalMax(value = "5.0", inclusive = true, message = "completeness는 최대 5.0점이어야 합니다.")
            @Pattern(regexp = "^[0-4](\\.\\d)?|5\\.0$", message = "completeness는 0.1점 단위로 입력해야 합니다.")
            private BigDecimal completeness;

            @DecimalMin(value = "0.0", inclusive = true, message = "finish는 최소 0.0점이어야 합니다.")
            @DecimalMax(value = "5.0", inclusive = true, message = "finish는 최대 5.0점이어야 합니다.")
            @Pattern(regexp = "^[0-4](\\.\\d)?|5\\.0$", message = "finish는 0.1점 단위로 입력해야 합니다.")
            private BigDecimal finish;

            @DecimalMin(value = "0.0", inclusive = true, message = "symmetry는 최소 0.0점이어야 합니다.")
            @DecimalMax(value = "5.0", inclusive = true, message = "symmetry는 최대 5.0점이어야 합니다.")
            @Pattern(regexp = "^[0-4](\\.\\d)?|5\\.0$", message = "symmetry는 0.1점 단위로 입력해야 합니다.")
            private BigDecimal symmetry;

            @DecimalMin(value = "0.0", inclusive = true, message = "balance는 최소 0.0점이어야 합니다.")
            @DecimalMax(value = "5.0", inclusive = true, message = "balance는 최대 5.0점이어야 합니다.")
            @Pattern(regexp = "^[0-4](\\.\\d)?|5\\.0$", message = "balance는 0.1점 단위로 입력해야 합니다.")
            private BigDecimal balance;

            public CommentCommand.CreateCommentRequest.RatingRequest toCommand() {
                return CommentCommand.CreateCommentRequest.RatingRequest.builder()
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
    public static class CreateCommentResponse {
        private final Long id;
        private final Long userId;
        private final Long postId;
        private final String content;
        private final List<String> imageUrls;
        private final RatingResponse rating;

        public CreateCommentResponse(CommentInfo commentInfo) {
            this.id = commentInfo.getId();
            this.userId = commentInfo.getUserId();
            this.postId = commentInfo.getPostId();
            this.content = commentInfo.getContent();
            this.imageUrls = commentInfo.getImageUrls();
            this.rating = Optional.ofNullable(commentInfo.getRating())
                    .map(RatingResponse::new)
                    .orElse(null);
        }

        @Getter
        @ToString
        public static class RatingResponse {

            private final BigDecimal completeness;

            private final BigDecimal finish;

            private final BigDecimal symmetry;

            private final BigDecimal balance;

            public RatingResponse(CommentInfo.RatingInfo ratingInfo) {
                this.completeness = ratingInfo.getCompleteness();
                this.finish = ratingInfo.getFinish();
                this.symmetry = ratingInfo.getSymmetry();
                this.balance = ratingInfo.getBalance();
            }
        }
    }
}
