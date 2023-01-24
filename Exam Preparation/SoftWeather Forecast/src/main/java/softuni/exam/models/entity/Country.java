package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "countries")
public class Country extends BaseEntity{


    @Size(min = 2,max = 60)
    @Column(name = "country_name",nullable = false,unique = true)
    private String countryName;


    @Size(min = 2,max = 20)
    @Column(nullable = false)
    private String currency;

}
