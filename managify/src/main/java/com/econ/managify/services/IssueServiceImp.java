package com.econ.managify.services;

import com.econ.managify.dtos.requests.IssuesCreateIssueRequestDto;
import com.econ.managify.exceptions.IssueException;
import com.econ.managify.interfaces.IssueService;
import com.econ.managify.interfaces.ProjectService;
import com.econ.managify.interfaces.UserService;
import com.econ.managify.models.Issues;
import com.econ.managify.models.Project;
import com.econ.managify.models.User;
import com.econ.managify.repositories.IssueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class IssueServiceImp implements IssueService {

    private final UserService userService;
    private final IssueRepository issueRepository;
    private final ProjectService projectService;

    public IssueServiceImp(UserService userService, IssueRepository issueRepository, ProjectService projectService) {
        this.userService = userService;
        this.issueRepository = issueRepository;
        this.projectService = projectService;
    }

    @Override
    public Issues getIssueById(Long issueId) throws IssueException {
        return issueRepository.findById(issueId)
                .orElseThrow(() -> new IssueException("No Issue Found"));
    }


    @Override
    public List<Issues> getIssueByProjectId(Long projectId) throws IssueException {
        projectService.getProjectById(projectId);
        return issueRepository.findByProjectId(projectId);
    }

    @Override
    public Issues createIssue(IssuesCreateIssueRequestDto issueRequest, User user) throws IssueException {
        Project project = projectService.getProjectById(issueRequest.getProjectId());
        Issues issues = new Issues();
        issues.setTitle(issueRequest.getTitle());
        issues.setDescription(issueRequest.getDescription());
        issues.setStatus(issueRequest.getStatus());
        issues.setProjectID(issueRequest.getProjectId());
        issues.setPriority(issueRequest.getPriority());
        issues.setDueDate(issueRequest.getDueDate());

        issues.setProject(project);

        return issueRepository.save(issues);
    }

    @Override
    public void deleteIssue(Long issueId, Long userId) throws IssueException {
        getIssueById(issueId);
         issueRepository.deleteById(issueId);
    }

    @Override
    public Issues addUserToIssue(Long issueId, Long userId) throws IssueException {
        User user = userService.findUserById(userId);
        Issues issue = getIssueById(issueId);
        issue.setAssigned(user);
        return issueRepository.save(issue);
    }

    @Override
    public Issues updateStatus(Long issueId, String status) throws IssueException {
        Issues issue = getIssueById(issueId);
        issue.setStatus(status);
        return issueRepository.save(issue);
    }
}
