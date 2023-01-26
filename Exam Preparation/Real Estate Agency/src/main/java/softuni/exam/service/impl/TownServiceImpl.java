package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.town.ImportTownDTO;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.constants.Messages.INVALID_TOWN;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_TOWN;
import static softuni.exam.constants.Paths.TOWNS_PATH_JSON;

@Service
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;
    private final ValidationUtils validationUtils;
    private final Gson gson;
    private final ModelMapper modelMapper;


    @Autowired
    public TownServiceImpl(TownRepository townRepository, ValidationUtils validationUtils, Gson gson, ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.validationUtils = validationUtils;
        this.gson = gson;
        this.modelMapper = modelMapper;
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

        List<ImportTownDTO> importTown = Arrays.stream(this.gson.fromJson(readTownsFileContent(), ImportTownDTO[].class)).toList();

        for (ImportTownDTO townDTO : importTown) {

            boolean isValid = validationUtils.isValid(townDTO);

            if (this.townRepository.findFirstByTownName(townDTO.getTownName()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                Town townToSave = this.modelMapper.map(townDTO, Town.class);
                this.townRepository.saveAndFlush(townToSave);
                sb.append(String.format(SUCCESSFULLY_IMPORTED_TOWN, townToSave.getTownName(), townToSave.getPopulation()));
            } else {
                sb.append(INVALID_TOWN).append(System.lineSeparator());
            }
        }

        return sb.toString();
    }
}
