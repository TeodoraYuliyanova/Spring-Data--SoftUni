package exam.model.dtos.laptopDTO;

import exam.model.Shop;
import exam.model.dtos.shopDTO.ShopDTOJson;
import exam.model.enums.WarrantyType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ImportLaptopsDTO {

    @Size(min = 9)
    @NotNull
    private String macAddress;

    @Min(value = 1)
    @NotNull
    private Double cpuSpeed;

    @Min(value = 8)
    @Max(value = 128)
    @NotNull
    private Integer ram;

    @Min(value = 128)
    @Max(value = 1024)
    @NotNull
    private Integer storage;

    @Size(min = 10)
    @NotNull
    private String description;

    @Min(value = 1)
    @NotNull
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @NotNull
    private WarrantyType warrantyType;

    @NotNull
    private ShopDTOJson shop;

}
