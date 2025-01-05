package com.petstylelab.groomersunite.domain.post;

public interface PostService {

    // 게시글 작성
    PostInfo createPost(PostCommand.CreatePostRequest request);
}
