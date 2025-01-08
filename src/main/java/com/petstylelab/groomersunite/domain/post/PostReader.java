package com.petstylelab.groomersunite.domain.post;

import org.springframework.data.domain.Page;

public interface PostReader {

    Post findById(Long id);

    Page<PostSummary> findByCriteria(PostCriteria.GetPosts criteria);
}
