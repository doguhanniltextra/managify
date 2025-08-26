package com.econ.managify.controllers;

import com.econ.managify.dtos.requests.CommentsGetCommentsByIssueIdRequestDto;
import com.econ.managify.dtos.responses.CommentsCreateResponseDto;
import com.econ.managify.dtos.responses.CommentsDeleteResponseDto;
import com.econ.managify.exceptions.CommentException;
import com.econ.managify.interfaces.CommentService;
import com.econ.managify.interfaces.UserService;
import com.econ.managify.models.Comments;
import com.econ.managify.models.User;
import com.econ.managify.dtos.requests.CreateCommentRequest;
import com.econ.managify.services.CommentServiceImp;
import com.econ.managify.services.UserServiceImp;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentServiceImp commentService;
    private final UserServiceImp userService;

    public CommentController(CommentServiceImp commentService, UserServiceImp userService) {
        this.commentService = commentService;
        this.userService = userService;
    }


    @PostMapping()
    public ResponseEntity<CommentsCreateResponseDto> createComment(
            @Valid @RequestBody CreateCommentRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws CommentException {
        User user = userService.findUserProfileByJwt(jwt);
        Comments createdComment = commentService.createComment(req.getIssueId(), user.getId(), req.getContent());

        CommentsCreateResponseDto commentsCreateResponseDto = new CommentsCreateResponseDto();

        commentsCreateResponseDto.setContent(createdComment.getContent());
        commentsCreateResponseDto.setCreatedDateTime(createdComment.getCreatedDateTime());

        return new ResponseEntity<>(commentsCreateResponseDto, HttpStatus.OK);
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentsDeleteResponseDto> deleteComment(@PathVariable Long commentId, @RequestHeader("Authorization") String jwt) throws CommentException {
        User user = userService.findUserProfileByJwt(jwt);
        commentService.deleteComment(commentId, user.getId());

        CommentsDeleteResponseDto commentsDeleteResponseDtoResponseEntity = new CommentsDeleteResponseDto();
        return ResponseEntity.ok(commentsDeleteResponseDtoResponseEntity);
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<CommentsGetCommentsByIssueIdRequestDto>> getCommentsByIssueId(@PathVariable Long issueId) {
        List<Comments> comments = commentService.findCommentByIssue(issueId);

        List<CommentsGetCommentsByIssueIdRequestDto> response = comments.stream().map(comment -> {
            CommentsGetCommentsByIssueIdRequestDto dto = new CommentsGetCommentsByIssueIdRequestDto();
            dto.setContent(comment.getContent());
            dto.setCreatedDateTime(comment.getCreatedDateTime());
            dto.setCommenterName(comment.getUser().getFullName()); // veya getUsername()
            return dto;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
