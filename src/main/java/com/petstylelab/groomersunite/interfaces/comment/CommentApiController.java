package com.petstylelab.groomersunite.interfaces.comment;

import com.petstylelab.groomersunite.common.response.CommonResponse;
import com.petstylelab.groomersunite.domain.comment.CommentCommand;
import com.petstylelab.groomersunite.domain.comment.CommentInfo;
import com.petstylelab.groomersunite.domain.comment.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;


    @PostMapping("/posts/{postId}/comments")
    public CommonResponse<CommentDto.CreateCommentResponse> createComment(@PathVariable Long postId,
                                                                          @ModelAttribute @Valid CommentDto.CreateCommentRequest request) {
        CommentCommand.CreateCommentRequest command = request.toCommand(postId);
        CommentInfo commentInfo = commentService.createComment(command);
        CommentDto.CreateCommentResponse response = new CommentDto.CreateCommentResponse(commentInfo);
        return CommonResponse.success(response);
    }

    @PatchMapping("/posts/{postId}/comments/{commentId}")
    public CommonResponse<CommentDto.UpdateCommentResponse> updateComment(@PathVariable Long postId, @PathVariable Long commentId,
                                                                          @ModelAttribute @Valid CommentDto.UpdateCommentRequest request) {
        CommentCommand.UpdateCommentRequest command = request.toCommand(postId, commentId);
        CommentInfo commentInfo = commentService.updateComment(command);
        CommentDto.UpdateCommentResponse response = new CommentDto.UpdateCommentResponse(commentInfo);
        return CommonResponse.success(response);
    }
}
