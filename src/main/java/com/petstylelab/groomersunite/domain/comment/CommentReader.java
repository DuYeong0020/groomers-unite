package com.petstylelab.groomersunite.domain.comment;

import org.springframework.data.domain.Page;

public interface CommentReader {

    Comment findById(Long id);

    Page<CommentDetail> findByCriteria(CommentCriteria.GetComments criteria);
}
