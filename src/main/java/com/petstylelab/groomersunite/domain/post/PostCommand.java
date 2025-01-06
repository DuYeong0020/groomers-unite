package com.petstylelab.groomersunite.domain.post;


import com.petstylelab.groomersunite.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PostCommand {

    @Getter
    @ToString
    public static class CreatePostRequest {
        private final String loginId;
        private final String title;
        private final String content;
        private final List<MultipartFile> imageFiles;

        @Builder
        public CreatePostRequest(String loginId, String title, String content, List<MultipartFile> imageFiles) {
            this.loginId = loginId;
            this.title = title;
            this.content = content;
            this.imageFiles = imageFiles;
        }

        public Post toEntity(User user) {
            return Post.builder()
                    .title(this.title)
                    .content(this.content)
                    .user(user)
                    .build();
        }
    }

    @Getter
    @ToString
    public static class UpdatePostRequest {
        private final String loginId;
        private final String title;
        private final String content;
        private final List<String> deleteImageUrls;
        private final List<MultipartFile> newImages;

        @Builder
        public UpdatePostRequest(String loginId, String title, String content, List<String> deleteImageUrls, List<MultipartFile> newImages) {
            this.loginId = loginId;
            this.title = title;
            this.content = content;
            this.deleteImageUrls = deleteImageUrls;
            this.newImages = newImages;
        }
    }
}
