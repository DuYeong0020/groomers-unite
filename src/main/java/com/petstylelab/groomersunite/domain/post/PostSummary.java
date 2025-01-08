package com.petstylelab.groomersunite.domain.post;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class PostSummary {
    private final Long id;
    private final String title;
    private final String createdBy;
    private final LocalDateTime createdAt;


    @QueryProjection
    public PostSummary(Long id, String title, String createdBy, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }
}
