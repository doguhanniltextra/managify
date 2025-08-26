package com.econ.managify.services;

import com.econ.managify.exceptions.CommentException;
import com.econ.managify.interfaces.CommentService;
import com.econ.managify.models.Comments;
import com.econ.managify.models.Issues;
import com.econ.managify.models.User;
import com.econ.managify.repositories.CommentRepository;
import com.econ.managify.repositories.IssueRepository;
import com.econ.managify.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImp  {

    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    public CommentServiceImp(CommentRepository commentRepository, IssueRepository issueRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
    }


    public Comments createComment(Long issueId, Long userId, String content) throws CommentException {
        Optional<Issues> issuesOptional = issueRepository.findById(issueId);
        Optional<User> userOptional = userRepository.findById(userId);

        if(issuesOptional.isEmpty()) {throw new CommentException("Issue Not Found");}
        if(userOptional.isEmpty()) {throw new CommentException("User Not Found");}
        Issues issues = issuesOptional.get();
        User user = userOptional.get();
        Comments comments = new Comments();
        comments.setIssues(issues);
        comments.setUser(user);
        comments.setCreatedDateTime(LocalDate.from(LocalDateTime.now()));
        comments.setContent(content);
        Comments savedComment = commentRepository.save(comments);
        issues.getComments().add(savedComment);
        return savedComment;
    }


    public void deleteComment(Long commentId, Long userId) throws CommentException {
        Optional<Comments> commentsOptional = commentRepository.findById(commentId);
        Optional<User> userOptional = userRepository.findById(userId);

        if(commentsOptional.isEmpty()) {throw new CommentException("Comment Not Found");}
        if(userOptional.isEmpty()) {throw new CommentException("User Not Found");}

        Comments comments = commentsOptional.get();
        User user = userOptional.get();

        if(comments.getUser().equals(user)) {commentRepository.delete(comments);}
        else {throw new CommentException("User does not have permission to delete this comment");}
    }

    public List<Comments> findCommentByIssue(Long issueId) {
        return commentRepository.findByIssuesId(issueId);
    }
}
