package com.petstylelab.groomersunite.domain.comment;

import org.springframework.data.domain.Page;

public interface CommentService {

    CommentInfo getCommentById(Long postId, Long commentId);

    Page<CommentDetail> getCommentsByCriteria(CommentCriteria.GetComments criteria);

    CommentInfo createComment(CommentCommand.CreateCommentRequest request);

    CommentInfo updateComment(CommentCommand.UpdateCommentRequest request);

}
