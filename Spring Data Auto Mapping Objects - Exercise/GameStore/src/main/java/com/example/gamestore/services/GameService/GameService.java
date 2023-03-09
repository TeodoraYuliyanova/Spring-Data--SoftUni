package com.example.gamestore.services.GameService;

public interface GameService {
    String addGame(String [] args);

    String editGame(String [] args);

    String deleteGame(String [] args);

    String getAllGames();
}
