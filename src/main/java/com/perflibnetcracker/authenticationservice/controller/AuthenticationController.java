package com.perflibnetcracker.authenticationservice.controller;


import com.perflibnetcracker.authenticationservice.bean.AuthenticationBean;
import com.perflibnetcracker.authenticationservice.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/api/service/authentication/authenticated/")
    public AuthenticationBean success() {
        return new AuthenticationBean("successes");
    }





}
