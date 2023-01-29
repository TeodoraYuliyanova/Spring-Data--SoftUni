package exam.model.dtos.townDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
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
@XmlRootElement(name = "town")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportTownDTO {

    @Size(min = 2)
    @NotNull
    @XmlElement
    private String name;

    @Min(value = 1)
    @NotNull
    @XmlElement
    private Long population;

    @Size(min = 10)
    @NotNull
    @XmlElement(name = "travel-guide")
    private String travelGuide;
}
