package com.petstylelab.groomersunite.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    @Override
    @Transactional
    public PostInfo createPost(PostCommand.CreatePostRequest request) {
        return null;
    }
}
