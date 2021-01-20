package com.perflibnetcracker.authenticationservice.controller;

import com.perflibnetcracker.authenticationservice.DTO.SubscriptionInfoDTO;
import com.perflibnetcracker.authenticationservice.DTO.UserInfoDTO;
import com.perflibnetcracker.authenticationservice.exceptions.UserAlreadyHasSubscriptionException;
import com.perflibnetcracker.authenticationservice.model.User;
import com.perflibnetcracker.authenticationservice.service.SubscriptionsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "${spring.frontend.url}")
public class SubscriptionController {
    private final SubscriptionsService subscriptionsService;

    public SubscriptionController(SubscriptionsService subscriptionsService) {
        this.subscriptionsService = subscriptionsService;
    }

    @GetMapping("${spring.urlmap}/check-subscription")
    public UserInfoDTO userHasSub(@AuthenticationPrincipal UserDetails currentUser) {
        return subscriptionsService.getUserInfoDTOByUsername(currentUser.getUsername());
    }

    @GetMapping("${spring.urlmap}/check-subscription-info")
    public SubscriptionInfoDTO subscriptionInfo(@AuthenticationPrincipal UserDetails currentUser) {
        return subscriptionsService.getSubscriptionInfoDTOByUsername(currentUser.getUsername());
    }

    @PostMapping("${spring.urlmap}/add-subscription/{days}")
    public ResponseEntity<User> addSubForUser(@AuthenticationPrincipal UserDetails currentUser,
                                              @PathVariable(value = "days") Integer days)
            throws UserAlreadyHasSubscriptionException {
        subscriptionsService.addSubscriptionToUser(currentUser.getUsername(), days);
        return new ResponseEntity("Added subscription for user: " + currentUser.getUsername(), HttpStatus.OK);
    }
}
