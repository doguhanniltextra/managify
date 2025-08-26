    package com.econ.managify.controllers;

    import com.econ.managify.dtos.requests.IssuesCreateIssueRequestDto;
    import com.econ.managify.dtos.responses.*;
    import com.econ.managify.exceptions.IssueException;

    import com.econ.managify.models.Issues;
    import com.econ.managify.models.User;
    import com.econ.managify.services.IssueServiceImp;
    import com.econ.managify.services.UserServiceImp;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    import java.util.UUID;

    @RestController
    @RequestMapping("/api/issues")
    public class IssueController {
        private final IssueServiceImp issueService;
        private final UserServiceImp userService;

        public IssueController(IssueServiceImp issueService, UserServiceImp userService) {
            this.issueService = issueService;
            this.userService = userService;
        }


        @GetMapping("/{issueId}")
        public ResponseEntity<IssuesGetIssueByIdResponseDto> getIssueById(@PathVariable Long issueId) throws IssueException {
            Issues createdIssueById = issueService.getIssueById(issueId);
            IssuesGetIssueByIdResponseDto issue =  new IssuesGetIssueByIdResponseDto();

            issue.setDescription(createdIssueById.getDescription());
            issue.setPriority(createdIssueById.getPriority());
            issue.setStatus(createdIssueById.getStatus());
            issue.setTitle(createdIssueById.getTitle());
            issue.setDueDate(createdIssueById.getDueDate());

            return ResponseEntity.ok(issue);
        }

        @GetMapping("/project/{projectId}")
        public ResponseEntity<List<IssuesGetIssueByProjectIdResponseDto>> getIssueByProjectId(@PathVariable UUID projectId) throws IssueException {
            List<Issues> createdIssueByProjectId = issueService.getIssueByProjectId(projectId);

            List<IssuesGetIssueByProjectIdResponseDto> list = createdIssueByProjectId
                    .stream()
                    .map(
                            createdIssueByProjectIdMap -> {
                                IssuesGetIssueByProjectIdResponseDto issue = new IssuesGetIssueByProjectIdResponseDto();

                                issue.setDescription(createdIssueByProjectIdMap.getDescription());
                                issue.setPriority(createdIssueByProjectIdMap.getPriority());
                                issue.setStatus(createdIssueByProjectIdMap.getStatus());
                                issue.setTitle(createdIssueByProjectIdMap.getTitle());
                                issue.setDueDate(createdIssueByProjectIdMap.getDueDate());
                                return issue;
                            }).toList();

            return ResponseEntity.ok(list);
        }


        @PostMapping
        public ResponseEntity<IssuesCreateIssueResponseDto> createIssue(@RequestBody IssuesCreateIssueRequestDto issue, @RequestHeader("Authorization") String token) throws IssueException {
            User tokenUser = userService.findUserProfileByJwt(token);
            User user = userService.findUserById(tokenUser.getId());
                Issues createdIssue = issueService.createIssue(issue, tokenUser);
                IssuesCreateIssueResponseDto issueDTO = new IssuesCreateIssueResponseDto();
                issueDTO.setDescription(createdIssue.getDescription());
                issueDTO.setDueDate(createdIssue.getDueDate());
                issueDTO.setId(createdIssue.getId());
                issueDTO.setPriority(createdIssue.getPriority());
                issueDTO.setProjectID(createdIssue.getProjectID());
                issueDTO.setProject(createdIssue.getProject());
                issueDTO.setStatus(createdIssue.getStatus());
                issueDTO.setTags(createdIssue.getTags());
                issueDTO.setTitle(createdIssue.getTitle());
                issueDTO.setAssignee(createdIssue.getAssigned());


                return  ResponseEntity.ok(issueDTO);
        }

        @DeleteMapping("/{issueId}")
        public ResponseEntity<IssuesDeleteIssueResponseDto> deleteIssue(@PathVariable Long issueId, @RequestHeader("Authorization") String token) throws  IssueException {
            User user = userService.findUserProfileByJwt(token);
            issueService.deleteIssue(issueId, user.getId());
            IssuesDeleteIssueResponseDto res = new IssuesDeleteIssueResponseDto("Deleted");
            res.setMessage("Issue deleted");
            return ResponseEntity.ok(res);
        }

        @PutMapping("/{issueId}/assignee/{userId}")
        public ResponseEntity<IssuesAddUserToIssueResponseDto> addUserToIssue(@PathVariable Long issueId, @PathVariable Long userId) throws Exception{
            Issues issues = issueService.addUserToIssue(issueId,userId);
            IssuesAddUserToIssueResponseDto issuesAddUserToIssueResponseDto = new IssuesAddUserToIssueResponseDto();

            issuesAddUserToIssueResponseDto.setDescription(issues.getDescription());
            issuesAddUserToIssueResponseDto.setPriority(issues.getPriority());
            issuesAddUserToIssueResponseDto.setStatus(issues.getStatus());
            issuesAddUserToIssueResponseDto.setTitle(issues.getTitle());
            issuesAddUserToIssueResponseDto.setDueDate(issues.getDueDate());

            return ResponseEntity.ok(issuesAddUserToIssueResponseDto);
        }

        @PutMapping("/{issueId}/status/{status}")
        public ResponseEntity<IssuesUpdateIssueStatusResponseDto> updateIssueStatus(@PathVariable String status, @PathVariable Long issueId) throws Exception{
            Issues issues = issueService.updateStatus(issueId,status);

            IssuesUpdateIssueStatusResponseDto issuesUpdateIssueStatusResponseDto = new IssuesUpdateIssueStatusResponseDto();

            issuesUpdateIssueStatusResponseDto.setStatus(issues.getStatus());
            issuesUpdateIssueStatusResponseDto.setDescription(issues.getDescription());
            issuesUpdateIssueStatusResponseDto.setTitle(issues.getTitle());

            return ResponseEntity.ok(issuesUpdateIssueStatusResponseDto);
        }

    }
