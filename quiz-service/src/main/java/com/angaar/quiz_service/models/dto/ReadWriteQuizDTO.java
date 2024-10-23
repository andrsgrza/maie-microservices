package com.angaar.quiz_service.models.dto;

public class ReadWriteQuizDTO {
    private String id;
    private String title;
    private String editableData;  // Data that users with write access can modify

    // Constructor
    public ReadWriteQuizDTO() {}

    public ReadWriteQuizDTO(String id, String title, String editableData) {
        this.id = id;
        this.title = title;
        this.editableData = editableData;
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

    public String getEditableData() {
        return editableData;
    }

    public void setEditableData(String editableData) {
        this.editableData = editableData;
    }
}
