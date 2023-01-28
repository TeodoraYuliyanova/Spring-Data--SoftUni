package com.example.football.service.impl;

import com.example.football.models.dto.stat.ImportStatDTO;
import com.example.football.models.dto.stat.ImportStatsWrapperDTO;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
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
import java.util.List;

import static com.example.football.constants.Messages.INVALID_STAT;
import static com.example.football.constants.Messages.SUCCESSFULLY_IMPORTED_STAT;
import static com.example.football.constants.Paths.STATS_PATH_XML;

@Service
public class StatServiceImpl implements StatService {

    private final StatRepository statRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;

    @Autowired
    public StatServiceImpl(StatRepository statRepository, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtils validationUtils) {
        this.statRepository = statRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
    }


    @Override
    public boolean areImported() {
        return this.statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(STATS_PATH_XML);
    }

    @Override
    public String importStats() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        File file = STATS_PATH_XML.toFile();

        ImportStatsWrapperDTO importStatsWrapperDTO = this.xmlParser.fromFile(file, ImportStatsWrapperDTO.class);

        List<ImportStatDTO> importStatsDTO = importStatsWrapperDTO.getStat().stream().toList();

        for (ImportStatDTO importStatDTO : importStatsDTO) {
            boolean isValid = validationUtils.isValid(importStatDTO);

            if (this.statRepository.findAllByPassingAndShootingAndEndurance(
                    importStatDTO.getPassing(), importStatDTO.getShooting(), importStatDTO.getEndurance()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                Stat stat = this.modelMapper.map(importStatDTO, Stat.class);
                this.statRepository.saveAndFlush(stat);
                sb.append(String.format(SUCCESSFULLY_IMPORTED_STAT, stat.getShooting(),stat.getPassing(),stat.getEndurance()));
            } else {
                sb.append(INVALID_STAT).append(System.lineSeparator());
            }
        }


        return sb.toString();
    }
}
