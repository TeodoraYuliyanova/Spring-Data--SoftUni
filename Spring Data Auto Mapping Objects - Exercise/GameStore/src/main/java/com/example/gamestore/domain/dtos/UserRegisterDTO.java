package com.example.gamestore.domain.dtos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.gamestore.Constants.ExceptionMessages.*;

public class UserRegisterDTO {

    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;

    public UserRegisterDTO(String email, String password, String confirmPassword, String fullName) {
        setEmail(email);
        setPassword(password);
        setConfirmPassword(confirmPassword);
        setFullName(fullName);
    }

    public UserRegisterDTO() {
    }

    public String getEmail() {
        return email;
    }

    protected void setEmail(String email) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)@[A-Za-z0-9-]+(.[A-Za-z0-9]+)(.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()){
            throw new IllegalArgumentException(INCORRECT_EMAIL);
        }
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    protected void setPassword(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$");
        Matcher matcher = pattern.matcher(password);

        if (!matcher.matches()){
            throw new IllegalArgumentException(INCORRECT_USERNAME_OR_PASSWORD);
        }

        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    protected void setConfirmPassword(String confirmPassword) {
        if (!this.password.equals(confirmPassword)){
            throw new IllegalArgumentException(PASSWORDS_MISMATCH);
        }

        this.confirmPassword = confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    protected void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
