package com.itforshort.toko.payload.response;

public class JwtResponse {
    private final String token;
    private String type = "Bearer";
    private final Long id;
    private final String username;

    public JwtResponse(String token, Long id, String username) {
        this.token = token;
        this.id = id;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}