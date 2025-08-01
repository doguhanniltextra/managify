package com.econ.managify.repositories;

import com.econ.managify.models.Issues;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issues, Long> {
    public List<Issues> findByProjectId(Long id);
}
