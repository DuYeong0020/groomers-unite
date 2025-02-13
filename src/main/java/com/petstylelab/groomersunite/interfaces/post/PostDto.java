package com.petstylelab.groomersunite.interfaces.post;

import com.petstylelab.groomersunite.domain.post.PostCommand;
import com.petstylelab.groomersunite.domain.post.PostCriteria;
import com.petstylelab.groomersunite.domain.post.PostInfo;
import com.petstylelab.groomersunite.domain.post.PostSummary;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostDto {

    @Getter
    @ToString
    public static class GetPostResponse {
        private final String title;
        private final String content;
        private final Long userId;
        private final List<String> imageUrls;

        public GetPostResponse(PostInfo postInfo) {
            this.title = postInfo.getTitle();
            this.content = postInfo.getContent();
            this.userId = postInfo.getUserId();
            this.imageUrls = postInfo.getImageUrls();
        }
    }

    @Getter
    @Setter
    @ToString
    public static class CreatePostRequest {

        @NotBlank(message = "title은 필수값입니다.")
        @Size(min = 1, max = 20, message = "title은 최소 1자, 최대 20자이어야 합니다.")
        private String title;

        @NotBlank(message = "content는 필수값입니다.")
        @Size(max = 5000, message = "content는 최대 5000자까지 작성 가능합니다.")
        private String content;

        @Size(max = 5, message = "최대 5개의 이미지만 업로드할 수 있습니다.")
        private List<MultipartFile> images;

        public PostCommand.CreatePostRequest toCommand(Long userId) {
            return PostCommand.CreatePostRequest.builder()
                    .userId(userId)
                    .title(title)
                    .content(content)
                    .imageFiles(images == null ? new ArrayList<>() : images)
                    .build();
        }
    }

    @Getter
    @ToString
    public static class GetPostsRequest {

        private String keyword;

        public PostCriteria.GetPosts toCriteria(Pageable pageable) {
            return PostCriteria.GetPosts.builder()
                    .keyword(keyword)
                    .pageable(pageable)
                    .build();
        }
    }

    @Getter
    @ToString
    public static class GetPostsResponse {
        private final Long id;
        private final String title;
        private final String createdBy;
        private final LocalDateTime createdAt;

        public GetPostsResponse(PostSummary postSummary) {
            this.id = postSummary.getId();
            this.title = postSummary.getTitle();
            this.createdBy = postSummary.getCreatedBy();
            this.createdAt = postSummary.getCreatedAt();
        }
    }

    @Getter
    @ToString
    public static class CreatePostResponse {
        private final String title;
        private final String content;
        private final Long userId;
        private final List<String> imageUrls;

        public CreatePostResponse(PostInfo postInfo) {
            this.title = postInfo.getTitle();
            this.content = postInfo.getContent();
            this.userId = postInfo.getUserId();
            this.imageUrls = postInfo.getImageUrls();
        }
    }

    @Getter
    @Setter
    @ToString
    public static class UpdatePostRequest {

        @NotBlank(message = "title은 필수값입니다.")
        @Size(min = 1, max = 20, message = "title은 최소 1자, 최대 20자이어야 합니다.")
        private String title;

        @NotBlank(message = "content는 필수값입니다.")
        @Size(max = 5000, message = "content는 최대 5000자까지 작성 가능합니다.")
        private String content;

        private List<String> deleteImageNames;

        @Size(max = 5, message = "최대 5개의 이미지만 업로드할 수 있습니다.")
        private List<MultipartFile> newImages;

        public PostCommand.UpdatePostRequest toCommand() {
            return PostCommand.UpdatePostRequest.builder()
                    .title(title)
                    .content(content)
                    .deleteImageNames(deleteImageNames == null ? new ArrayList<>() : deleteImageNames)
                    .newImages(newImages  == null ? new ArrayList<>() : newImages)
                    .build();
        }
    }

    @Getter
    @ToString
    public static class UpdatePostResponse {
        private final String title;
        private final String content;
        private final Long userId;
        private final List<String> imageUrls;

        public UpdatePostResponse(PostInfo postInfo) {
            this.title = postInfo.getTitle();
            this.content = postInfo.getContent();
            this.userId = postInfo.getUserId();
            this.imageUrls = postInfo.getImageUrls();
        }
    }
}
