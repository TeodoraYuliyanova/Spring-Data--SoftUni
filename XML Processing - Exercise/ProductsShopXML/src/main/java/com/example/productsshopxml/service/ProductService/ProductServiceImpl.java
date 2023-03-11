package com.example.productsshopxml.service.ProductService;

import com.example.productsshopxml.domain.dtos.products.ProductDTO;
import com.example.productsshopxml.domain.dtos.products.ProductInRangeWithNoBuyerDTO;
import com.example.productsshopxml.domain.dtos.products.wrappers.ProductsInRangeWithNoBuyerWrapperDTO;
import com.example.productsshopxml.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductServiceImpl implements ProductService{

    private final ModelMapper modelMapper = new ModelMapper();

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public List<ProductInRangeWithNoBuyerDTO> findAllByPriceBetweenAndBuyerNotNullOrderByPrice(BigDecimal low, BigDecimal high) throws JAXBException {
        List<ProductInRangeWithNoBuyerDTO> products = this.productRepository.findAllByPriceBetweenAndBuyerNotNullOrderByPrice(low, high).orElseThrow(NoSuchElementException::new)
                .stream().map(product -> modelMapper.map(product, ProductDTO.class))
                .map(ProductDTO::toProductInRangeWithNoBuyerDTO).toList();

        final ProductsInRangeWithNoBuyerWrapperDTO productsInRangeWithNoBuyerWrapperDTO = new ProductsInRangeWithNoBuyerWrapperDTO(products);

        final JAXBContext context = JAXBContext.newInstance(productsInRangeWithNoBuyerWrapperDTO.getClass());
        final Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        File file = new File("src/main/resources/outputs/products-in-range.xml");
        marshaller.marshal(productsInRangeWithNoBuyerWrapperDTO,file);

        return products;
    }
}
