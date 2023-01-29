package exam.model.dtos.shopDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ShopDTOJson {

    @NotNull
    private String name;
}
