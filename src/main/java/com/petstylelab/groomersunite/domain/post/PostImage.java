package com.petstylelab.groomersunite.domain.post;

import com.petstylelab.groomersunite.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.security.InvalidParameterException;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalFileName;

    private String storeFileName;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public void modifyPost(Post post) {
        this.post = post;
    }

    public PostImage(String originalFileName, String storeFileName, String url) {
        if (!StringUtils.hasText(originalFileName)) throw new InvalidParameterException();
        if (!StringUtils.hasText(storeFileName)) throw new InvalidParameterException();
        if (!StringUtils.hasText(url)) throw new InvalidParameterException();

        this.originalFileName = originalFileName;
        this.storeFileName = storeFileName;
        this.url = url;
    }
}