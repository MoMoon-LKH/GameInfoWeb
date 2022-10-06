package com.project.gameInfo.controller;

import com.project.gameInfo.controller.dto.*;
import com.project.gameInfo.domain.*;
import com.project.gameInfo.repository.GamesGenreRepository;
import com.project.gameInfo.service.*;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    private final MemberService memberService;

    private final CategoryService categoryService;
    private final GamesService gamesService;
    private final CommentService commentService;
    private final GamesPlatformService gamesPlatformService;
    private final GamesGenreService gamesGenreService;


    @PostMapping("/user/post")
    public ResponseEntity<?> createPost(@RequestBody CreatePostDto postDto, @AuthenticationPrincipal User user) {

        Member member = memberService.findMemberByMemberId(user.getUsername());
        Category category = categoryService.findByCategoryId(postDto.getCategoryId());

        if (postDto.getMemberId().equals(member.getId())) {

            Post post;

            if(postDto.getGameId() != null){
                Games games = gamesService.findById(postDto.getGameId());
                post = Post.createPost(postDto, category, member, games);
            } else {
                post = Post.createPostNotGames(postDto, category, member);
            }

            postService.save(post);

            return ResponseEntity.ok(new PostDto(post));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
    }


    @GetMapping("/all/post/{postId}")
    public ResponseEntity<?> getPost(@PathVariable("postId") Long id) {
        Post post = postService.findById(id);
        postService.addView(post);

        Map<String, Object> map = new HashMap<>();
        map.put("post", new PostDto(post));
        map.put("category", new CategoryDto(post.getCategory()));
        map.put("member", new MemberDto(post.getMember()));

        if(post.getGames() != null) {
            GamesDto gamesDto = new GamesDto(post.getGames());
            gamesDto.setGenres(gamesGenreService.findGenresByGamesId(gamesDto.getId()));
            gamesDto.setPlatform(gamesPlatformService.findPlatformsByGamesId(gamesDto.getId()));
            map.put("game", gamesDto);
        }
        return ResponseEntity.ok(map);
    }


    @GetMapping("/all/post/list")
    public ResponseEntity<?> getPostList(
            @RequestParam Long categoryId,
            @RequestParam(required = false) Long gameId, @PageableDefault Pageable pageable){

        List<PostListDto> postList = postService.findAllByCategoryAndGamePage(categoryId, gameId, pageable);
        Long total = postService.countByCategoryIdAndGamesId(categoryId, gameId);

        Map<String, Object> map = new HashMap<>();
        map.put("posts", postList);
        map.put("total", total);


        return ResponseEntity.ok(map);
    }


    @GetMapping("/all/post/main")
    public ResponseEntity<?> getNewsList(
            @RequestParam Long categoryId,
            @PageableDefault(size = 5) Pageable pageable) {

        List<PostListDto> list = postService.findAllByCategory(categoryId, pageable);

        return ResponseEntity.ok(list);
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
    public ResponseEntity<?> deletePost(@RequestParam Long id, @AuthenticationPrincipal User user) {

        Post post = postService.findById(id);

        if (user.getUsername().equals(post.getMember().getMemberId())) {
            List<Comment> comments = commentService.findAllByPostId(post.getId());
            commentService.deletePostComment(comments);
            postService.delete(post);

            return ResponseEntity.ok("삭제 완료");
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("해당 게시물에 대한 권한이 없습니다.");
    }


}
