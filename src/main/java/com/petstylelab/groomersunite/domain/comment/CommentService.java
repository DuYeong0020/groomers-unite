package com.petstylelab.groomersunite.domain.comment;

public interface CommentService {

    CommentInfo createComment(CommentCommand.CreateCommentRequest request);
}
