package com.project.gameInfo.controller;


import com.project.gameInfo.controller.dto.CommentDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;
    private final PostService postService;


    @PostMapping("/comment")
    public ResponseEntity<?> saveComment(@RequestBody CommentDto commentDto, @AuthenticationPrincipal User user) {

        Member member = memberService.findMemberByMemberId(user.getUsername());
        Post post = postService.findById(commentDto.getPostId());

        if (commentDto.getMemberId().equals(member.getId())) {
            Comment comment = Comment.createComment(commentDto, post, member);
            commentService.save(comment);

            return ResponseEntity.ok(convertCommentDto(comment));

        } else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("해당 권한이 없습니다.");
        }

    }


    CommentDto convertCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .replyId(comment.getReplyId())
                .content(comment.getContent())
                .nickname(comment.getMember().getNickname())
                .memberId(comment.getMember().getId())
                .postId(comment.getPost().getId())
                .build();
    }
}
