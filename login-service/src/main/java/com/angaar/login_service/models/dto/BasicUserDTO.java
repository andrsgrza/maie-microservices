package com.angaar.login_service.models.dto;

public class BasicUserDTO {	
	private String userId;
    private String username;
    
    public BasicUserDTO(String id, String username) {
        this.userId = id;
        this.username = username;
    }
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username.trim();
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId.trim();
	}
}
