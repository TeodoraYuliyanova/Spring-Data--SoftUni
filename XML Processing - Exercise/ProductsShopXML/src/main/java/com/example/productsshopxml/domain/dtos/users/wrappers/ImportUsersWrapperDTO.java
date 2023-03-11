package com.example.productsshopxml.domain.dtos.users.wrappers;

import com.example.productsshopxml.domain.dtos.users.ImportUserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportUsersWrapperDTO {

    @XmlElement(name = "user")
    private List<ImportUserDTO> users;
}
