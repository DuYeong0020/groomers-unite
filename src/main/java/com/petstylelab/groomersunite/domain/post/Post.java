package com.petstylelab.groomersunite.domain.post;

import com.petstylelab.groomersunite.domain.BaseEntity;
import com.petstylelab.groomersunite.domain.comment.Comment;
import com.petstylelab.groomersunite.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public void modifyTitle(String title) {
        if (StringUtils.hasText(title)) {
            this.title = title;
        }
    }

    public void modifyContent(String content) {
        if (StringUtils.hasText(content)) {
            this.content = content;
        }
    }

    public void addImage(PostImage image) {
        if (images == null) {
            images = new ArrayList<>();
        }
        images.add(image);
        image.modifyPost(this);
    }

    public void removeImage(PostImage image) {
        images.remove(image);
        image.modifyPost(null);
    }

    @Builder
    public Post(String title, String content, User user) {
        if (!StringUtils.hasText(title)) throw new InvalidParameterException();
        if (!StringUtils.hasText(content)) throw new InvalidParameterException();
        if (user == null) throw new InvalidParameterException();

        this.title = title;
        this.content = content;
        this.user = user;
    }
}
