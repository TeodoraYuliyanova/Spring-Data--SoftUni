package com.example.football.service.impl;

import com.example.football.models.dto.player.ImportPlayerDTO;
import com.example.football.models.dto.player.ImportPlayersWrapperDTO;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import com.example.football.util.ValidationUtils;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.example.football.constants.Messages.*;
import static com.example.football.constants.Paths.PLAYERS_PATH_XML;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;
    private final TownRepository townRepository;
    private final TeamRepository teamRepository;
    private final StatRepository statRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtils validationUtils, TownRepository townRepository, TeamRepository teamRepository, StatRepository statRepository) {
        this.playerRepository = playerRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.townRepository = townRepository;
        this.teamRepository = teamRepository;
        this.statRepository = statRepository;
    }


    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(PLAYERS_PATH_XML);
    }

    @Override
    public String importPlayers() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        File file = PLAYERS_PATH_XML.toFile();

        ImportPlayersWrapperDTO importPlayersWrapperDTO = xmlParser.fromFile(file, ImportPlayersWrapperDTO.class);
        List<ImportPlayerDTO> importPlayersDTO = importPlayersWrapperDTO.getPlayer().stream().toList();

        for (ImportPlayerDTO playerDTO : importPlayersDTO) {
            boolean isValid = validationUtils.isValid(playerDTO);

            if (this.playerRepository.findFirstByEmail(playerDTO.getEmail()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                if (this.townRepository.findFirstByName(playerDTO.getTown().getName()).isPresent()) {
                    if (this.teamRepository.findFirstByName(playerDTO.getTeam().getName()).isPresent()) {
                        if (this.statRepository.findById(playerDTO.getStat().getId()).isPresent()) {
                            Town townToSet = this.townRepository.findFirstByName(playerDTO.getTown().getName()).get();
                            Team teamToSet = this.teamRepository.findFirstByName(playerDTO.getTeam().getName()).get();
                            Stat statToSet = this.statRepository.findById(playerDTO.getStat().getId()).get();

                            Player player = this.modelMapper.map(playerDTO, Player.class);
                            player.setBirthDate(LocalDate.parse(playerDTO.getBirthDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                            player.setTown(townToSet);
                            player.setTeam(teamToSet);
                            player.setStat(statToSet);

                            this.playerRepository.saveAndFlush(player);
                            sb.append(String.format(SUCCESSFULLY_IMPORTED_PLAYER, player.getFirstName(), player.getLastName(),
                                    player.getPosition()));
                        }
                    }
                }
            } else {
                sb.append(INVALID_PLAYER).append(System.lineSeparator());
            }
        }

        return sb.toString();
    }

    @Override
    public String exportBestPlayers() {

        List<Player> players = this.playerRepository
                .findAllByBirthDateAfterAndBirthDateBeforeOrderByStatShootingDescStatPassingDescStatEnduranceDescLastName
                (LocalDate.of(1995, 1, 1), LocalDate.of(2003, 1, 1)).orElseThrow(NoSuchElementException::new);

       return players.stream().map(player -> String.format(PLAYERS_OUTPUT,
               player.getFirstName(),player.getLastName(),
               player.getPosition(),
               player.getTeam().getName(),
               player.getTeam().getStadiumName())).collect(Collectors.joining(System.lineSeparator()));

    }
}
