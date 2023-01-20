package softuni.exam.instagraphlite.models.enitites;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @Size(min = 2,max = 18)
    @Column(nullable = false,unique = true)
    private String username;

    @Size(min = 4)
    @Column(nullable = false)
    private String password;

    @ManyToOne
    @NotNull
    private Picture profilePicture;


}
