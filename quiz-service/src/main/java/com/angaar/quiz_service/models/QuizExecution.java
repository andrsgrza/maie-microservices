package com.angaar.quiz_service.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

import com.angaar.quiz_service.models.Quiz;

@Document(collection = "quizzes")
public class QuizExecution {
	@Id
    private String id;
	private String user;
	private LocalDateTime startTime;
	private LocalDateTime submitTime;
	
	
}

