package com.project.gameInfo.controller;


import com.project.gameInfo.controller.dto.CommentDto;
import com.project.gameInfo.controller.dto.CreateCommentDto;
import com.project.gameInfo.domain.Comment;
import com.project.gameInfo.domain.Member;
import com.project.gameInfo.domain.Post;
import com.project.gameInfo.service.CommentService;
import com.project.gameInfo.service.MemberService;
import com.project.gameInfo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;
    private final PostService postService;



    @GetMapping("/all/comment/{postId}")
    public ResponseEntity<?> getPostComments(@PathVariable("postId") Long postId) {

        List<Comment> comments = commentService.findAllByPostId(postId);
        List<CommentDto> commentDtos = new ArrayList<>();

        for (Comment comment : comments) {
            commentDtos.add(convertCommentDto(comment));
        }

        return ResponseEntity.ok(commentDtos);
    }


    @PostMapping("/user/comment")
    public ResponseEntity<?> createComment(@RequestBody CreateCommentDto commentDto, @AuthenticationPrincipal User user) {

        Member member = memberService.findMemberByMemberId(user.getUsername());
        Post post = postService.findById(commentDto.getPostId());

        if (user.getUsername().equals(member.getMemberId())) {
            Comment comment = Comment.createComment(commentDto, post, member);
            commentService.save(comment);

            return ResponseEntity.ok(convertCommentDto(comment));

        } else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("작성자와 해당 유저가 다릅니다.");
        }

    }

    @PutMapping("/user/comment")
    public ResponseEntity<?> updateComment(@RequestBody CommentDto commentDto, @AuthenticationPrincipal User user) {

        Member member = memberService.findMemberByMemberId(user.getUsername());

        if (user.getUsername().equals(member.getMemberId())) {
            Comment comment = commentService.findById(commentDto.getId());
            commentService.updateComment(comment, commentDto);

            return ResponseEntity.ok(convertCommentDto(comment));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("해당 작성자에 대한 접근 권한이 없습니다.");
    }


    @DeleteMapping("/user/comment")
    public ResponseEntity<?> deleteComment(@RequestBody CommentDto commentDto, @AuthenticationPrincipal User user) {

        Member member = memberService.findMemberByMemberId(user.getUsername());

        if (user.getUsername().equals(member.getMemberId())) {
            Comment comment = commentService.findById(commentDto.getId());
            commentService.deleteComment(comment);

            return ResponseEntity.ok("삭제가 정상적으로 이루어졌습니다.");

        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("해당 작성자에 대한 접근 권한이 없습니다.");
    }



    CommentDto convertCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .replyId(comment.getReplyId())
                .content(comment.getContent())
                .nickname(comment.getMember().getNickname())
                .memberId(comment.getMember().getId())
                .status(comment.getStatus().toString())
                .build();
    }
}
