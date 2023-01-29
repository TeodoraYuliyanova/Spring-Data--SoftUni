package exam.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "towns")
public class Town extends BaseEntity{

    @Size(min = 2)
    @Column(unique = true,nullable = false)
    private String name;

    @Min(value = 1)
    @Column(nullable = false)
    private Long population;

    @Size(min = 10)
    @Column(nullable = false,columnDefinition = "TEXT")
    private String travelGuide;
}
