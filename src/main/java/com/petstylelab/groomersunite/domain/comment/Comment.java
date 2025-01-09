package com.petstylelab.groomersunite.domain.comment;

import com.petstylelab.groomersunite.domain.BaseEntity;
import com.petstylelab.groomersunite.domain.comment.rating.Rating;
import com.petstylelab.groomersunite.domain.post.Post;
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
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentImage> images = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "rating_id")
    private Rating rating;

    public void addImage(CommentImage image) {
        if (images == null) {
            images = new ArrayList<>();
        }
        images.add(image);
        image.modifyComment(this);
    }

    @Builder
    public Comment(String content, Post post, User user, Rating rating) {
        if (!StringUtils.hasText(content)) throw new InvalidParameterException();
        if (post == null) throw new InvalidParameterException();
        if (user == null) throw new InvalidParameterException();

        this.content = content;
        this.post = post;
        this.user = user;
        this.rating = rating;
    }
}