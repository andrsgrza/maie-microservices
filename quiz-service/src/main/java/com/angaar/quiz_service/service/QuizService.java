package com.angaar.quiz_service.service;

import com.angaar.quiz_service.models.Quiz;
import com.angaar.quiz_service.models.entitlements.*;
import com.angaar.quiz_service.repositories.QuizRepository;
import com.angaar.quiz_service.repositories.ResourceEntitlementRepository;
import com.angaar.quiz_service.service.strategy.QuizEntitlementStrategy;
import com.angaar.quiz_service.service.strategy.QuizEntitlementStrategyFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private ResourceEntitlementService resourceService;
    
    public void getQuizzesForUser(String userId) {
    	
    	Map<String, Role> entitlementMap= resourceService.findQuizzesEntitledByUser(userId);
    	entitlementMap.forEach((resourceId, role) -> {
            System.out.println("Resource: " + resourceId + ", Highest Role: " + role);
        });
        
        
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Optional<Quiz> getQuizById(String id) {
        return quizRepository.findById(id);
    }

    public Quiz createQuiz(Quiz quiz, String ownerId) {
    	System.out.println("CREATING QUIZZ");
    	Quiz.Metadata meta = quiz.getMetadata();
    	meta.setCreationDate(LocalDate.now().toString());
    	quiz.setMetadata(meta);
    	Quiz savedQuiz = quizRepository.save(quiz);
    	resourceService.assignRole(ownerId, ResourceType.QUIZ, quiz.getId(), TargetType.USER, Role.OWNER, false);
    	System.out.println("The received id: " + ownerId);
    	//QuizEntitlementStrategyFactory
    	//resourceService.assignRole(null, null, null, null, null, false);
        return savedQuiz;
    }

    public void deleteQuiz(String id) {
        quizRepository.deleteById(id);
    }

    public Quiz updateQuiz(String id, Quiz quizDetails) {
        // Use Optional to find the quiz, throwing an exception if it's not found
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        // Update quiz fields
        quiz.setTitle(quizDetails.getTitle());
        quiz.setMetadata(quizDetails.getMetadata());
        quiz.setSections(quizDetails.getSections());

        return quizRepository.save(quiz);
    }
}