package com.example.productsshop.domain.dtos.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductsInRangeWithNoBuyerDTO {

    private String name;
    private BigDecimal price;
    private String seller;
}
