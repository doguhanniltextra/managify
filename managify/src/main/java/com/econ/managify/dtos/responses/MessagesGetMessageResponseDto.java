package com.econ.managify.dtos.responses;

import java.time.LocalDateTime;

public class MessagesGetMessageResponseDto {
    private String content;
    private LocalDateTime createdAt;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
