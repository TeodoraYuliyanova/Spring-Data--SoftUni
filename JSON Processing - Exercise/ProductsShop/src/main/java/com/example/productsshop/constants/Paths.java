package com.example.productsshop.constants;

import java.nio.file.Path;

public enum Paths {
    ;
    public static final Path PRODUCTS_WITH_NO_BUYERS_IN_RANGE_JSON_PATH =
            Path.of("src/main/resources/outputs/products-in-range.json");

    public static final Path USERS_WITH_SOLD_PRODUCTS_WITH_BUYER =
            Path.of("src/main/resources/outputs/users-sold-products.json");
    public static final Path CATEGORIES_BY_PRODUCT_COUNT =
            Path.of("src/main/resources/outputs/categories-by-products.json");
}
