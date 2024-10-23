package com.angaar.login_service.models;

import jakarta.persistence.*;

@Entity
public class User {

    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = true)
    private String telephone;

    @Lob
    @Column(name = "profile_picture")
    private byte[] profilePicture;

    // Foreign key reference to the Vault entity
    @ManyToOne
    @JoinColumn(name = "password_id", nullable = false)
    private Vault passwordVault;

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Vault getPasswordVault() {
        return passwordVault;
    }

    public void setPasswordVault(Vault passwordVault) {
        this.passwordVault = passwordVault;
    }
    
    @PrePersist
    public void prePersist() {
    	this.id = "UR_" + java.util.UUID.randomUUID().toString().substring(0, 8);
	}
}
