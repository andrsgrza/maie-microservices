package com.angaar.quiz_service.models.dto;

public class ReadOnlyQuizDTO {
    private String id;
    private String title;

    // Constructor
    public ReadOnlyQuizDTO() {}

    public ReadOnlyQuizDTO(String id, String title) {
        this.id = id;
        this.title = title;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
