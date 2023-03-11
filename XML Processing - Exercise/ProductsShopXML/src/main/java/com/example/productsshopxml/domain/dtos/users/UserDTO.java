package com.example.productsshopxml.domain.dtos.users;


import com.example.productsshopxml.domain.dtos.products.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String firstName;

    private String lastName;

    private Integer age;

    private Set<ProductDTO> soldProducts;

    private Set<ProductDTO> boughtProducts;

    public String getFullName(){
        return getFirstName() + " " + getLastName();
    }
}
