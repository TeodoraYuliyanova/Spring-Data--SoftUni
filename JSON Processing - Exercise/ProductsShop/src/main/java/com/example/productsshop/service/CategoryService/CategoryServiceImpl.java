package com.example.productsshop.service.CategoryService;

import com.example.productsshop.domain.dtos.categories.CategoryByProductsSummaryDto;
import com.example.productsshop.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static com.example.productsshop.constants.Paths.CATEGORIES_BY_PRODUCT_COUNT;
import static com.example.productsshop.constants.Utils.writeJsonIntoFile;


@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryByProductsSummaryDto> getCategoriesSummary() throws IOException {
        List<CategoryByProductsSummaryDto> categories = this.categoryRepository.getCategoriesSummary()
                .orElseThrow(NoSuchElementException::new);

        writeJsonIntoFile(categories,CATEGORIES_BY_PRODUCT_COUNT);
        return categories;
    }
}
