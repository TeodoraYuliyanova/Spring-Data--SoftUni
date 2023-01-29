package exam.model.dtos.shopDTO;

import exam.model.dtos.townDTO.TownDtoXml;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "shop")
public class ImportShopDTO {


    @Size(min = 4)
    @NotNull
    @XmlElement
    private String address;

    @Min(value = 1)
    @Max(value = 50)
    @NotNull
    @XmlElement(name = "employee-count")
    private Integer employeeCount;


    @Min(value = 20000)
    @NotNull
    @XmlElement
    private BigDecimal income;

    @Size(min = 4)
    @NotNull
    @XmlElement
    private String name;

    @Min(value = 150)
    @NotNull
    @XmlElement(name = "shop-area")
    private Integer shopArea;

    @NotNull
    @XmlElement
    private TownDtoXml town;
}
