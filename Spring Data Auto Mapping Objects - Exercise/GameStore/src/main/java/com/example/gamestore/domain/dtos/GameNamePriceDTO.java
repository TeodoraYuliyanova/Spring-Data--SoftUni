package com.example.gamestore.domain.dtos;

import java.math.BigDecimal;

public interface GameNamePriceDTO {

    String getTitle();
    BigDecimal getPrice();

    default String info() {
        return getTitle() + " " + getPrice();
    }

}
