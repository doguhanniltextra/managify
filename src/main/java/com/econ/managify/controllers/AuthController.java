package com.econ.managify.controllers;

import com.econ.managify.configs.JwtProvider;
import com.econ.managify.interfaces.SubscriptionService;
import com.econ.managify.models.User;
import com.econ.managify.repositories.UserRepository;
import com.econ.managify.dtos.requests.LoginRequest;
import com.econ.managify.dtos.responses.AuthResponse;
import com.econ.managify.services.CustomUserDetailsService;
import com.econ.managify.exceptions.AuthException;

import com.econ.managify.services.SubscriptionServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;
    private final SubscriptionServiceImp subscriptionService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, CustomUserDetailsService customUserDetailsService, SubscriptionServiceImp subscriptionService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailsService = customUserDetailsService;
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/create-user")
    public ResponseEntity<User> createUserHandler(@RequestBody User user) throws AuthException {
        User isUserExist = userRepository.findByEmail(user.getEmail());

        if(isUserExist != null) {throw new AuthException("Email Already Exists");}

        User createdUser = new User();

        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createdUser.setEmail(user.getEmail());
        createdUser.setFullName(user.getFullName());

        User savedUser = userRepository.save(createdUser);

        subscriptionService.createSubscription(savedUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),
                user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse response = new AuthResponse();
        response.setMessage("User is created");
        response.setJwt(jwt);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();


        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse response = new AuthResponse();
        response.setMessage("Signup success");
        response.setJwt(jwt);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        if (userDetails == null) {throw new BadCredentialsException("Invalid username");}
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {throw new BadCredentialsException("Invalid password");}

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
