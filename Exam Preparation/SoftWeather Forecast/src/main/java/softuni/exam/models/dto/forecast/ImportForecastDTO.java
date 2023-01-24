package softuni.exam.models.dto.forecast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.DaysOfWeek;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "forecast")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportForecastDTO {

    @NotNull
    @XmlElement(name = "day_of_week")
    private DaysOfWeek dayOfWeek;

    @DecimalMin(value = "-20")
    @DecimalMax(value = "60")
    @NotNull
    @XmlElement(name = "max_temperature")
    private Double maxTemperature;

    @DecimalMin(value = "-50")
    @DecimalMax(value = "40")
    @NotNull
    @XmlElement(name = "min_temperature")
    private Double minTemperature;

    @NotNull
    @XmlElement
    private String sunrise;

    @NotNull
    @XmlElement
    private String sunset;

    @XmlElement
    @NotNull
    private Integer city;
}
