package com.angaar.quiz_service.service.strategy;

import com.angaar.quiz_service.models.Quiz;
import com.angaar.quiz_service.models.dto.OwnerQuizDTO;

public class OwnerQuizStrategy implements QuizEntitlementStrategy {

    @Override
    public Object mapToDto(Quiz quiz) {
        OwnerQuizDTO ownerQuizDTO = new OwnerQuizDTO();
        ownerQuizDTO.setId(quiz.getId());
        ownerQuizDTO.setTitle(quiz.getTitle());
        ownerQuizDTO.setMetadata(quiz.getMetadata());
        ownerQuizDTO.setSections(quiz.getSections());
        return ownerQuizDTO;
    }
}
