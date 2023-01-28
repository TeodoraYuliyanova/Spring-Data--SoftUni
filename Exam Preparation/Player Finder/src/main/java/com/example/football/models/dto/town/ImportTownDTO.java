package com.example.football.models.dto.town;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class ImportTownDTO {

    @Size(min = 2)
    @NotNull
    private String name;

    @Min(value = 1)
    @NotNull
    private Integer population;

    @Size(min = 10)
    @NotNull
    private String travelGuide;
}
