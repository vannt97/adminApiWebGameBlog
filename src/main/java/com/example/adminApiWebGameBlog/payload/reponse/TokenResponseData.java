package com.example.adminApiWebGameBlog.payload.reponse;

public class TokenResponseData {
    private String token;
    private String refeshToken;

    public TokenResponseData(String token, String refeshToken) {
        this.token = token;
        this.refeshToken = refeshToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefeshToken() {
        return refeshToken;
    }

    public void setRefeshToken(String refeshToken) {
        this.refeshToken = refeshToken;
    }
}
