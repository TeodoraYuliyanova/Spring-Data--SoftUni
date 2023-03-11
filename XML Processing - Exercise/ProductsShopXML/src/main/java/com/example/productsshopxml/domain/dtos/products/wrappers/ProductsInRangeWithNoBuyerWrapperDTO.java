package com.example.productsshopxml.domain.dtos.products.wrappers;

import com.example.productsshopxml.domain.dtos.products.ProductInRangeWithNoBuyerDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsInRangeWithNoBuyerWrapperDTO {

    @XmlElement
    private List<ProductInRangeWithNoBuyerDTO> product;
}
