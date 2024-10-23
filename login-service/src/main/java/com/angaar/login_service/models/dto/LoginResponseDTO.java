package com.angaar.login_service.models.dto;

public class LoginResponseDTO {
    private String token;
    // Constructor
    public LoginResponseDTO(String token) {
        this.token = token;
    }

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
