package com.econ.managify.dtos.requests;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.UUID;

public class IssuesCreateIssueRequestDto {
    @NotBlank(message = "Title cannot be empty")
    private String title;
    @NotBlank(message = "Description cannot be empty")
    private String description;
    @NotBlank(message = "Status cannot be empty")
    private String status;
    private UUID projectId;
    private String priority;
    private LocalDate dueDate;

    public IssuesCreateIssueRequestDto( ) {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public IssuesCreateIssueRequestDto(String title, String description, String status, UUID projectId, String priority, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.projectId = projectId;
        this.priority = priority;
        this.dueDate = dueDate;
    }
}
