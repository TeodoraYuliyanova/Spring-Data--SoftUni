package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.country.ImportCountryDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.Constants.Messages.*;
import static softuni.exam.Constants.Paths.COUNTRIES_PATH;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final Gson gson = new Gson();
    private final ValidationUtils validationUtils;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, ValidationUtils validationUtils) {
        this.countryRepository = countryRepository;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(COUNTRIES_PATH);
    }

    @Override
    public String importCountries() throws IOException {
        StringBuilder sb = new StringBuilder();

        List<ImportCountryDTO> importCountryDTO = Arrays.stream(gson.fromJson(readCountriesFromFile(), ImportCountryDTO[].class)).toList();

        for (ImportCountryDTO countryDTO : importCountryDTO) {
            boolean isValid = validationUtils.isValid(countryDTO);

            if (this.countryRepository.findFirstByCountryName(countryDTO.getCountryName()).isPresent()){
                isValid = false;
            }

            if (isValid){
                Country country = this.modelMapper.map(countryDTO, Country.class);
                this.countryRepository.saveAndFlush(country);
                sb.append(String.format(SUCCESSFULLY_IMPORTED_COUNTRY, country.getCountryName(),country.getCurrency()))
                        .append(System.lineSeparator());
            }else {
                sb.append(String.format(INCORRECT_COUNTRY)).append(System.lineSeparator());
            }
        }

        return sb.toString();
    }
}
