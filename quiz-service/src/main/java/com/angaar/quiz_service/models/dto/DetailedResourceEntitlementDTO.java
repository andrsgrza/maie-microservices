package com.angaar.quiz_service.models.dto;
import com.angaar.quiz_service.models.entitlements.ResourceEntitlement;

public class DetailedResourceEntitlementDTO {
	private ResourceEntitlement resourceEntitlement;
	private String username;
	
	public ResourceEntitlement getResourceEntitlement() {
		return resourceEntitlement;
	}
	public void setResourceEntitlement(ResourceEntitlement resourceEntitlement) {
		this.resourceEntitlement = resourceEntitlement;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
