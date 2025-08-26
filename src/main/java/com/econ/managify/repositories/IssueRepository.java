package com.econ.managify.repositories;

import com.econ.managify.models.Issues;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IssueRepository extends JpaRepository<Issues, Long> {
    public List<Issues> findByProjectId(UUID id);
}
