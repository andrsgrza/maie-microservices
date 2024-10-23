package com.angaar.quiz_service.service.strategy;

import com.angaar.quiz_service.models.Quiz;
import com.angaar.quiz_service.models.dto.ReadOnlyQuizDTO;

public class ReadOnlyQuizStrategy implements QuizEntitlementStrategy {

    @Override
    public Object mapToDto(Quiz quiz) {
        ReadOnlyQuizDTO readOnlyQuizDTO = new ReadOnlyQuizDTO();
        readOnlyQuizDTO.setId(quiz.getId());
        readOnlyQuizDTO.setTitle(quiz.getTitle());
        return readOnlyQuizDTO;
    }
}