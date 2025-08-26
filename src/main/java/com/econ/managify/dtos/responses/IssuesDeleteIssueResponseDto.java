package com.econ.managify.dtos.responses;

public class IssuesDeleteIssueResponseDto {
    private String message;

    public IssuesDeleteIssueResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
