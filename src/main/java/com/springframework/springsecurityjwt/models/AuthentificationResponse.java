package com.springframework.springsecurityjwt.models;

public class AuthentificationResponse {

    private final String jwt;


    public AuthentificationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
