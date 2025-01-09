package com.petstylelab.groomersunite.domain.comment;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.security.InvalidParameterException;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalFileName;

    private String storeFileName;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;


    public void modifyComment(Comment comment) {
        this.comment = comment;
    }

    public CommentImage(String originalFileName, String storeFileName, String url) {
        if (!StringUtils.hasText(originalFileName)) throw new InvalidParameterException();
        if (!StringUtils.hasText(storeFileName)) throw new InvalidParameterException();
        if (!StringUtils.hasText(url)) throw new InvalidParameterException();

        this.originalFileName = originalFileName;
        this.storeFileName = storeFileName;
        this.url = url;
    }
}
