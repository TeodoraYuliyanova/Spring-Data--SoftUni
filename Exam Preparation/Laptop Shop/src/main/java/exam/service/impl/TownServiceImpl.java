package exam.service.impl;

import exam.model.Town;
import exam.model.dtos.townDTO.ImportTownDTO;
import exam.model.dtos.townDTO.ImportTownsWrapperDTO;
import exam.repository.TownRepository;
import exam.service.TownService;
import exam.util.ValidationUtils;
import exam.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static exam.constants.Messages.INVALID_TOWN;
import static exam.constants.Messages.SUCCESSFULLY_IMPORTED_TOWN;
import static exam.constants.Paths.TOWNS_IMPORT_XML;

@Service
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;
    private final XmlParser xmlParser;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, ValidationUtils validationUtils, XmlParser xmlParser) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
    }


    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(TOWNS_IMPORT_XML);
    }

    @Override
    public String importTowns() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        File file = TOWNS_IMPORT_XML.toFile();
        ImportTownsWrapperDTO importTownsWrapperDTO = xmlParser.fromFile(file, ImportTownsWrapperDTO.class);
        List<ImportTownDTO> importTownDTO = importTownsWrapperDTO.getTown().stream().toList();

        for (ImportTownDTO townDTO : importTownDTO) {
            boolean isValid = validationUtils.isValid(townDTO);

            if (this.townRepository.findFirstByName(townDTO.getName()).isPresent()){
                isValid = false;
            }

            if (isValid){
                Town town = this.modelMapper.map(townDTO, Town.class);
                this.townRepository.saveAndFlush(town);
                sb.append(String.format(SUCCESSFULLY_IMPORTED_TOWN,town.getName()));
            }else{
                sb.append(INVALID_TOWN).append(System.lineSeparator());
            }
        }


        return sb.toString();
    }
}
