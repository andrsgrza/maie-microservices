package com.angaar.quiz_service.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.angaar.quiz_service.models.Section;

import jakarta.persistence.PrePersist;

import java.util.List;

@Document(collection = "quizzes")
public class Quiz {

    @Id
    private String id;
    private String title;
    private Metadata metadata;
    private List<Section> sections;

    public Quiz() {
        generateId();
    }

    public Quiz(String title, Metadata metadata, List<Section> sections) {
        this.title = title;
        this.metadata = metadata;
        this.sections = sections;
        generateId(); 
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
    
    public void generateId() {
        if (this.id == null) {
            this.id = "RQ_" + java.util.UUID.randomUUID().toString().substring(0, 8);
        }
    }

    // Inner Metadata class
    public static class Metadata {
        private String author;
        private String description;
        private String creationDate;

        public Metadata() {}

        public Metadata(String author, String description, String creationDate) {
            this.author = author;
            this.description = description;
            this.creationDate = creationDate;
        }

        // Getters and Setters
        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(String creationDate) {
            this.creationDate = creationDate;
        }
        
    }

    
}
