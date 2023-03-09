package com.example.productsshop.service.UserService;

import com.example.productsshop.domain.dtos.users.UsersWithSoldProductsDTO;

import java.io.IOException;
import java.util.List;

public interface UserService {

    List<UsersWithSoldProductsDTO> findAllBySoldProductsIsNotNullAndSoldProductsBuyerLastNameIsNotNullOrderBySoldProductsBuyerFirstName() throws IOException;
}
