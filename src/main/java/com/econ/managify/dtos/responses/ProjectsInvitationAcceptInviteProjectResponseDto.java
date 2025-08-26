package com.econ.managify.dtos.responses;

import java.util.UUID;

public class ProjectsInvitationAcceptInviteProjectResponseDto {
    private String token;
    private String email;
    private UUID projectId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }
}
