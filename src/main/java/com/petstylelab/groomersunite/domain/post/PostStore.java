package com.petstylelab.groomersunite.domain.post;

public interface PostStore {
    Post storePost(Post post);

    void deletePost(Post post);
}
