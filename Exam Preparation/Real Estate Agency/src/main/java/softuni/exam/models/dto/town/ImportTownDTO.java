package softuni.exam.models.dto.town;

import lombok.AllArgsConstructor;
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
    private String townName;

    @Min(value = 1)
    @NotNull
    private Integer population;
}
