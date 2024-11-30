package com.petstylelab.groomersunite.domain;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class BaseEntity {

    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private String created_by;
    private String updated_by;
}