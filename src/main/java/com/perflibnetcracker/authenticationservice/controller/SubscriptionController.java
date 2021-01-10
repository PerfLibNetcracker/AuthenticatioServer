package com.perflibnetcracker.authenticationservice.controller;

import com.perflibnetcracker.authenticationservice.DTO.SubscriptionInfoDTO;
import com.perflibnetcracker.authenticationservice.DTO.UserDTO;
import com.perflibnetcracker.authenticationservice.model.User;
import com.perflibnetcracker.authenticationservice.repository.SubscriptionRepository;
import com.perflibnetcracker.authenticationservice.service.SubscriptionsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@CrossOrigin(origins = "${spring.frontend.url}")
public class SubscriptionController {

    private final SubscriptionsService subscriptionsService;
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionController(SubscriptionsService subscriptionsService, SubscriptionRepository subscriptionRepository) {
        this.subscriptionsService = subscriptionsService;
        this.subscriptionRepository = subscriptionRepository;
    }

    @GetMapping("${spring.urlmap}/check-subscription")
    public UserDTO userHasSub(@AuthenticationPrincipal UserDetails currentUser) {
        UserDTO userDTO = subscriptionsService.hasSub(currentUser.getUsername(), LocalDateTime.now());
        return userDTO;
    }

    @GetMapping("${spring.urlmap}/check-subscription-info")
    public SubscriptionInfoDTO subscriptionInfo(@AuthenticationPrincipal UserDetails currentUser) {
        Integer countFreeBooks = subscriptionRepository.getCountFreeBooksByUsername(currentUser.getUsername());
        LocalDateTime endTimeSubscription = subscriptionRepository.getEndTimeByUsername(currentUser.getUsername());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        SubscriptionInfoDTO subscriptionInfoDTO = new SubscriptionInfoDTO(endTimeSubscription.format(formatter), countFreeBooks);
        return subscriptionInfoDTO;
    }

    @PostMapping("${spring.urlmap}/add-subscription/{days}")
    public ResponseEntity<User> addSubForUser(@AuthenticationPrincipal UserDetails currentUser, @PathVariable(value = "days") Integer days) {
        subscriptionsService.addSub(currentUser.getUsername(), days);
        return new ResponseEntity("Added subscription for user: " + currentUser.getUsername(), HttpStatus.OK);
    }
}
