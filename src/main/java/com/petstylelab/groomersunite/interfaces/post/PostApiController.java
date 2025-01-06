package com.petstylelab.groomersunite.interfaces.post;


import com.petstylelab.groomersunite.common.response.CommonResponse;
import com.petstylelab.groomersunite.domain.post.PostCommand;
import com.petstylelab.groomersunite.domain.post.PostInfo;
import com.petstylelab.groomersunite.domain.post.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(("/posts"))
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    @PostMapping
    public CommonResponse<PostDto.CreatePostResponse> createPost(@ModelAttribute @Valid PostDto.CreatePostRequest request) {
        PostCommand.CreatePostRequest command = request.toCommand();
        PostInfo postInfo = postService.createPost(command);
        PostDto.CreatePostResponse response = new PostDto.CreatePostResponse(postInfo);
        return CommonResponse.success(response);
    }

    @PutMapping("/{postId}")
    public CommonResponse<PostDto.UpdatePostResponse> updatePost(@PathVariable Long postId,
                                                                 @ModelAttribute @Valid PostDto.UpdatePostRequest request) {
        PostCommand.UpdatePostRequest command = request.toCommand();
        PostInfo postInfo = postService.updatePost(command, postId);
        PostDto.UpdatePostResponse response = new PostDto.UpdatePostResponse(postInfo);
        return CommonResponse.success(response);
    }
}
