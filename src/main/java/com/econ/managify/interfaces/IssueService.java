package com.econ.managify.interfaces;

import com.econ.managify.dtos.requests.IssuesCreateIssueRequestDto;
import com.econ.managify.exceptions.IssueException;
import com.econ.managify.models.Issues;
import com.econ.managify.models.User;

import java.util.List;
import java.util.UUID;

public interface IssueService {
    Issues getIssueById(Long issueId) throws IssueException;
    List<Issues> getIssueByProjectId(UUID projectId) throws IssueException;

    Issues createIssue(IssuesCreateIssueRequestDto issueRequest, User user) throws IssueException;

    void deleteIssue(Long issueId, Long userId) throws IssueException;
    Issues addUserToIssue(Long issueId, Long userId) throws IssueException;
    Issues updateStatus(Long issueId, String status) throws IssueException;
}

