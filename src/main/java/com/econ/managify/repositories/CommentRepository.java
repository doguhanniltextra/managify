package com.econ.managify.repositories;

import com.econ.managify.models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findByIssuesId(Long issueId);
}
