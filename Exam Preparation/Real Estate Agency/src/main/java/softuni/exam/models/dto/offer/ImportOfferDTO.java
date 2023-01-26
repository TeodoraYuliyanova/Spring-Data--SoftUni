package softuni.exam.models.dto.offer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.dto.agent.AgentDTO;
import softuni.exam.models.dto.apartment.ApartmentDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
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
@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportOfferDTO {

    @Positive
    @NotNull
    @XmlElement
    private BigDecimal price;

    @NotNull
    @XmlElement
    private String publishedOn;

    @XmlElement
    private AgentDTO agent;

    @XmlElement
    private ApartmentDTO apartment;


}
