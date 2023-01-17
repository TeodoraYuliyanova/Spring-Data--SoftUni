package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.part.ImportPartsDTO;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.PartRepository;
import softuni.exam.service.PartService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.constants.Messages.INVALID_PART;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_PART;
import static softuni.exam.constants.Paths.PARTS_IMPORT_JSON;

@Service
public class PartServiceImpl implements PartService {
    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtils validationUtils;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper, Gson gson, ValidationUtils validationUtils) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
    }


    @Override
    public boolean areImported() {
        return this.partRepository.count() > 0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        return Files.readString(PARTS_IMPORT_JSON);
    }

    @Override
    public String importParts() throws IOException {
        StringBuilder sb = new StringBuilder();

        List<ImportPartsDTO> importPartsDTO = Arrays.stream(this.gson.fromJson(readPartsFileContent(), ImportPartsDTO[].class)).toList();
        for (ImportPartsDTO partDTO : importPartsDTO) {
            boolean isValid = this.validationUtils.isValid(partDTO);

            if (this.partRepository.findFirstByPartName(partDTO.getPartName()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                Part partToSave = this.modelMapper.map(partDTO, Part.class);
                this.partRepository.saveAndFlush(partToSave);
                sb.append(String.format(SUCCESSFULLY_IMPORTED_PART, partToSave.getPartName(), partToSave.getPrice()));
            } else {
                sb.append(INVALID_PART).append(System.lineSeparator());
            }
        }

        return sb.toString();
    }
}
