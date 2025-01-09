package com.petstylelab.groomersunite.infrastructure.comment;

import com.petstylelab.groomersunite.domain.comment.Comment;
import com.petstylelab.groomersunite.domain.comment.CommentStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentJpaStore implements CommentStore {

    private final CommentJpaRepository commentJpaRepository;

    @Override
    public Comment storeComment(Comment comment) {
        return commentJpaRepository.save(comment);
    }
}
