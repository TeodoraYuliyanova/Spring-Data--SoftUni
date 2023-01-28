package com.example.football.service.impl;

import com.example.football.models.dto.team.ImportTeamDTO;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
import com.example.football.util.ValidationUtils;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;

import static com.example.football.constants.Messages.INVALID_TEAM;
import static com.example.football.constants.Messages.SUCCESSFULLY_IMPORTED_TEAM;
import static com.example.football.constants.Paths.TEAMS_PATH_JSON;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TownRepository townRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;

    public TeamServiceImpl(TeamRepository teamRepository, TownRepository townRepository, Gson gson, ModelMapper modelMapper, ValidationUtils validationUtils) {
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(TEAMS_PATH_JSON);
    }

    @Override
    public String importTeams() throws IOException {
        StringBuilder sb = new StringBuilder();

        ImportTeamDTO[] importTeamDTO = this.gson.fromJson(readTeamsFileContent(), ImportTeamDTO[].class);

        for (ImportTeamDTO teamDTO : importTeamDTO) {
            boolean isValid = validationUtils.isValid(teamDTO);

            if (this.teamRepository.findFirstByName(teamDTO.getName()).isPresent()) {
                isValid = false;
            }

            if (isValid) {

                if (this.townRepository.findFirstByName(teamDTO.getTownName()).isPresent()) {
                    Town townToSet = this.townRepository.findFirstByName(teamDTO.getTownName()).get();
                    Team teamToBeSaved = this.modelMapper.map(teamDTO, Team.class);
                    teamToBeSaved.setTown(townToSet);
                    this.teamRepository.saveAndFlush(teamToBeSaved);
                    sb.append(String.format(SUCCESSFULLY_IMPORTED_TEAM, teamToBeSaved.getName(), teamToBeSaved.getFanBase()));
                }
            } else {
                sb.append(INVALID_TEAM).append(System.lineSeparator());
            }
        }


        return sb.toString();
    }
}
