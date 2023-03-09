package com.example.gamestore.services.UserService;

import com.example.gamestore.domain.dtos.LogInUserDTO;
import com.example.gamestore.domain.dtos.UserRegisterDTO;
import com.example.gamestore.domain.entities.User;
import com.example.gamestore.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.gamestore.Constants.ExceptionMessages.EMAIL_ALREADY_EXISTS;
import static com.example.gamestore.Constants.ExceptionMessages.INCORRECT_USERNAME_OR_PASSWORD;
import static com.example.gamestore.Constants.Outputs.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    private User loggedInUser;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public String registerUser(String[] args) {
        final String email = args[1];
        final String password = args[2];
        final String confirmPassword = args[3];
        final String fullName = args[4];

        UserRegisterDTO userRegisterDTO;

        try {
            userRegisterDTO = new UserRegisterDTO(email, password, confirmPassword, fullName);

        } catch (IllegalArgumentException exception) {
            return exception.getMessage();
        }

        User registeredUser = this.modelMapper.map(userRegisterDTO, User.class);

        if (this.userRepository.count() == 0) {
            registeredUser.setAdministrator(true);
        }

        boolean isUserRegistered = this.userRepository.findByEmail(userRegisterDTO.getEmail()).isPresent();

        if (isUserRegistered) {
            return EMAIL_ALREADY_EXISTS;
        }

        this.userRepository.save(registeredUser);

        return String.format(SUCCESSFULLY_REGISTERED_USER, registeredUser.getFullName());
    }

    @Override
    public String logInUser(String[] args) {
        final String email = args[1];
        final String password = args[2];

        final LogInUserDTO userToLogIn = new LogInUserDTO(email, password);

        Optional<User> user = this.userRepository.findFirstByEmail(userToLogIn.getEmail());

        if (user.isPresent() && this.loggedInUser == null && user.get().getPassword().equals(userToLogIn.getPassword())) {

            this.loggedInUser = user.get();
            return String.format(SUCCESSFULLY_LOGGED_IN_USER, loggedInUser.getFullName());
        }

        return INCORRECT_USERNAME_OR_PASSWORD;
    }

    @Override
    public String logOut() {
        if (this.loggedInUser == null){
            return NO_USER_TO_LOG_OUT;
        }

        String fullNameOfUserLoggingOut = loggedInUser.getFullName();
        this.loggedInUser = null;
        return String.format(SUCCESSFUL_LOG_OUT, fullNameOfUserLoggingOut);
    }

    @Override
    public User getLoggedInUser() {
        return this.loggedInUser;
    }


}
