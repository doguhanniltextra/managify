package com.econ.managify.controllers;


import com.econ.managify.dtos.responses.UserGetUserProfileResponseDto;
import com.econ.managify.exceptions.AuthException;
import com.econ.managify.interfaces.UserService;
import com.econ.managify.models.User;
import com.econ.managify.services.UserServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    public UserController(UserServiceImp userService) {
        this.userService = userService;
    }

    private final UserServiceImp userService;


    @GetMapping("/profile")
    public ResponseEntity<UserGetUserProfileResponseDto> getUserProfile(@RequestHeader("Authorization") String jwt) throws AuthException {
        User users = userService.findUserProfileByJwt(jwt);
        UserGetUserProfileResponseDto userGetUserProfileResponseDto = new UserGetUserProfileResponseDto();

        userGetUserProfileResponseDto.setEmail(users.getEmail());
        userGetUserProfileResponseDto.setFullName(users.getFullName());
        return new ResponseEntity<>(userGetUserProfileResponseDto, HttpStatus.OK);
    }
}
