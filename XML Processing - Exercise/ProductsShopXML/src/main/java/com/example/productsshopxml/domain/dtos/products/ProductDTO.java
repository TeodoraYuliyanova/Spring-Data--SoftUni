package com.example.productsshopxml.domain.dtos.products;

import com.example.productsshopxml.domain.dtos.users.UserDTO;
import com.example.productsshopxml.domain.entities.Category;
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

    private UserDTO seller;

    private UserDTO buyer;

    private Set<Category> categories;

    public ProductInRangeWithNoBuyerDTO toProductInRangeWithNoBuyerDTO(){
       return new ProductInRangeWithNoBuyerDTO(name,price,seller.getFullName());
    }
}
