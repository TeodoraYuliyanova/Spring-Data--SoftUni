package com.example.gamestore.repositories;

import com.example.gamestore.domain.dtos.GameDTO;
import com.example.gamestore.domain.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface GameRepository extends JpaRepository<Game,Long> {

    Optional<Game> findByTitle(String title);

    Optional<Game> findById(long id);

    @Query("SELECT g.title AS title,g.price AS price FROM Game g")
    Set<GameDTO> getAllGames();
}
