package com.example.productsshop.domain.dtos.users;

import com.example.productsshop.domain.dtos.products.SoldProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersWithSoldProductsDTO {

    private String firstName;

    private String lastName;

    private List<SoldProductDTO> soldProducts;

}
