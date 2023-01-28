package com.example.football.service.impl;

import com.example.football.models.dto.town.ImportTownDTO;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.example.football.util.ValidationUtils;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static com.example.football.constants.Messages.INVALID_TOWN;
import static com.example.football.constants.Messages.SUCCESSFULLY_IMPORTED_TOWN;
import static com.example.football.constants.Paths.TOWNS_PATH_JSON;


@Service
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, Gson gson, ModelMapper modelMapper, ValidationUtils validationUtils) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(TOWNS_PATH_JSON);
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder sb = new StringBuilder();

        List<ImportTownDTO> importTownDTO = Arrays.stream(this.gson.fromJson(readTownsFileContent(), ImportTownDTO[].class)).toList();

        for (ImportTownDTO townDTO : importTownDTO) {
            boolean isValid = validationUtils.isValid(townDTO);

            if (this.townRepository.findFirstByName(townDTO.getName()).isPresent()){
                isValid = false;
            }

            if (isValid){
                Town town = this.modelMapper.map(townDTO, Town.class);
                this.townRepository.saveAndFlush(town);
                sb.append(String.format(SUCCESSFULLY_IMPORTED_TOWN,town.getName(),town.getPopulation()));
            }else {
                sb.append(INVALID_TOWN).append(System.lineSeparator());
            }
        }


        return sb.toString();
    }
}
