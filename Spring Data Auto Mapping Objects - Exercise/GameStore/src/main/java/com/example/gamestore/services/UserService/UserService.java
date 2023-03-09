package com.example.gamestore.services.UserService;

import com.example.gamestore.domain.entities.User;

public interface UserService {

   String registerUser(String[] args);

   String logInUser(String[] args);

   String logOut();

   User getLoggedInUser();
}
