package com.econ.managify.controllers;

import com.econ.managify.dtos.requests.ProjectsUpdateProjectRequestDto;
import com.econ.managify.dtos.responses.*;
import com.econ.managify.exceptions.AuthException;
import com.econ.managify.exceptions.InvatitionException;
import com.econ.managify.interfaces.InvitationService;
import com.econ.managify.interfaces.ProjectService;
import com.econ.managify.interfaces.UserService;
import com.econ.managify.models.Chat;
import com.econ.managify.models.Invitation;
import com.econ.managify.models.Project;
import com.econ.managify.models.User;
import com.econ.managify.dtos.requests.InviteRequest;
import com.econ.managify.services.InvitationServiceImp;
import com.econ.managify.services.ProjectServiceImp;
import com.econ.managify.services.UserServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final InvitationServiceImp invitationService;
    private final ProjectServiceImp projectService;
    private final UserServiceImp userService;

    public ProjectController(InvitationServiceImp invitationService, ProjectServiceImp projectService, UserServiceImp userService) {
        this.invitationService = invitationService;
        this.projectService = projectService;
        this.userService = userService;
    }


    @GetMapping()
    @Cacheable(value = "projects", key = "#user.id + '_' + #category + '_' + #tag")
    public ResponseEntity<List<ProjectsGetProjectsResponseDto>> getProjects(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tag,
            @RequestHeader("Authorization") String jwt
    ) throws AuthException {
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> createdProject = projectService.getProjectByTeam(user, category, tag);

        List<ProjectsGetProjectsResponseDto> dto = createdProject
                .stream()
                .map(created -> {
                    ProjectsGetProjectsResponseDto projectsGetProjectsResponseDto = new ProjectsGetProjectsResponseDto();
                    projectsGetProjectsResponseDto.setCategory(created.getCategory());
                    projectsGetProjectsResponseDto.setName(created.getName());
                    projectsGetProjectsResponseDto.setDescription(created.getDescription());
                    return projectsGetProjectsResponseDto;
                }).toList();

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @GetMapping("/{projectId}")
    @Cacheable(value = "project", key = "#projectId")
    public ResponseEntity<ProjectsGetProjectByIdResponseDto> getProjectById(
            @PathVariable UUID projectId,
            @RequestHeader("Authorization") String jwt
    ) throws AuthException {

        User user = userService.findUserProfileByJwt(jwt);

        if (jwt == null || jwt.trim().isEmpty()) throw new AuthException("TOKEN ERROR");

        Project project = projectService.getProjectById(projectId);

        ProjectsGetProjectByIdResponseDto projectsGetProjectByIdResponseDto = new ProjectsGetProjectByIdResponseDto();

        // CHAT : DTO -> ProjectsChatGetProjectByIdResponseDto
        ProjectsChatGetProjectByIdResponseDto projectsChatGetProjectByIdResponseDto = new ProjectsChatGetProjectByIdResponseDto();
        projectsChatGetProjectByIdResponseDto.setName(project.getChat().getName());

        // USER : DTO -> ProjectsUserGetProjectByIdResponseDto
        ProjectsUserGetProjectByIdResponseDto projectsUserGetProjectByIdResponseDto = new ProjectsUserGetProjectByIdResponseDto();
        projectsUserGetProjectByIdResponseDto.setProjectSize(project.getOwner().getProjectSize());
        projectsUserGetProjectByIdResponseDto.setEmail(project.getOwner().getEmail());
        projectsUserGetProjectByIdResponseDto.setFullName(project.getOwner().getFullName());

        // ISSUES : DTO -> ProjectsIssuesGetProjectByIdResponseDto
        List<ProjectsIssuesGetProjectByIdResponseDto> issueDtos = project.getIssues()
                .stream()
                .map(issue -> {
                    ProjectsIssuesGetProjectByIdResponseDto dto = new ProjectsIssuesGetProjectByIdResponseDto();
                    dto.setTitle(issue.getTitle());
                    dto.setDescription(issue.getDescription());
                    dto.setStatus(issue.getStatus());
                    dto.setPriority(issue.getPriority());
                    dto.setDueDate(issue.getDueDate());
                    return dto;
                })
                .collect(Collectors.toList());

        projectsGetProjectByIdResponseDto.setCategory(project.getCategory());
        projectsGetProjectByIdResponseDto.setDescription(project.getDescription());
        projectsGetProjectByIdResponseDto.setName(project.getName());
        projectsGetProjectByIdResponseDto.setTags(project.getTags());
        projectsGetProjectByIdResponseDto.setChat(projectsChatGetProjectByIdResponseDto);
        projectsGetProjectByIdResponseDto.setOwner(projectsUserGetProjectByIdResponseDto);
        projectsGetProjectByIdResponseDto.setIssues(issueDtos);

        return new ResponseEntity<>(projectsGetProjectByIdResponseDto, HttpStatus.OK);
    }


    // POST endpoint to create a new project
    @PostMapping
    public ResponseEntity<ProjectsCreateProjectResponseDto> createProject(
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        Project createdProject = projectService.createProject(project, user);

        ProjectsCreateProjectResponseDto projectsCreateProjectResponseDto = new ProjectsCreateProjectResponseDto();
        projectsCreateProjectResponseDto.setCategory(createdProject.getCategory());
        projectsCreateProjectResponseDto.setDescription(createdProject.getDescription());
        projectsCreateProjectResponseDto.setName(createdProject.getName());

        return new ResponseEntity<>(projectsCreateProjectResponseDto, HttpStatus.OK);
    }

    // PATCH endpoint to update an existing project
    @PatchMapping("/{projectId}")
    @CachePut(value = "project", key = "#projectId")
    public ResponseEntity<ProjectsUpdateProjectResponseDto> updateProject(
            @PathVariable UUID projectId,
            @RequestHeader("Authorization") String jwt,
            @RequestBody ProjectsUpdateProjectRequestDto projectsUpdateProjectRequestDto
    ) throws AuthException {

        User user = userService.findUserProfileByJwt(jwt);

        Project project = new Project();

        // REQUEST
        project.setCategory(projectsUpdateProjectRequestDto.getCategory());
        project.setName(projectsUpdateProjectRequestDto.getName());
        project.setDescription(projectsUpdateProjectRequestDto.getDescription());
        Project updatedProject = projectService.updateProject(project, projectId);

        // RESPONSE
        ProjectsUpdateProjectResponseDto projectsUpdateProjectResponseDto = new ProjectsUpdateProjectResponseDto();
        projectsUpdateProjectResponseDto.setCategory(project.getCategory());
        projectsUpdateProjectResponseDto.setDescription(project.getDescription());
        projectsUpdateProjectResponseDto.setName(project.getName());
        projectsUpdateProjectResponseDto.setTags(project.getTags());


        return new ResponseEntity<>(projectsUpdateProjectResponseDto, HttpStatus.OK);
    }

    // DELETE endpoint to delete a project
    @DeleteMapping("/{projectId}")
    @CacheEvict(value = "project", key = "#projectId")
    public ResponseEntity<ProjectsDeleteProjectResponseDto> deleteProject(
            @PathVariable UUID projectId,
            @RequestHeader("Authorization") String jwt
    ) throws AuthException {
        User user = userService.findUserProfileByJwt(jwt);
        projectService.deleteProject(projectId, user.getId());
        ProjectsDeleteProjectResponseDto projectsDeleteProjectResponseDto = new ProjectsDeleteProjectResponseDto();
        projectsDeleteProjectResponseDto.setMessage("Project Deleted Successfully.");
        return new ResponseEntity<>(projectsDeleteProjectResponseDto, HttpStatus.OK);
    }


    // GET endpoint to search for projects by keyword
    @GetMapping("/search")
    @Cacheable(value = "projectsSearch", key = "#keyword + '_' + #user.id")
    public ResponseEntity<List<ProjectsSearchProjectResponseDto>> searchProject(
            @RequestParam(required = false) String keyword,
            @RequestHeader("Authorization") String jwt
    ) throws AuthException {
        User user = userService.findUserProfileByJwt(jwt);

        List<Project> projects = projectService.searchProjects(keyword, user);

        List<ProjectsSearchProjectResponseDto> dto = projects.stream().map(
                project -> {
                    ProjectsSearchProjectResponseDto projectsSearchProjectResponseDto = new ProjectsSearchProjectResponseDto();
                    projectsSearchProjectResponseDto.setCategory(project.getCategory());
                    projectsSearchProjectResponseDto.setDescription(project.getDescription());
                    projectsSearchProjectResponseDto.setName(project.getName());
                    projectsSearchProjectResponseDto.setTags(project.getTags());
                    projectsSearchProjectResponseDto.setEmail(project.getOwner().getEmail());
                    projectsSearchProjectResponseDto.setFullName(project.getOwner().getFullName());
                    return projectsSearchProjectResponseDto;
                }).toList();

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @GetMapping("/{projectId}/chat")
    public ResponseEntity<ProjectsChatGetChatProjectProjectIdResponseDto> getChatProjectId(
            @PathVariable UUID projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Chat chat = projectService.getChatByProjectId(projectId);

        ProjectsChatGetChatProjectProjectIdResponseDto projectsChatGetChatProjectProjectIdResponseDto = new ProjectsChatGetChatProjectProjectIdResponseDto();

        projectsChatGetChatProjectProjectIdResponseDto.setName(chat.getName());

        return new ResponseEntity<>(projectsChatGetChatProjectProjectIdResponseDto, HttpStatus.OK);
    }

    @PostMapping("/invite")
    public ResponseEntity<ProjectsInviteProjectResponseDto> inviteProject (
            @RequestBody InviteRequest req,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws Exception, InvatitionException {
        User user = userService.findUserProfileByJwt(jwt);
        invitationService.sendInvitation(req.getEmail(),req.getProjectId());
        ProjectsInviteProjectResponseDto projectsInviteProjectResponseDto = new ProjectsInviteProjectResponseDto("User Invitation Sent");
        return new ResponseEntity<>(projectsInviteProjectResponseDto, HttpStatus.OK);
    }

    @GetMapping("/accept_invitation")
    public ResponseEntity<ProjectsInvitationAcceptInviteProjectResponseDto> acceptInviteProject(
            @RequestParam String token,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws InvatitionException {
        User user = userService.findUserProfileByJwt(jwt);
        Invitation invitation = invitationService.acceptInvitation(token, user.getId());
        projectService.addUserToProject(invitation.getProjectId(), user.getId());

        ProjectsInvitationAcceptInviteProjectResponseDto projectsInvitationAcceptInviteProjectResponseDto = new ProjectsInvitationAcceptInviteProjectResponseDto();

        projectsInvitationAcceptInviteProjectResponseDto.setProjectId(invitation.getProjectId());
        projectsInvitationAcceptInviteProjectResponseDto.setEmail(invitation.getEmail());


        return new ResponseEntity<>(projectsInvitationAcceptInviteProjectResponseDto, HttpStatus.ACCEPTED);
    }

}
