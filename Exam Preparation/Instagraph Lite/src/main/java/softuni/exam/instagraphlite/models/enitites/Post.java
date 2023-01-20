package softuni.exam.instagraphlite.models.enitites;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post extends BaseEntity{

    @Size(min = 21)
    @Column(nullable = false)
    private String caption;

    @ManyToOne
    @NotNull
    private User user;

    @ManyToOne
    @NotNull
    private Picture picture;
}
