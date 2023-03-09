package com.example.gamestore.services.GameService;

import com.example.gamestore.domain.dtos.GameDTO;
import com.example.gamestore.domain.dtos.GameNamePriceDTO;
import com.example.gamestore.domain.entities.Game;
import com.example.gamestore.repositories.GameRepository;
import com.example.gamestore.services.UserService.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;


import static com.example.gamestore.Constants.Commands.IMPOSSIBLE_COMMAND;
import static com.example.gamestore.Constants.ExceptionMessages.*;
import static com.example.gamestore.Constants.Outputs.*;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final UserService userService;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, UserService userService) {
        this.gameRepository = gameRepository;
        this.userService = userService;
    }

    @Override
    public String addGame(String[] args) {
        if (this.userService.getLoggedInUser() != null && this.userService.getLoggedInUser().isAdministrator()) {

            String title = args[1];
            BigDecimal price = BigDecimal.valueOf(Double.parseDouble(args[2]));
            double size = Double.parseDouble(args[3]);
            String trailer = args[4];
            String imageThumbnailURL = args[5];
            String description = args[6];
            LocalDate releaseDate = LocalDate.now();

            GameDTO addGameDTO = new GameDTO(title, price, size, trailer, imageThumbnailURL, description, releaseDate);

            Game gameToAdd = this.modelMapper.map(addGameDTO, Game.class);

            boolean doesGameExists = this.gameRepository.findByTitle(addGameDTO.getTitle()).isPresent();

            if (doesGameExists) {
                throw new IllegalArgumentException(GAME_ALREADY_EXISTS);
            }

            this.gameRepository.save(gameToAdd);
            return String.format(SUCCESSFULLY_ADDED_GAME, gameToAdd.getTitle());
        }
        return IMPOSSIBLE_COMMAND;

    }

    @Override
    public String editGame(String[] args) {
        long gameId = Long.parseLong(args[1]);

        if (!this.gameRepository.existsById(gameId)) {
            throw new IllegalArgumentException(GAME_DOES_NOT_EXISTS);
        }

        if (!this.userService.getLoggedInUser().isAdministrator()) {
            throw new IllegalArgumentException(CANNOT_DO_EDITS);
        }

        Game foundGameById = this.gameRepository.findById(gameId).get();

        final Game game = modelMapper.map(foundGameById, Game.class);

        for (int i = 2; i < args.length; i++) {
            String field = args[i].split("=")[0];
            String value = args[i].split("=")[1];

            switch (field) {

                case "title" -> game.setTitle(value);

                case "price" -> game.setPrice(BigDecimal.valueOf(Double.parseDouble(value)));

                case "size" -> game.setSize(Double.parseDouble(value));

                case "trailer" -> game.setTrailer(value);

                case "imageThumbnailURL" -> game.setImageThumbnailURL(value);

                case "description" -> game.setDescription(value);

                case "releaseDate" -> game.setReleaseDate(LocalDate.parse(value));

            }
        }
        this.gameRepository.save(game);
        return String.format(SUCCESSFULLY_EDITED_GAME, game.getTitle());
    }

    @Override
    public String deleteGame(String[] args) {
        long gameId = Long.parseLong(args[1]);

        if (!this.gameRepository.existsById(gameId)) {
            throw new IllegalArgumentException(INVALID_GAME_ID);
        }

        Game foundGameById = this.gameRepository.findById(gameId).get();

        Game game = modelMapper.map(foundGameById, Game.class);
        String gameName = game.getTitle();

        this.gameRepository.delete(game);
        return String.format(SUCCESSFULLY_DELETED_GAME,gameName);
    }

//    @Override
//    public String getAllGames() {
////        return this.gameRepository.getAllGames()
////                .stream()
////                .map(GameNamePriceDTO::info)
////                .collect(Collectors.joining(System.lineSeparator()));
//    }
}
