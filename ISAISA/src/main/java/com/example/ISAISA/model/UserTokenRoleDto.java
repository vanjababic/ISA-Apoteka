package com.example.ISAISA.model;

public class UserTokenRoleDto {

    private String accessToken;
    private int expiresIn;
    private Boolean enabled;
    private String role;



    public UserTokenRoleDto(String accessToken, int expiresIn, String role, Boolean enabled) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.role = role;
        this.enabled=enabled;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
