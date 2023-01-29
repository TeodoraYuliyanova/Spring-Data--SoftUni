package exam.model;

import exam.model.enums.WarrantyType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "laptops")
public class Laptop extends BaseEntity{

    @Size(min = 9)
    @Column(name = "mac_address",nullable = false,unique = true)
    private String macAddress;

    @Min(value = 1)
    @Column(nullable = false,name = "cpu_address")
    private Double cpuSpeed;

    @Min(value = 8)
    @Max(value = 128)
    @Column(nullable = false)
    private Integer ram;

    @Min(value = 128)
    @Max(value = 1024)
    @Column(nullable = false)
    private Integer storage;

    @Size(min = 10)
    @Column(nullable = false,columnDefinition = "TEXT")
    private String description;

    @Min(value = 1)
    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "warranty_type")
    private WarrantyType warrantyType;

    @ManyToOne
    private Shop shop;


}
