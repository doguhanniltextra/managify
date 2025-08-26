package com.econ.managify.services;

import com.econ.managify.configs.JwtProvider;
import com.econ.managify.exceptions.AuthException;
import com.econ.managify.interfaces.UserService;
import com.econ.managify.models.User;
import com.econ.managify.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImp {

    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User findUserProfileByJwt(String jwt) throws AuthException {
        String email = JwtProvider.getEmailFromToken(jwt);
        return findUserByEmail(email);
    }


    public User findUserByEmail(String email) throws AuthException {
        User user = userRepository.findByEmail(email);
        if(user == null) throw new AuthException("User Not Found");
        return user;
    }


    public User findUserById(Long userId) throws AuthException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) throw new AuthException("User Not Found");
        return optionalUser.get();
    }


    public User updateUsersProjectSize(User user, int number) throws AuthException {
        user.setProjectSize(user.getProjectSize()+number);
        if(number < 0) throw new AuthException("Given number cannot be negative");
        return userRepository.save(user);
    }
}
