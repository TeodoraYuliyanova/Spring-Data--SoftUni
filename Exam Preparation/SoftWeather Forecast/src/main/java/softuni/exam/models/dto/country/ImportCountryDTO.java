package softuni.exam.models.dto.country;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class ImportCountryDTO {


    @Size(min = 2,max = 60)
    private String countryName;

    @Size(min = 2,max = 20)
    private String currency;
}
