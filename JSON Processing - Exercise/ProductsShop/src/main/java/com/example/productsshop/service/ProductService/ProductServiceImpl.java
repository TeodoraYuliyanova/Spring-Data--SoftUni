package com.example.productsshop.service.ProductService;

import com.example.productsshop.domain.dtos.products.ProductDTO;
import com.example.productsshop.domain.dtos.products.ProductsInRangeWithNoBuyerDTO;
import com.example.productsshop.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import static com.example.productsshop.constants.Paths.PRODUCTS_WITH_NO_BUYERS_IN_RANGE_JSON_PATH;
import static com.example.productsshop.constants.Utils.writeJsonIntoFile;


@Service
public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper = new ModelMapper();

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    @Transactional
    public List<ProductsInRangeWithNoBuyerDTO> findAllByPriceBetweenAndBuyerIsNotNullOrderByPriceDesc(BigDecimal low, BigDecimal high) throws IOException {
        List<ProductsInRangeWithNoBuyerDTO> products = this.productRepository.findAllByPriceBetweenAndBuyerIsNotNullOrderByPriceDesc(low, high)
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .map(ProductDTO::toProductsInRangeWithNoBuyerDTO)
                .toList();

        writeJsonIntoFile(products,PRODUCTS_WITH_NO_BUYERS_IN_RANGE_JSON_PATH);
        return products;
    }
}
