package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.car.ImportCarDTO;
import softuni.exam.models.dto.car.ImportCarsWrapperDTO;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static softuni.exam.constants.Messages.INVALID_CAR;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_CAR;
import static softuni.exam.constants.Paths.CARS_IMPORT_XML;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtils validationUtils;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtils validationUtils) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
    }


    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFromFile() throws IOException {
        return Files.readString(CARS_IMPORT_XML);
    }

    @Override
    public String importCars() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        File file = CARS_IMPORT_XML.toFile();
        ImportCarsWrapperDTO importCarsWrapperDTO = this.xmlParser.fromFile(file, ImportCarsWrapperDTO.class);
        List<ImportCarDTO> importCarDTO = importCarsWrapperDTO.getCars().stream().toList();

        for (ImportCarDTO carDTO : importCarDTO) {
            boolean isValid = this.validationUtils.isValid(carDTO);

            if (this.carRepository.findFirstByPlateNumber(carDTO.getPlateNumber()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                Car carToSave = this.modelMapper.map(carDTO, Car.class);
                this.carRepository.saveAndFlush(carToSave);
                sb.append(String.format(SUCCESSFULLY_IMPORTED_CAR, carToSave.getCarMake(), carToSave.getCarModel()));
            } else {
                sb.append(INVALID_CAR).append(System.lineSeparator());
            }
        }

        return sb.toString();
    }
}
