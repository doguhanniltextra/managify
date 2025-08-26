package com.econ.managify.dtos.requests;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class CommentsGetCommentsByIssueIdRequestDto {
    @NotBlank(message = "Content cannot be empty")
    private String content;
    @Nullable
    private LocalDate createdDateTime;
    @NotBlank(message = "Commenter name cannot be empty")
    private String commenterName;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDate createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }
}
