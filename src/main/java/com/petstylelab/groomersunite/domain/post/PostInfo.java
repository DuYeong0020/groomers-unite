package com.petstylelab.groomersunite.domain.post;

import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class PostInfo {
    private final String title;
    private final String content;
    private final Long userId;
    private final List<String> imageUrls;

    public PostInfo(Post post) {
        title = post.getTitle();
        content = post.getContent();
        userId = post.getUser().getId();
        this.imageUrls = Optional.ofNullable(post.getImages())
                .orElse(Collections.emptyList())
                .stream()
                .map(PostImage::getUrl)
                .collect(Collectors.toList());
    }
}
