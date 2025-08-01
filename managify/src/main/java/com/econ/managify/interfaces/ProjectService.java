package com.econ.managify.interfaces;

import com.econ.managify.exceptions.MessageException;
import com.econ.managify.exceptions.ProjectServiceException;
import com.econ.managify.models.Chat;
import com.econ.managify.models.Project;
import com.econ.managify.models.User;

import java.util.List;

public interface ProjectService {

    Project createProject(Project project, User user) throws Exception;

    List<Project> getProjectByTeam(User user, String category, String tag) throws Exception;

    Project getProjectById(Long projectId) throws MessageException, ProjectServiceException;

    void deleteProject(Long projectId, Long userId) throws Exception;

    Project updateProject(Project updatedProject, Long id) throws Exception;

    void addUserToProject(Long projectId, Long userId) throws ProjectServiceException;
    void removeUserFromProject(Long projectId, Long userId) throws Exception;

    Chat getChatByProjectId(Long projectId) throws MessageException;

    List<Project> searchProjects(String keyword, User user) throws  Exception;


}
