package com.angaar.quiz_service.models.dto;

import java.util.List;

import com.angaar.quiz_service.models.Section;
import com.angaar.quiz_service.models.Quiz.Metadata;

public class OwnerQuizDTO {
    private String id;
    private String title;
    private Metadata metadata;
    private List<Section> sections;
    

    // Constructor
    public OwnerQuizDTO() {}

    public OwnerQuizDTO(String id, String title, Metadata metadata, List<Section> sections) {
        this.id = id;
        this.title = title;
        this.metadata = metadata;
        this.sections  = sections;
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

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}
    
}
