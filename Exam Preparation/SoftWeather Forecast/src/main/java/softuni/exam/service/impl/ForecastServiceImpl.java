package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.DaysOfWeek;
import softuni.exam.models.dto.forecast.ImportForecastDTO;
import softuni.exam.models.dto.forecast.ImportForecastsWrapper;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Forecast;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.ForecastService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.Constants.Messages.*;
import static softuni.exam.Constants.Paths.FORECASTS_PATH;

@Service
public class ForecastServiceImpl implements ForecastService {

    private final ForecastRepository forecastRepository;
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final XmlParser xmlParser;
    private final ValidationUtils validationUtils;

    @Autowired
    public ForecastServiceImpl(ForecastRepository forecastRepository, CityRepository cityRepository, XmlParser xmlParser, ValidationUtils validationUtils) {
        this.forecastRepository = forecastRepository;
        this.cityRepository = cityRepository;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(FORECASTS_PATH);
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        final File file = FORECASTS_PATH.toFile();
        final ImportForecastsWrapper forecastsWrapper = xmlParser.fromFile(file, ImportForecastsWrapper.class);

        List<ImportForecastDTO> forecasts = forecastsWrapper.getForecasts();
        for (ImportForecastDTO forecast : forecasts) {

            boolean isValid = validationUtils.isValid(forecast);

            if (isValid) {
                if (this.cityRepository.findById(forecast.getCity()).isPresent()) {
                    final City city = this.cityRepository.findById(forecast.getCity()).get();

                    final Forecast forecastToSave = this.modelMapper.map(forecast, Forecast.class);
                    forecastToSave.setCity(city);
                    forecastToSave.setSunrise(LocalTime.parse(forecast.getSunrise(),
                           DateTimeFormatter.ofPattern("HH:mm:ss")));
                    forecastToSave.setSunset(LocalTime.parse(forecast.getSunset(),
                           DateTimeFormatter.ofPattern("HH:mm:ss")));
                    this.forecastRepository.saveAndFlush(forecastToSave);
                    sb.append(String.format(SUCCESSFULLY_IMPORTED_FORECAST, forecast.getDayOfWeek(), forecast.getMaxTemperature()))
                            .append(System.lineSeparator());
                }
                continue;
            }
                sb.append(INCORRECT_FORECAST).append(System.lineSeparator());
        }
        return sb.toString();
    }

    @Override
    public String exportForecasts() {

        final List<Forecast> forecasts = this.forecastRepository.findAllByDayOfWeekAndCityPopulationLessThanOrderByMaxTemperatureDescIdAsc(DaysOfWeek.SUNDAY, 150000L)
                .orElseThrow(NoSuchElementException::new);

        return forecasts.stream().map(forecast -> String.format(FORECASTS_OUTPUT_FORMAT,
                forecast.getCity().getCityName(), forecast.getMinTemperature(), forecast.getMaxTemperature(), forecast.getSunrise(),
                forecast.getSunset())).collect(Collectors.joining(System.lineSeparator()));

    }
}
