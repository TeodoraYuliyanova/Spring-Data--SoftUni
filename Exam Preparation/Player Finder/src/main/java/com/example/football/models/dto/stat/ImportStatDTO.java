package com.example.football.models.dto.stat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "stat")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportStatDTO {

    @Min(value = 1)
    @NotNull
    @XmlElement
    private Double shooting;

    @Min(value = 1)
    @NotNull
    @XmlElement
    private Double passing;

    @Min(value = 1)
    @NotNull
    @XmlElement
    private Double endurance;
}
