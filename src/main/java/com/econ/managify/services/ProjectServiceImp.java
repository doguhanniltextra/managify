package com.econ.managify.services;

import com.econ.managify.exceptions.AuthException;
import com.econ.managify.exceptions.MessageException;
import com.econ.managify.exceptions.ProjectServiceException;
import com.econ.managify.interfaces.ChatService;
import com.econ.managify.interfaces.ProjectService;
import com.econ.managify.interfaces.UserService;
import com.econ.managify.models.Chat;
import com.econ.managify.models.Project;
import com.econ.managify.models.User;
import com.econ.managify.repositories.ProjectRepository;
import com.econ.managify.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImp  {

    private final ProjectRepository projectRepository;
    private final UserServiceImp userService;
    private final ChatServiceImp chatService;
    private final UserRepository userRepository;

    public ProjectServiceImp(ProjectRepository projectRepository, UserServiceImp userService, ChatServiceImp chatService, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userService = userService;
        this.chatService = chatService;
        this.userRepository = userRepository;
    }


    public Project createProject(Project project, User user) throws Exception {
        Project createdProject = new Project();
        createdProject.setOwner(user);
        createdProject.setTags(project.getTags());
        createdProject.setName(project.getName());
        createdProject.setCategory(project.getCategory());
        createdProject.setDescription(project.getDescription());
        createdProject.getTeam().add(user);


        Project savedProject = projectRepository.save(createdProject);


        user.setProjectSize(user.getProjectSize() + 1);
        userRepository.save(user);

        Chat chat = new Chat();
        chat.setProject(savedProject);
        Chat projectChat = chatService.createChat(chat);
        savedProject.setChat(projectChat);

        return savedProject;
    }


    public List<Project> getProjectByTeam(User user, String category, String tag) throws AuthException {
        List<Project> projects = projectRepository.findByTeamContains(user);

        if (category != null) {
            projects = projects.stream()
                    .filter(project -> project.getCategory().equals(category))
                    .collect(Collectors.toList());
        }

        if (tag != null) {
            projects = projects.stream()
                    .filter(project -> project.getTags().contains(tag))
                    .collect(Collectors.toList());
        }

        return projects;
    }



    public Project getProjectById(UUID projectId) throws AuthException {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if(optionalProject.isEmpty()) {
            throw new AuthException("Project Not Found");
        }
        return optionalProject.get();
    }


    public void deleteProject(UUID projectId, Long userId) throws AuthException {
        getProjectById(projectId);
        projectRepository.deleteById(projectId);
    }


    public Project updateProject(Project updatedProject, UUID id) throws AuthException {
        Project project = getProjectById(id);
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        project.setTags(updatedProject.getTags());
        return projectRepository.save(project);
    }


    public void addUserToProject(UUID projectId, Long userId) throws AuthException {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);

        if(!project.getTeam().contains(user)){
            project.getChat().getUsers().add(user);
            project.getTeam().add(user);

        }
        projectRepository.save(project);
    }


    public void removeUserFromProject(UUID projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);

        if(project.getTeam().contains(user)){
            project.getChat().getUsers().remove(user);
            project.getTeam().remove(user) ;
        }
        projectRepository.save(project);
    }


    public Chat getChatByProjectId(UUID projectId) throws MessageException, ProjectServiceException {
        Project project = getProjectById(projectId);
        return project.getChat();
    }


    public List<Project> searchProjects(String keyword, User user) throws AuthException {
        return projectRepository.findByNameContainingAndTeamContains(keyword, user);
    }




}
