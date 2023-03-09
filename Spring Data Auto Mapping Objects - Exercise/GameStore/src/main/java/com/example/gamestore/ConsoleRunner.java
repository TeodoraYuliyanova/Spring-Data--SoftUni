package com.example.gamestore;

import com.example.gamestore.services.GameService.GameService;
import com.example.gamestore.services.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.example.gamestore.Constants.Commands.*;
import static com.example.gamestore.Constants.Outputs.NO_COMMAND_FOUND;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final UserService userService;
    private final GameService gameService;

    @Autowired
    public ConsoleRunner(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();

        while (!input.equals("close")){

            String[] arguments = input.split("\\|");

            String command = arguments[0];

            String output = switch (command) {

                case REGISTER_USER -> userService.registerUser(arguments);

                case LOGIN_USE -> userService.logInUser(arguments);

                case LOGOUT -> userService.logOut();

                case ADD_GAME -> gameService.addGame(arguments);

                case EDIT_GAME -> gameService.editGame(arguments);

                case DELETE_GAME -> gameService.deleteGame(arguments);

                default -> NO_COMMAND_FOUND;

            };

            System.out.println(output);


           input = scanner.nextLine();
        }

        String printCommand = scanner.nextLine();

        switch (printCommand){

            case ALL_GAMES -> gameService.getAllGames();
        }
    }
}
