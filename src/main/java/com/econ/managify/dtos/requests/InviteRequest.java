package com.econ.managify.dtos.requests;

import java.util.UUID;

public class InviteRequest {
    private UUID projectId;
    private String email;

    public InviteRequest() {
    }

    public InviteRequest(UUID projectId, String email) {
        this.projectId = projectId;
        this.email = email;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
