package com.perflibnetcracker.authenticationservice.service.implementation;

import com.perflibnetcracker.authenticationservice.DTO.SubscriptionInfoDTO;
import com.perflibnetcracker.authenticationservice.DTO.UserInfoDTO;
import com.perflibnetcracker.authenticationservice.exceptions.UserAlreadyHasSubscriptionException;
import com.perflibnetcracker.authenticationservice.model.Subscription;
import com.perflibnetcracker.authenticationservice.model.User;
import com.perflibnetcracker.authenticationservice.repository.SubscriptionRepository;
import com.perflibnetcracker.authenticationservice.repository.UserRepository;
import com.perflibnetcracker.authenticationservice.service.SubscriptionsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class SubscriptionsServiceImpl implements SubscriptionsService {
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionsServiceImpl(UserRepository userRepository, SubscriptionRepository subscriptionRepository) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public UserInfoDTO getUserInfoDTOByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (isNull(user)) {
            throw new UsernameNotFoundException("Пользователь под именем " + username + " не был найден");
        }
        Subscription subscription =
                subscriptionRepository.getUserNonExpiredSubscriptionByUsername(username, LocalDateTime.now());
        UserInfoDTO userInfoDTO = new UserInfoDTO(user);
        if (nonNull(subscription)) {
            if (subscription.getEndTime().isAfter(LocalDateTime.now())) {
                userInfoDTO.setHasSub(true);
            }
            if (subscription.getFreeBookCount() > 0) {
                userInfoDTO.setHasFreeBook(true);
            }
        }
        return userInfoDTO;
    }

    @Override
    public void addSubscriptionToUser(String username, Integer days) throws UserAlreadyHasSubscriptionException {
        User user = userRepository.findByUsername(username);
        if (isNull(user)) {
            throw new UsernameNotFoundException("Пользователь с именем " + username + " не был найден по имени");
        }
        LocalDateTime now = LocalDateTime.now();
        boolean hasActiveSubscription =
                subscriptionRepository.userHasNonExpiredSubscriptionByUsername(username, now);
        if (hasActiveSubscription) {
            throw new UserAlreadyHasSubscriptionException("Пользователь под именем " + username + " уже имеет подписку");
        }
        Subscription subscriptionForDB = new Subscription();
        subscriptionForDB.setEndTime(now.plusDays(days));
        if (days == 7) {
            //TODO(Kuptsov) MINOR: В идеале нужно вынести кол-во беспл. книг в конфигурационные файлы
            subscriptionForDB.setFreeBookCount(3);
        } else if (days == 30) {
            subscriptionForDB.setFreeBookCount(5);
        }
        user.getSubscriptions().add(subscriptionForDB);
        userRepository.save(user);
    }

    @Override
    public SubscriptionInfoDTO getSubscriptionInfoDTOByUsername(String username) {
        Subscription subscription =
                subscriptionRepository.getUserNonExpiredSubscriptionByUsername(username, LocalDateTime.now());
        SubscriptionInfoDTO subscriptionInfoDTO = new SubscriptionInfoDTO();
        if (nonNull(subscription)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            subscriptionInfoDTO.setEndTime(subscription.getEndTime().format(formatter));
            subscriptionInfoDTO.setFreeBookCount(subscription.getFreeBookCount());
        }
        return subscriptionInfoDTO;
    }
}
