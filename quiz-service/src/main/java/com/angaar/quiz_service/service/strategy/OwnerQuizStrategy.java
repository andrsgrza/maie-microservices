package com.angaar.quiz_service.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angaar.quiz_service.models.Quiz;

import com.angaar.quiz_service.models.dto.OwnerQuizDTO;
import com.angaar.quiz_service.service.ResourceEntitlementService;

@Service
public class OwnerQuizStrategy implements QuizEntitlementStrategy {

    @Autowired
    private ResourceEntitlementService resourceEntitlementService;

    @Override
    public Object mapToDto(Quiz quiz) {
        OwnerQuizDTO ownerQuizDTO = new OwnerQuizDTO();
        ownerQuizDTO.setId(quiz.getId());
        ownerQuizDTO.setTitle(quiz.getTitle());
        ownerQuizDTO.setMetadata(quiz.getMetadata());
        ownerQuizDTO.setSections(quiz.getSections());
        ownerQuizDTO.setResourceEntitlement(resourceEntitlementService.findEntitlementsForQuiz(quiz.getId()));
        return ownerQuizDTO;
    }
}
