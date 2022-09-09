package com.project.gameInfo.controller;

import com.project.gameInfo.controller.dto.CreatePostDto;
import com.project.gameInfo.controller.dto.PostDto;
import com.project.gameInfo.controller.dto.PostListDto;
import com.project.gameInfo.domain.Category;
import com.project.gameInfo.domain.Comment;
import com.project.gameInfo.domain.Member;
import com.project.gameInfo.domain.Post;
import com.project.gameInfo.service.CategoryService;
import com.project.gameInfo.service.CommentService;
import com.project.gameInfo.service.MemberService;
import com.project.gameInfo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    private final MemberService memberService;

    private final CategoryService categoryService;

    private final CommentService commentService;


    @PostMapping("/user/post")
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto, @AuthenticationPrincipal User user) {

        Member member = memberService.findMemberByMemberId(user.getUsername());
        Category category = categoryService.findByCategoryId(postDto.getCategoryId());

        if (user.getUsername().equals(member.getMemberId())) {

            Post post = Post.createPost(postDto, category, member);

            postService.save(post);

            return ResponseEntity.ok(new PostDto(post));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("해당 유저와 작성자가 다르므로 권한이 없습니다.");
    }


    @GetMapping("/all/post/{postId}")
    public ResponseEntity<?> getPost(@PathVariable("postId") Long id) {

        return ResponseEntity.ok(new PostDto(postService.findById(id)));
    }


    @GetMapping("/all/post/list")
    public ResponseEntity<?> getPostList(
            @RequestParam Long categoryId,
            @RequestParam Long gameId, @PageableDefault Pageable pageable){

        List<PostListDto> postList = postService.findAllByCategoryAndGamePage(categoryId, gameId, pageable);

        return ResponseEntity.ok(postList);
    }


    @PutMapping("/user/post")
    public ResponseEntity<?> updatePost(@RequestBody PostDto postDto,
                                        @AuthenticationPrincipal User user) {

        Member member = memberService.findMemberByMemberId(user.getUsername());

        if (member.getId().equals(postDto.getMemberId())) {
            Post post = postService.findById(postDto.getId());

            postService.updatePost(post, postDto);

            return ResponseEntity.ok(new PostDto(post));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("해당 게시물에 대한 권한이 없습니다.");

    }


    @DeleteMapping("/user/post")
    public ResponseEntity<?> deletePost(@RequestBody PostDto postDto, @AuthenticationPrincipal User user) {

        Member member = memberService.findMemberByMemberId(user.getUsername());

        if (member.getId().equals(postDto.getMemberId())) {
            Post post = postService.findById(postDto.getId());
            List<Comment> comments = commentService.findAllByPostId(postDto.getId());
            commentService.deletePostComment(comments);
            postService.delete(post);

            return ResponseEntity.ok("삭제 완료");
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("해당 게시물에 대한 권한이 없습니다.");
    }


}
