package com.econ.managify.interfaces;

import com.econ.managify.exceptions.AuthException;
import com.econ.managify.exceptions.MessageException;
import com.econ.managify.exceptions.ProjectServiceException;
import com.econ.managify.models.Chat;
import com.econ.managify.models.Project;
import com.econ.managify.models.User;

import java.util.List;
import java.util.UUID;

public interface ProjectService {

    Project createProject(Project project, User user) throws Exception;

    List<Project> getProjectByTeam(User user, String category, String tag) throws AuthException;


    void deleteProject(UUID projectId, Long userId) throws AuthException;

    Project updateProject(Project updatedProject, UUID id) throws AuthException;

    void addUserToProject(UUID projectId, Long userId) throws ProjectServiceException;
    void removeUserFromProject(UUID projectId, Long userId) throws Exception;



    List<Project> searchProjects(String keyword, User user) throws  AuthException;


    Chat getChatByProjectId(UUID projectId);

    Project getProjectById(UUID projectId);
}
