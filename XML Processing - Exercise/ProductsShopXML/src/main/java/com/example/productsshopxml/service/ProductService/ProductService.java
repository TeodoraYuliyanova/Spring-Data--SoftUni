package com.example.productsshopxml.service.ProductService;

import com.example.productsshopxml.domain.dtos.products.ProductInRangeWithNoBuyerDTO;

import javax.xml.bind.JAXBException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

   List<ProductInRangeWithNoBuyerDTO> findAllByPriceBetweenAndBuyerNotNullOrderByPrice(BigDecimal low, BigDecimal high) throws JAXBException;
}
