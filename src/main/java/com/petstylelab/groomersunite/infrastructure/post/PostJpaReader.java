package com.petstylelab.groomersunite.infrastructure.post;

import com.petstylelab.groomersunite.domain.post.Post;
import com.petstylelab.groomersunite.domain.post.PostReader;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostJpaReader implements PostReader {

    private final PostJpaRepository postJpaRepository;

    @Override
    public Post findById(Long id) {
        return postJpaRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
