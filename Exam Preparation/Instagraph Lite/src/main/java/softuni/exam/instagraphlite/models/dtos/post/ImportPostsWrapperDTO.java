package softuni.exam.instagraphlite.models.dtos.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "posts")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportPostsWrapperDTO {

    @XmlElement(name = "post")
    private List<ImportPostDTO> posts;

}
