package com.angaar.quiz_service.models.dto;

import java.util.List;
import java.util.Map;

import com.angaar.quiz_service.models.Section;
import com.angaar.quiz_service.models.entitlements.ResourceEntitlement;
import com.angaar.quiz_service.models.entitlements.Role;
import com.angaar.quiz_service.models.Quiz.Metadata;

public class OwnerQuizDTO {
	private Role entitlementRole;
    private String id;
    private String title;
    private Metadata metadata;
    private Map<Role, List<String>> resourceEntitlement;
    private List<Section> sections;
    

    // Constructor
    public OwnerQuizDTO() {this.entitlementRole = Role.OWNER;}

    public OwnerQuizDTO(String id, String title, Metadata metadata, List<Section> sections, Map<Role, List<String>> resouceEntitlement) {
        this.id = id;
        this.title = title;
        this.metadata = metadata;
        this.sections  = sections;
        this.entitlementRole = Role.OWNER;
        this.resourceEntitlement = resouceEntitlement;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	public Role getEntitlementRole() {
		return entitlementRole;
	}

	public void setEntitlementRole(Role entitlementRole) {
		this.entitlementRole = entitlementRole;
	}

	public Map<Role, List<String>> getResourceEntitlement() {
		return resourceEntitlement;
	}

	public void setResourceEntitlement(Map<Role, List<String>> resourceEntitlement) {
		this.resourceEntitlement = resourceEntitlement;
	}
	
    
}
