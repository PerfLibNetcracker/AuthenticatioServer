package com.perflibnetcracker.authenticationservice.controller;

import com.perflibnetcracker.authenticationservice.DTO.UserDTO;
import com.perflibnetcracker.authenticationservice.model.Book;
import com.perflibnetcracker.authenticationservice.model.User;
import com.perflibnetcracker.authenticationservice.service.SubscriptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@CrossOrigin
public class SubscriptionController {

    private SubscriptionsService subscriptionsService;
    @Autowired
    public void setSubscriptionsService(SubscriptionsService subscriptionsService) {
        this.subscriptionsService = subscriptionsService;
    }

    @GetMapping("/api/service/authentication/authenticated/check-subscription")
    public UserDTO userHasSub(@AuthenticationPrincipal UserDetails currentUser) {
        System.out.println(currentUser.getUsername());
        UserDTO userDTO = subscriptionsService.hasSub(currentUser.getUsername(), LocalDateTime.now());
        return userDTO;
    }

    @PostMapping("/api/service/authentication/authenticated/add-subscription/{days}")
    public ResponseEntity<User> addSubForUser(@AuthenticationPrincipal UserDetails currentUser, @PathVariable(value = "days") Integer days) {
        subscriptionsService.addSub(currentUser.getUsername(), days);
        System.out.println(currentUser.getUsername() + "нажал");
        return new ResponseEntity("Added subscription for user: " + currentUser.getUsername(), HttpStatus.OK);
    }
}
