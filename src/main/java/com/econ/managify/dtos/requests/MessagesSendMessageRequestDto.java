package com.econ.managify.dtos.requests;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class MessagesSendMessageRequestDto {
    private Long senderId;
    @NotBlank(message = "Content cannot be empty")
    private String content;
    private UUID projectId;


    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }
}
