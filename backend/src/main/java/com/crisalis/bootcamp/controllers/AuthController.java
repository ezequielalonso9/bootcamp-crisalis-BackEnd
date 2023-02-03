package com.crisalis.bootcamp.controllers;


import com.crisalis.bootcamp.Services.TokenService;
import com.crisalis.bootcamp.Services.UserService;
import com.crisalis.bootcamp.model.dto.UserDto;
import com.crisalis.bootcamp.model.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.security.RolesAllowed;

@Controller
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private final TokenService tokenService;
    @Autowired
    private UserService userService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/auth")
    public ResponseEntity<Object> token(Authentication authentication){
        System.out.println(authentication);
        LOG.debug("Token request for user: '{}'",authentication.getName());
        String token = tokenService.generateToken(authentication);
        LOG.debug("Token granted: {}", token);
        return new ResponseEntity<>(token, HttpStatus.ACCEPTED);
    }

    @RolesAllowed(value = "ROLE_ADMIN")
    @PostMapping("/auth/SingIn")
    public ResponseEntity<Object> SingIn(@RequestBody UserDto user){
        User userCreated = userService.createUser(user);
        return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
    }
}
