package com.petstylelab.groomersunite.domain.post;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PostCommand {

    @Getter
    @ToString
    public static class CreatePostRequest {
        private final String title;
        private final String content;
        private final List<MultipartFile> imageFiles;

        @Builder
        public CreatePostRequest(String title, String content, List<MultipartFile> imageFiles) {
            this.title = title;
            this.content = content;
            this.imageFiles = imageFiles;
        }
    }
}
