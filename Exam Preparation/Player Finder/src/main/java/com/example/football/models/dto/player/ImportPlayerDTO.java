package com.example.football.models.dto.player;

import com.example.football.models.dto.stat.StatDTO;
import com.example.football.models.dto.team.TeamDTO;
import com.example.football.models.dto.town.TownDTO;
import com.example.football.models.entity.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "player")
public class ImportPlayerDTO {

    @Size(min = 3)
    @NotNull
    @XmlElement(name = "first-name")
    private String firstName;

    @Size(min = 3)
    @NotNull
    @XmlElement(name = "last-name")
    private String lastName;

    @Email
    @NotNull
    @XmlElement
    private String email;

    @NotNull
    @XmlElement(name = "birth-date")
    private String birthDate;

    @NotNull
    @XmlElement
    private Position position;

    @NotNull
    @XmlElement
    private TownDTO town;

    @NotNull
    @XmlElement
    private TeamDTO team;

    @NotNull
    @XmlElement
    private StatDTO stat;
}
