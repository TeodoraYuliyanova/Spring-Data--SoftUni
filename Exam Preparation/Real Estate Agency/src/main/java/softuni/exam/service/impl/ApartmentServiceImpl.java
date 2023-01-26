package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.apartment.ImportApartmentDTO;
import softuni.exam.models.dto.apartment.ImportApartmentsWrapperDTO;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import static softuni.exam.constants.Messages.INVALID_APARTMENT;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_APARTMENT;
import static softuni.exam.constants.Paths.APARTMENTS_PATH_XML;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentRepository apartmentRepository;

    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtils validationUtils;
    private final TownRepository townRepository;

    @Autowired
    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtils validationUtils, TownRepository townRepository) {
        this.apartmentRepository = apartmentRepository;

        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return this.apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(APARTMENTS_PATH_XML);
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        File file = APARTMENTS_PATH_XML.toFile();
        ImportApartmentsWrapperDTO importApartmentsWrapperDTO = xmlParser.fromFile(file, ImportApartmentsWrapperDTO.class);
        List<ImportApartmentDTO> apartmentDTO = importApartmentsWrapperDTO.getApartment();

        for (ImportApartmentDTO importApartmentDTO : apartmentDTO) {
            boolean isValid = validationUtils.isValid(importApartmentDTO);

            Optional<Apartment> apartmentToCheck = this.apartmentRepository.findApartmentByAreaAndTownName(importApartmentDTO.getArea(), importApartmentDTO.getTown());

            if (apartmentToCheck.isPresent()) {
                isValid = false;
            }

            if (isValid) {
                if (this.townRepository.findFirstByTownName(importApartmentDTO.getTown()).isPresent()) {
                    Town townToSet = this.townRepository.findFirstByTownName(importApartmentDTO.getTown()).get();
                    Apartment apartment = this.modelMapper.map(importApartmentDTO, Apartment.class);
                    apartment.setTown(townToSet);
                    this.apartmentRepository.saveAndFlush(apartment);
                    sb.append(String.format(SUCCESSFULLY_IMPORTED_APARTMENT, apartment.getApartmentType(), apartment.getArea()));
                }
            } else {
                sb.append(INVALID_APARTMENT).append(System.lineSeparator());
            }
        }


        return sb.toString();
    }
}
