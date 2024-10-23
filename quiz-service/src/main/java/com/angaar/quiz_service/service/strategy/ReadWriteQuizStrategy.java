package com.angaar.quiz_service.service.strategy;

import com.angaar.quiz_service.models.Quiz;
import com.angaar.quiz_service.models.dto.ReadWriteQuizDTO;

public class ReadWriteQuizStrategy implements QuizEntitlementStrategy {

    @Override
    public Object mapToDto(Quiz quiz) {
        ReadWriteQuizDTO readWriteQuizDTO = new ReadWriteQuizDTO();
        readWriteQuizDTO.setId(quiz.getId());
        readWriteQuizDTO.setTitle(quiz.getTitle());
        readWriteQuizDTO.setEditableData("Editable data for user with write access");
        return readWriteQuizDTO;
    }
}