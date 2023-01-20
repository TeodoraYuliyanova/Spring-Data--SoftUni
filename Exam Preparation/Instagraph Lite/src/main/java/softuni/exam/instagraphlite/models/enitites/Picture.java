package softuni.exam.instagraphlite.models.enitites;

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
@Table(name = "pictures")
public class Picture extends BaseEntity{

    @Column(nullable = false,unique = true)
    private String path;

    @Min(value = 500)
    @Max(value = 60000)
    @Column(nullable = false)
    private Double size;
}
