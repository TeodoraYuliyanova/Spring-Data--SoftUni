package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.mechanic.ImportMechanicsDTO;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.repository.MechanicRepository;
import softuni.exam.service.MechanicService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.constants.Messages.INVALID_MECHANIC;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_MECHANIC;
import static softuni.exam.constants.Paths.MECHANICS_IMPORT_JSON;

@Service
public class MechanicServiceImpl implements MechanicService {

    private final MechanicRepository mechanicRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtils validationUtils;

    @Autowired
    public MechanicServiceImpl(MechanicRepository mechanicRepository, ModelMapper modelMapper, Gson gson, ValidationUtils validationUtils) {
        this.mechanicRepository = mechanicRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
    }


    @Override
    public boolean areImported() {
        return this.mechanicRepository.count() > 0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        return Files.readString(MECHANICS_IMPORT_JSON);
    }

    @Override
    public String importMechanics() throws IOException {
        StringBuilder sb = new StringBuilder();

        List<ImportMechanicsDTO> importMechanicsDTO = Arrays.stream(this.gson.fromJson(readMechanicsFromFile(), ImportMechanicsDTO[].class)).toList();

        for (ImportMechanicsDTO mechanicDTO : importMechanicsDTO) {
            boolean isValid = this.validationUtils.isValid(mechanicDTO);

            if (this.mechanicRepository.findFirstByEmail(mechanicDTO.getEmail()).isPresent()){
                isValid = false;
            }

            if (isValid){
                Mechanic mechanicToSave = this.modelMapper.map(mechanicDTO, Mechanic.class);
                this.mechanicRepository.saveAndFlush(mechanicToSave);
                sb.append(String.format(SUCCESSFULLY_IMPORTED_MECHANIC,mechanicToSave.getFirstName(),mechanicToSave.getLastName()));
            }else{
                sb.append(INVALID_MECHANIC).append(System.lineSeparator());
            }
        }


        return sb.toString();
    }
}
