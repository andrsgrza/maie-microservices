package com.angaar.quiz_service.models.dto;

import java.util.List;

import com.angaar.quiz_service.models.entitlements.Role;

public class QuizEntitlementRequestDTO {
    private Role role;
	private List<QuizUserDTO> quizUserList;
	
	public QuizEntitlementRequestDTO() {}
	
	public QuizEntitlementRequestDTO(Role role, List<QuizUserDTO> quizUser) {		
		super();
		System.out.println("Constructing quiz entitlement request for role " + role + " and quizUserList " +quizUser.toString());
		this.role = role;
		this.quizUserList = quizUser;
	}
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}

	public List<QuizUserDTO> getQuizUserList() {
		return quizUserList;
	}

	public void setQuizUserList(List<QuizUserDTO> quizUserList) {
		this.quizUserList = quizUserList;
	}
	
}
