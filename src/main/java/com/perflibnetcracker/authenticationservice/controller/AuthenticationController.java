package com.perflibnetcracker.authenticationservice.controller;


import com.perflibnetcracker.authenticationservice.DTO.AuthenticationDTO;
import com.perflibnetcracker.authenticationservice.model.User;
import com.perflibnetcracker.authenticationservice.service.implementation.AuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/api/service/authentication/authenticated/")
    public AuthenticationDTO success() {
        return new AuthenticationDTO("successes");
    }

    @PostMapping("/api/service/authentication/registration/")
    public User newUser(@RequestBody User user) throws Exception {

        User userObj = authenticationService.saveUser(user);

        return userObj;
    }





}
