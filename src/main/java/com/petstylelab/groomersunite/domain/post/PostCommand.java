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
        private final Long userId;
        private final String title;
        private final String content;
        private final List<MultipartFile> imageFiles;

        @Builder
        public CreatePostRequest(Long userId, String title, String content, List<MultipartFile> imageFiles) {
            this.userId = userId;
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
        private final String title;
        private final String content;
        private final List<String> deleteImageNames;
        private final List<MultipartFile> newImages;

        @Builder
        public UpdatePostRequest(String title, String content, List<String> deleteImageNames, List<MultipartFile> newImages) {
            this.title = title;
            this.content = content;
            this.deleteImageNames = deleteImageNames;
            this.newImages = newImages;
        }
    }
}
