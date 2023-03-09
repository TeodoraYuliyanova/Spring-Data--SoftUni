package com.example.gamestore.Constants;

public enum ExceptionMessages {

    ;
    public static final String INCORRECT_EMAIL = "Incorrect email.";
    public static final String INCORRECT_USERNAME_OR_PASSWORD = "Incorrect username / password";
    public static final String EMAIL_ALREADY_EXISTS = "Email already exists.";
    public static final String PASSWORDS_MISMATCH = "Passwords don't match.";
    public static final String INVALID_GAME_TITLE = "Invalid game title.";
    public static final String INVALID_PRICE = "Price must be a positive number.";
    public static final String INVALID_SIZE = "Size must be a positive number.";
    public static final String INVALID_TRAILER = "Trailer ID must be 11 character.";
    public static final String INVALID_THUMBNAIL_URL = "Invalid thumbnail URL.";
    public static final String INVALID_DESCRIPTION = "Description should be at least 20 symbols.";
    public static final String GAME_ALREADY_EXISTS = "Game is already in the catalog.";
    public static final String GAME_DOES_NOT_EXISTS = "This game does not exists in the catalog.You need to add it first and after that you will be able to edit it.";
    public static final String CANNOT_DO_EDITS = "You need to become admin to do edits.";
    public static final String INVALID_GAME_ID = "Invalid game Id.";
}
