package softuni.exam.models.dto.car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.enums.CarType;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportCarDTO {

    @Size(min = 2, max = 30)
    @NotNull
    @XmlElement
    private String carMake;

    @Size(min = 2, max = 30)
    @NotNull
    @XmlElement
    private String carModel;

    @Min(value = 1)
    @NotNull
    @XmlElement
    private Integer year;

    @Size(min = 2, max = 30)
    @NotNull
    @XmlElement
    private String plateNumber;

    @Min(value = 1)
    @NotNull
    @XmlElement
    private Integer kilometers;

    @Min(value = 1)
    @NotNull
    @XmlElement
    private Double engine;


    @Enumerated(EnumType.STRING)
    @NotNull
    @XmlElement
    private CarType carType;


}
