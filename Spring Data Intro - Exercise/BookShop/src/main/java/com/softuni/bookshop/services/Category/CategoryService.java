package com.softuni.bookshop.services.Category;

import com.softuni.bookshop.domain.entities.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {

    void seedCategories(List<Category> categories);
    boolean isDataSeeded();

    Set<Category> getRandomCategories();
}
