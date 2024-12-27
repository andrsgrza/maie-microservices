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
    
    @Autowired
    private QuizEntitlementStrategyFactory strategyFactory;
    
    
    public List<Object> getAllQuizzesForUser(String userId) {
        // Retrieve all resource entitlements for the user
        List<ResourceEntitlement> entitlements = resourceService.getResourcesForTarget(ResourceType.QUIZ, TargetType.USER, userId);
        if(entitlements == null) {
        	return null;
        }
        List<Object> quizzesWithEntitlement = new ArrayList<>();

        for (ResourceEntitlement entitlement : entitlements) {
            // Retrieve the quiz by its ID
            Optional<Quiz> quizOpt = quizRepository.findById(entitlement.getResourceId());

            if (quizOpt.isPresent()) {
                Quiz quiz = quizOpt.get();

                // Call the static method from the factory directly
                QuizEntitlementStrategy strategy = strategyFactory.getStrategy(userId, entitlement);

                // Map the quiz to the appropriate DTO and include the user's role
                Object quizDto = strategy.mapToDto(quiz);

                // Add the DTO and role info to the response
                quizzesWithEntitlement.add(quizDto);
            }
        }

        return quizzesWithEntitlement;
    }


//    public List<Quiz> getAllQuizzes( String userId) {
//    	resourceService.getResourcesForTarget(userId);
//        return quizRepository.findAll();
//    }

    public Optional<Quiz> getQuizById(String id) {
        return quizRepository.findById(id);
    }

    public Quiz createQuiz(Quiz quiz, String ownerId) {
        // The user creating the quiz will be assigned as the OWNER
        Quiz.Metadata meta = quiz.getMetadata();
        meta.setCreationDate(LocalDate.now().toString());
        quiz.setMetadata(meta);
        Quiz savedQuiz = quizRepository.save(quiz);
        resourceService.assignUserRoleToQuiz(ownerId, quiz.getId(), Role.OWNER);

        return savedQuiz;
    }

    public void deleteQuiz(String id, String userId) {
        // Check if the user has permission to delete this quiz (OWNER or READ_WRITE role)
        boolean hasPermission = resourceService.hasPermission(userId, ResourceType.QUIZ, id, Role.OWNER);

        if (!hasPermission) {
            throw new RuntimeException("User does not have permission to delete this quiz");
        }

        quizRepository.deleteById(id);
    }

    public Quiz updateQuiz(String id, Quiz quizDetails, String userId) {
        // Check if the user has permission to update this quiz (OWNER or READ_WRITE role)
        boolean hasPermission = resourceService.hasPermission(userId, ResourceType.QUIZ, id, Role.READ_WRITE);

        if (!hasPermission) {
            throw new RuntimeException("User does not have permission to update this quiz");
        }

        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found"));

        // Update quiz fields
        quiz.setTitle(quizDetails.getTitle());
        quiz.setMetadata(quizDetails.getMetadata());
        quiz.setSections(quizDetails.getSections());

        return quizRepository.save(quiz);
    }
}