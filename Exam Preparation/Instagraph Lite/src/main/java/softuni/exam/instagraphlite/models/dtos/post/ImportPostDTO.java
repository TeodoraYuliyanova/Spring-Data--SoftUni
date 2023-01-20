package softuni.exam.instagraphlite.models.dtos.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.instagraphlite.models.dtos.picture.PictureDTO;
import softuni.exam.instagraphlite.models.dtos.user.UserDTO;
import softuni.exam.instagraphlite.models.enitites.Picture;
import softuni.exam.instagraphlite.models.enitites.User;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "post")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportPostDTO {

    @Size(min = 21)
    @NotNull
    @XmlElement
    private String caption;

    @NotNull
    @XmlElement
    private UserDTO user;

    @XmlElement
    @NotNull
    private PictureDTO picture;
}
