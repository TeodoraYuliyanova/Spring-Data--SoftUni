package com.example.productsshop.domain.dtos.products;

import com.example.productsshop.domain.dtos.categories.CategoryDTO;
import com.example.productsshop.domain.dtos.users.UserDTO;
import com.example.productsshop.domain.entities.Category;
import com.example.productsshop.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private String name;
    private BigDecimal price;

    private UserDTO buyer;
    private UserDTO seller;

    private Set<CategoryDTO> categories;


    public ProductsInRangeWithNoBuyerDTO toProductsInRangeWithNoBuyerDTO(){
        return new ProductsInRangeWithNoBuyerDTO(name,price, seller.getFullName());
    }
}
