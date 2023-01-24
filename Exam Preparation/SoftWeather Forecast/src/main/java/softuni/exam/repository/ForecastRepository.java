package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.DaysOfWeek;
import softuni.exam.models.entity.Forecast;

import java.util.List;
import java.util.Optional;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast,Integer> {

    Optional<List<Forecast>> findAllByDayOfWeekAndCityPopulationLessThanOrderByMaxTemperatureDescIdAsc(DaysOfWeek dayOfWeek,Long city_population);

}
