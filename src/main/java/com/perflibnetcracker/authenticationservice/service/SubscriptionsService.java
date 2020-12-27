package com.perflibnetcracker.authenticationservice.service;

import com.perflibnetcracker.authenticationservice.DTO.UserDTO;

import java.time.LocalDateTime;

public interface SubscriptionsService {
    UserDTO hasSub(String username, LocalDateTime localDateTime);
    void addSub(String username, Integer days);
}
