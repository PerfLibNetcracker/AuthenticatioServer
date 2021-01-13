package com.perflibnetcracker.authenticationservice.service;

import com.perflibnetcracker.authenticationservice.DTO.UserInfoDTO;

import java.time.LocalDateTime;

public interface SubscriptionsService {
    UserInfoDTO hasSub(String username, LocalDateTime localDateTime);

    void addSub(String username, Integer days);
}
