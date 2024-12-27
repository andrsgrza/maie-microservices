package com.angaar.quiz_service.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuizUserDTO {
	private String userId;
    private String username;
    
    public QuizUserDTO() {}

    public QuizUserDTO(String userId, String username) {
        this.userId	 = userId;
        this.username = username;
    }

   

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void print() {
    	if(this.userId != null) {
    		System.out.println("User Id " + userId);
    	}else {
    		System.out.println("User Id is null");
    	}
    	if(this.username != null) {
    		System.out.println("User Name " + username);
    	}else {
    		System.out.println("User Name is null");
    	}
    }
}
