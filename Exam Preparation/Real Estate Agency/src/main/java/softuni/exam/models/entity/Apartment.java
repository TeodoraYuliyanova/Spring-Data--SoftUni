package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.ApartmentType;

import javax.persistence.*;
import javax.validation.constraints.Min;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "apartments")
public class Apartment extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(name = "apartment_type",nullable = false)
    private ApartmentType apartmentType;

    @Min(value = 40)
    @Column(nullable = false)
    private double area;

    @ManyToOne
    private Town town;
}
