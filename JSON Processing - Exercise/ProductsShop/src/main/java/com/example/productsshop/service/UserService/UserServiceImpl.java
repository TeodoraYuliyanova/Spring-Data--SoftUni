package com.example.productsshop.service.UserService;

import com.example.productsshop.domain.dtos.users.UsersWithSoldProductsDTO;
import com.example.productsshop.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static com.example.productsshop.constants.Paths.USERS_WITH_SOLD_PRODUCTS_WITH_BUYER;
import static com.example.productsshop.constants.Utils.writeJsonIntoFile;

@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper = new ModelMapper();
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UsersWithSoldProductsDTO> findAllBySoldProductsIsNotNullAndSoldProductsBuyerLastNameIsNotNullOrderBySoldProductsBuyerFirstName() throws IOException {
        List<UsersWithSoldProductsDTO> users = this.userRepository.findAllBySoldProductsIsNotNullAndSoldProductsBuyerLastNameIsNotNullOrderBySoldProductsBuyerFirstName()
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(user -> modelMapper.map(user, UsersWithSoldProductsDTO.class)).toList();

        writeJsonIntoFile(users, USERS_WITH_SOLD_PRODUCTS_WITH_BUYER);

        return users;

    }
}
