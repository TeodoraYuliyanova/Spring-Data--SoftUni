package softuni.exam.instagraphlite.models.dtos.picture;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ImportPicturesDTOJson {

    @NotNull
    private String path;

    @Min(value = 500)
    @Max(value = 60000)
    @NotNull
    private Double size;
}
