package com.petstylelab.groomersunite.interfaces.post;


import com.petstylelab.groomersunite.common.argumentresolver.Login;
import com.petstylelab.groomersunite.common.response.CommonResponse;
import com.petstylelab.groomersunite.domain.post.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(("/posts"))
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public CommonResponse<PostDto.GetPostResponse> getPostById(@PathVariable Long postId) {
        PostInfo postInfo = postService.getPostById(postId);
        PostDto.GetPostResponse response = new PostDto.GetPostResponse(postInfo);
        return CommonResponse.success(response);
    }

    @GetMapping
    public CommonResponse<Page<PostDto.GetPostsResponse>> getPostsByCriteria(@Valid PostDto.GetPostsRequest request, Pageable pageable) {
        PostCriteria.GetPosts criteria = request.toCriteria(pageable);
        Page<PostSummary> postSummaryPage = postService.getPostsByCriteria(criteria);
        Page<PostDto.GetPostsResponse> response = postSummaryPage.map(PostDto.GetPostsResponse::new);
        return CommonResponse.success(response);
    }

    @PostMapping
    public CommonResponse<PostDto.CreatePostResponse> createPost(@Login Long userId, @ModelAttribute @Valid PostDto.CreatePostRequest request) {
        PostCommand.CreatePostRequest command = request.toCommand(userId);
        PostInfo postInfo = postService.createPost(command);
        PostDto.CreatePostResponse response = new PostDto.CreatePostResponse(postInfo);
        return CommonResponse.success(response);
    }

    @PatchMapping("/{postId}")
    public CommonResponse<PostDto.UpdatePostResponse> updatePost(@PathVariable Long postId,
                                                                 @ModelAttribute @Valid PostDto.UpdatePostRequest request) {
        PostCommand.UpdatePostRequest command = request.toCommand();
        PostInfo postInfo = postService.updatePost(command, postId);
        PostDto.UpdatePostResponse response = new PostDto.UpdatePostResponse(postInfo);
        return CommonResponse.success(response);
    }

    @DeleteMapping("/{postId}")
    public CommonResponse<Boolean> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return CommonResponse.success(true);
    }
}
