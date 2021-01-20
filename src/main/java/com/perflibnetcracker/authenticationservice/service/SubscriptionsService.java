package com.perflibnetcracker.authenticationservice.service;

import com.perflibnetcracker.authenticationservice.DTO.SubscriptionInfoDTO;
import com.perflibnetcracker.authenticationservice.DTO.UserInfoDTO;
import com.perflibnetcracker.authenticationservice.exceptions.AlreadyHasSubscriptionException;

public interface SubscriptionsService {
    /**
     * Возвращает DTO с информацией о пользователе и информацией о том,
     * есть ли подписка у пользователя
     *
     * @param username пользователя
     * @return DTO пользователя и наличия подписки
     */
    UserInfoDTO getUserInfoDTOByUsername(String username);

    /**
     * Добавить подписку пользователю
     *
     * @param username пользователя
     * @param days     кол-во дней подписки
     */
    void addSubscriptionToUser(String username, Integer days) throws AlreadyHasSubscriptionException;

    /**
     * Вернуть DTO содержащую информацию об подписке (окончание, кол-во бесплатных книг)
     *
     * @param username пользователя
     * @return DTO с информацией о подписке
     */
    SubscriptionInfoDTO getSubscriptionInfoDTOByUsername(String username);
}
