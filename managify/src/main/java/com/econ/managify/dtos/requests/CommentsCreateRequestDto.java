package com.econ.managify.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CommentsCreateRequestDto {
    @NotNull(message = "Issue must be provided")
    private Long issueId;

    @NotBlank(message = "Content cannot be empty")
    private String content;

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
