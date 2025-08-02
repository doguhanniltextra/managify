package com.econ.managify.dtos.responses;

import java.time.LocalDate;

public class CommentsCreateResponseDto {
    private String content;
    private LocalDate createdDateTime;


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
}
