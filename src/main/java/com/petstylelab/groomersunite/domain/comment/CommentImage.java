package com.petstylelab.groomersunite.domain.comment;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class CommentImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
