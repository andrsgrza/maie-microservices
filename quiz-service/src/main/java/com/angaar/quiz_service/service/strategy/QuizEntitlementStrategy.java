package com.angaar.quiz_service.service.strategy;

import com.angaar.quiz_service.models.Quiz;

public interface QuizEntitlementStrategy {

	Object mapToDto(Quiz quiz);

}
