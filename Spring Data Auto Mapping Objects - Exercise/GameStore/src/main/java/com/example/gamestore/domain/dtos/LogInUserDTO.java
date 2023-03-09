package com.example.gamestore.domain.dtos;

public class LogInUserDTO {

    private String email;
    private String password;

    public LogInUserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LogInUserDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
