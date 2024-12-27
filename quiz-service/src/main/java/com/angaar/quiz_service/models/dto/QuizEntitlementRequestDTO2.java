package com.angaar.quiz_service.models.dto;

import java.util.List;

import com.angaar.quiz_service.models.entitlements.EntitlementRequestOperation;

public class QuizEntitlementRequestDTO2 {
	private EntitlementRequestOperation operation;
	private List<QuizEntitlementRequestDTO> quizUserListPerRole;
	
	QuizEntitlementRequestDTO2() {}

	public EntitlementRequestOperation getOperation() {
		return operation;
	}

	public void setOperation(EntitlementRequestOperation operation) {
		this.operation = operation;
	}

	public List<QuizEntitlementRequestDTO> getQuizUserListPerRole() {
		return quizUserListPerRole;
	}

	public void setQuizUserListPerRole(List<QuizEntitlementRequestDTO> quizUserListPerRole) {
		this.quizUserListPerRole = quizUserListPerRole;
	}


	
	

}
