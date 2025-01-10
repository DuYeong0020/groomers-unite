package com.petstylelab.groomersunite.domain.comment;

public interface CommentService {

    CommentInfo getCommentById(Long postId, Long commentId);

    CommentInfo createComment(CommentCommand.CreateCommentRequest request);

    CommentInfo updateComment(CommentCommand.UpdateCommentRequest request);

}
