package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dtos.user.ImportUserDTOJson;
import softuni.exam.instagraphlite.models.enitites.Picture;
import softuni.exam.instagraphlite.models.enitites.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static softuni.exam.instagraphlite.constants.Messages.*;
import static softuni.exam.instagraphlite.constants.Paths.USERS_IMPORT_JSON;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;
    private final Gson gson;
    private final PictureRepository pictureRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtils validationUtils, Gson gson, PictureRepository pictureRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.gson = gson;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public boolean areImported() {
        return this.userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(USERS_IMPORT_JSON);
    }

    @Override
    public String importUsers() throws IOException {
        StringBuilder sb = new StringBuilder();

        List<ImportUserDTOJson> importUsersDTOJson = Arrays.stream(this.gson.fromJson(readFromFileContent(), ImportUserDTOJson[].class)).toList();

        for (ImportUserDTOJson importUserDTO : importUsersDTOJson) {
            boolean isValid = validationUtils.isValid(importUserDTO);

            if (this.userRepository.findFirstByUsername(importUserDTO.getUsername()).isPresent()) {
                isValid = false;
            }

            if (isValid) {
                if (this.pictureRepository.findFirstByPath(importUserDTO.getProfilePicture()).isPresent()) {
                    User user = this.modelMapper.map(importUserDTO, User.class);
                    Picture picture = this.pictureRepository.findFirstByPath(importUserDTO.getProfilePicture()).get();
                    user.setProfilePicture(picture);
                    this.userRepository.saveAndFlush(user);
                    sb.append(String.format(SUCCESSFULLY_IMPORTED_USER, user.getUsername()));
                }else{
                    sb.append(INVALID_USER).append(System.lineSeparator());
                }
            } else {
                sb.append(INVALID_USER).append(System.lineSeparator());
            }
        }

        return sb.toString();
    }

    @Override
    public String exportUsersWithTheirPosts() {
        return null;
    }
}
