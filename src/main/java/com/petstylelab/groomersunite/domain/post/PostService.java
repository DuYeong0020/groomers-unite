package com.petstylelab.groomersunite.domain.post;

import org.springframework.data.domain.Page;

public interface PostService {

    // 단일 게시글 조회
    PostInfo getPostById(Long postId);

    // 게시글목록 조회
    Page<PostSummary> getPostsByCriteria(PostCriteria.GetPosts criteria);

    // 게시글 작성
    PostInfo createPost(PostCommand.CreatePostRequest request);

    // 게시글 수정
    PostInfo updatePost(PostCommand.UpdatePostRequest request, Long postId);

    // 게시글 삭제
    void deletePost(Long postId);
}
