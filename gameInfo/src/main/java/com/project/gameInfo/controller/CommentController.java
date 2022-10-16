package com.project.gameInfo.controller;


import com.project.gameInfo.controller.dto.CommentDto;
import com.project.gameInfo.controller.dto.CreateCommentDto;
import com.project.gameInfo.controller.dto.UpdateCommentDto;
import com.project.gameInfo.domain.*;
import com.project.gameInfo.service.CommentLikeService;
import com.project.gameInfo.service.CommentService;
import com.project.gameInfo.service.MemberService;
import com.project.gameInfo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;
    private final PostService postService;

    private final CommentLikeService commentLikeService;


    @GetMapping("/all/comment")
    public ResponseEntity<?> getPostComments(@RequestParam Long postId,
                                             @PageableDefault(size = 30) Pageable pageable) {

        List<Comment> comments = commentService.findAllByPostIdPage(postId, pageable);
        List<CommentDto> commentDtos = comments.stream().map(this::convertCommentDto)
                .collect(Collectors.toList());


        return ResponseEntity.ok(commentDtos);
    }


    @PostMapping("/user/comment")
    public ResponseEntity<?> createComment(@Valid @RequestBody CreateCommentDto commentDto, @AuthenticationPrincipal User user) {

        Comment comment;
        Member member = memberService.findMemberByMemberId(user.getUsername());
        Post post = postService.findById(commentDto.getPostId());


        if (commentDto.getParentId() != null) {
            Comment parent = commentService.findById(commentDto.getParentId());
            int groupOrder = commentService.maxGroupOrders(commentDto.getPostId(), parent.getGroupNum()) + 1;
            comment = Comment.createComment(commentDto, parent.getGroupNum(), groupOrder, post, member, parent);

        } else{
            int groupNum = commentService.maxGroupNum(commentDto.getPostId()) + 1;
            comment = Comment.createComment(commentDto, groupNum, 0, post, member, null);
        }


        commentService.save(comment);

        return ResponseEntity.ok(convertCommentDto(comment));

    }

    @PatchMapping("/user/comment")
    public ResponseEntity<?> updateComment(@Valid @RequestBody UpdateCommentDto commentDto, @AuthenticationPrincipal User user) {

        Comment comment = commentService.findById(commentDto.getId());

        if (comment.getMember().getMemberId().equals(user.getUsername())) {
            commentService.updateComment(comment, commentDto);

            return ResponseEntity.ok(convertCommentDto(comment));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("해당 작성자에 대한 접근 권한이 없습니다.");
    }


    @DeleteMapping("/user/comment")
    public ResponseEntity<?> deleteComment(@RequestParam Long commentId, @AuthenticationPrincipal User user) {

        Comment comment = commentService.findById(commentId);

        if (comment.getMember().getMemberId().equals(user.getUsername())) {
            commentService.deleteComment(comment);

            return ResponseEntity.ok("삭제가 정상적으로 이루어졌습니다.");

        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("해당 작성자에 대한 접근 권한이 없습니다.");
    }


    @PostMapping("/user/comment/like")
    public ResponseEntity<?> likeComment(@RequestParam Long commentId, @AuthenticationPrincipal User user) {

        Member member = memberService.findMemberByMemberId(user.getUsername());
        Comment comment = commentService.findById(commentId);

        Optional<CommentLike> commentLike = commentLikeService.findCommentLike(commentId, member.getId());
        Map<String, String> map = new HashMap<>();

        if (commentLike.isPresent()) {
            commentLikeService.delLike(commentLike.get());

            map.put("name", "like");
            map.put("type", "delete");
            map.put("message", "success");

            return ResponseEntity.ok(map);

        } else {
            commentLikeService.saveLike(CommentLike.createLike(member, comment));

            map.put("name", "like");
            map.put("type", "create");
            map.put("message", "success");

            return ResponseEntity.ok("like create success");

        }
    }


    @PostMapping("/user/comment/unlike")
    public ResponseEntity<?> unlikeComment(@RequestParam Long commentId, @AuthenticationPrincipal User user) {

        Member member = memberService.findMemberByMemberId(user.getUsername());
        Comment comment = commentService.findById(commentId);

        Optional<CommentDislike> commentDislike = commentLikeService.findCommentDislike(commentId, member.getId());
        Map<String, String> map = new HashMap<>();

        if (commentDislike.isPresent()) {
            commentLikeService.delDislike(commentDislike.get());

            map.put("name", "dislike");
            map.put("type", "delete");
            map.put("message", "success");

            return ResponseEntity.ok(map);

        } else {
            commentLikeService.saveDislike(CommentDislike.createUnlike(member, comment));

            map.put("name", "dislike");
            map.put("type", "create");
            map.put("message", "success");

            return ResponseEntity.ok(map);

        }

    }



    CommentDto convertCommentDto(Comment comment) {

        if(comment.getParent() != null) {

            return CommentDto.builder()
                    .id(comment.getId())
                    .parentNickname(comment.getParent().getMember().getNickname())
                    .content(comment.getContent())
                    .nickname(comment.getMember().getNickname())
                    .memberId(comment.getMember().getId())
                    .status(comment.getStatus())
                    .groupNum(comment.getGroupNum())
                    .groupOrder(comment.getGroupOrder())
                    .build();
        } else {

            return CommentDto.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .nickname(comment.getMember().getNickname())
                    .memberId(comment.getMember().getId())
                    .status(comment.getStatus())
                    .groupNum(comment.getGroupNum())
                    .groupOrder(comment.getGroupOrder())
                    .build();
        }
    }
}
