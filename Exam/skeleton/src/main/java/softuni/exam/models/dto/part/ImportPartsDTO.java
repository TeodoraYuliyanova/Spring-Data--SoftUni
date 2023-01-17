package softuni.exam.models.dto.part;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class ImportPartsDTO {

    @Size(min = 2,max = 19)
    @NotNull
    private String partName;

    @Min(value = 10)
    @Max(value = 2000)
    @NotNull
    private Double price;

    @Min(value = 1)
    @NotNull
    private Integer quantity;
}
