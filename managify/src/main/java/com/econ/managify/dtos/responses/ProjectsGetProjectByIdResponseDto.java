package com.econ.managify.dtos.responses;

import java.util.ArrayList;
import java.util.List;

public class ProjectsGetProjectByIdResponseDto {
    private String name;
    private String description;
    private String category;
    private List<String> tags = new ArrayList<>();

    private ProjectsChatGetProjectByIdResponseDto chat;

    private ProjectsUserGetProjectByIdResponseDto owner;

    private List<ProjectsIssuesGetProjectByIdResponseDto> issues = new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public ProjectsChatGetProjectByIdResponseDto getChat() {
        return chat;
    }

    public void setChat(ProjectsChatGetProjectByIdResponseDto chat) {
        this.chat = chat;
    }

    public ProjectsUserGetProjectByIdResponseDto getOwner() {
        return owner;
    }

    public void setOwner(ProjectsUserGetProjectByIdResponseDto owner) {
        this.owner = owner;
    }

    public List<ProjectsIssuesGetProjectByIdResponseDto> getIssues() {
        return issues;
    }

    public void setIssues(List<ProjectsIssuesGetProjectByIdResponseDto> issues) {
        this.issues = issues;
    }
}
