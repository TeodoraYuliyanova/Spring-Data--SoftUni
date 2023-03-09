package com.example.productsshop.service.CategoryService;
import com.example.productsshop.domain.dtos.categories.CategoryByProductsSummaryDto;

import java.io.IOException;
import java.util.List;


public interface CategoryService {

    List<CategoryByProductsSummaryDto> getCategoriesSummary() throws IOException;
}
