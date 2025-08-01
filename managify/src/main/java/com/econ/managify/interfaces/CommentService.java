package com.econ.managify.interfaces;


import com.econ.managify.exceptions.CommentException;
import com.econ.managify.models.Comments;

import java.util.List;

public interface CommentService {
    Comments createComment(Long issueId, Long userId, String comment) throws CommentException;
    void deleteComment(Long commentId, Long userId) throws CommentException;
    List<Comments> findCommentByIssue(Long issueId);
}
