package com.example.productsshop.service.ProductService;

import com.example.productsshop.domain.dtos.products.ProductsInRangeWithNoBuyerDTO;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;


public interface ProductService {

    List<ProductsInRangeWithNoBuyerDTO> findAllByPriceBetweenAndBuyerIsNotNullOrderByPriceDesc(BigDecimal low, BigDecimal high) throws IOException;
}
