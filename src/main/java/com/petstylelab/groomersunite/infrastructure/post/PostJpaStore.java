package com.petstylelab.groomersunite.infrastructure.post;

import com.petstylelab.groomersunite.domain.post.Post;
import com.petstylelab.groomersunite.domain.post.PostStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostJpaStore implements PostStore {

    private final PostJpaRepository postJpaRepository;

    @Override
    public Post storePost(Post post) {
        return postJpaRepository.save(post);
    }

    @Override
    public void deletePost(Post post) {
        postJpaRepository.delete(post);
    }
}
