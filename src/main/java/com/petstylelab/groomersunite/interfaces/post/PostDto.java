package com.petstylelab.groomersunite.interfaces.post;

import com.petstylelab.groomersunite.domain.post.PostCommand;
import com.petstylelab.groomersunite.domain.post.PostInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

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

        @NotBlank(message = "loginId는 필수값입니다.")
        @Size(min = 5, max = 15, message = "loginId는 최소 5자, 최대 15자이어야 합니다.")
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "loginId는 영문자와 숫자만 허용됩니다.")
        private String loginId;

        @NotBlank(message = "title은 필수값입니다.")
        @Size(min = 1, max = 20, message = "title은 최소 1자, 최대 20자이어야 합니다.")
        private String title;

        @NotBlank(message = "content는 필수값입니다.")
        @Size(max = 5000, message = "content는 최대 5000자까지 작성 가능합니다.")
        private String content;

        @Size(max = 5, message = "최대 5개의 이미지만 업로드할 수 있습니다.")
        private List<MultipartFile> images;

        public PostCommand.CreatePostRequest toCommand() {
            return PostCommand.CreatePostRequest.builder()
                    .loginId(loginId)
                    .title(title)
                    .content(content)
                    .imageFiles(images == null ? new ArrayList<>() : images)
                    .build();
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

        @NotBlank(message = "loginId는 필수값입니다.")
        @Size(min = 5, max = 15, message = "loginId는 최소 5자, 최대 15자이어야 합니다.")
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "loginId는 영문자와 숫자만 허용됩니다.")
        private String loginId;

        @NotBlank(message = "title은 필수값입니다.")
        @Size(min = 1, max = 20, message = "title은 최소 1자, 최대 20자이어야 합니다.")
        private String title;

        @NotBlank(message = "content는 필수값입니다.")
        @Size(max = 5000, message = "content는 최대 5000자까지 작성 가능합니다.")
        private String content;

        private List<String> deleteImageUrls;

        @Size(max = 5, message = "최대 5개의 이미지만 업로드할 수 있습니다.")
        private List<MultipartFile> newImages;

        public PostCommand.UpdatePostRequest toCommand() {
            return PostCommand.UpdatePostRequest.builder()
                    .loginId(loginId)
                    .title(title)
                    .content(content)
                    .deleteImageUrls(deleteImageUrls == null ? new ArrayList<>() : deleteImageUrls)
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
