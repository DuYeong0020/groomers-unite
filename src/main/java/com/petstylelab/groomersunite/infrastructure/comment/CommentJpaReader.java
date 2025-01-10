package com.petstylelab.groomersunite.infrastructure.comment;

import com.petstylelab.groomersunite.domain.comment.Comment;
import com.petstylelab.groomersunite.domain.comment.CommentReader;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentJpaReader implements CommentReader {

    private final CommentJpaRepository commentJpaRepository;

    @Override
    public Comment findById(Long id) {
        return commentJpaRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
