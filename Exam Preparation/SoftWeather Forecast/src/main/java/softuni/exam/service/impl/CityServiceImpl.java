package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.city.ImportCityDTO;
import softuni.exam.models.entity.City;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CityService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.Constants.Messages.*;
import static softuni.exam.Constants.Paths.CITIES_PATH;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final Gson gson = new Gson();
    private final ValidationUtils validationUtils;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, CountryRepository countryRepository, ValidationUtils validationUtils) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(CITIES_PATH);
    }

    @Override
    public String importCities() throws IOException {
        StringBuilder sb = new StringBuilder();

        List<ImportCityDTO> importCityDTO = Arrays.stream(gson.fromJson(readCitiesFileContent(), ImportCityDTO[].class)).toList();

        for (ImportCityDTO city : importCityDTO) {

            boolean isValid = validationUtils.isValid(city);

            if (this.cityRepository.findFirstByCityName(city.getCityName()).isPresent()) {
                continue;
            }

            if (isValid) {
                sb.append(String.format(SUCCESSFULLY_IMPORTED_CITY, city.getCityName(), city.getPopulation()))
                        .append(System.lineSeparator());
                if (this.countryRepository.findById(city.getCountry()).isPresent()) {

                    City cityToSave = this.modelMapper.map(city, City.class);
                    cityToSave.setCountry(this.countryRepository.findById(city.getCountry()).get());
                    this.cityRepository.saveAndFlush(cityToSave);
                }else {
                    sb.append("Error").append(System.lineSeparator());
                }

            } else {
                sb.append(INCORRECT_CITY).append(System.lineSeparator());
            }
        }
        return sb.toString();
    }
}
