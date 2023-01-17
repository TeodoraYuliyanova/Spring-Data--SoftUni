package softuni.exam.models.dto.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.dto.car.CarDTO;
import softuni.exam.models.dto.mechanic.MechanicDTO;
import softuni.exam.models.dto.part.PartDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "task")
public class ImportTaskDTO {

    @NotNull
    @XmlElement
    private String date;

    @Positive
    @NotNull
    @XmlElement
    private BigDecimal price;

    @NotNull
    @XmlElement
    private CarDTO car;

    @NotNull
    @XmlElement
    private MechanicDTO mechanic;

    @NotNull
    @XmlElement
    private PartDTO part;
}
