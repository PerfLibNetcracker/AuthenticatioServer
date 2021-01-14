package com.perflibnetcracker.authenticationservice.service.implementation;

import com.perflibnetcracker.authenticationservice.DTO.UserInfoDTO;
import com.perflibnetcracker.authenticationservice.model.Subscription;
import com.perflibnetcracker.authenticationservice.model.User;
import com.perflibnetcracker.authenticationservice.repository.SubscriptionRepository;
import com.perflibnetcracker.authenticationservice.repository.UserRepository;
import com.perflibnetcracker.authenticationservice.service.SubscriptionsService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class SubscriptionsServiceImpl implements SubscriptionsService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionsServiceImpl(UserRepository userRepository, SubscriptionRepository subscriptionRepository) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public UserInfoDTO hasSub(String username, LocalDateTime localDateTime) {
        return userRepository.findUserWithSubscriptionAndWithFreeBook(username, localDateTime);
    }

    @Override
    public void addSub(String username, Integer days) {
        LocalDateTime nowTime = LocalDateTime.now().plusDays(days);
        Subscription subscriptionForDB = new Subscription();
        subscriptionForDB.setEndTime(Timestamp.valueOf(nowTime));
        if (days == 7) {
            subscriptionForDB.setFreeBook(3);
        } else if (days == 30) {
            subscriptionForDB.setFreeBook(5);
        }
        subscriptionRepository.save(subscriptionForDB);
        Subscription newSub = subscriptionRepository.findOneByEndTime(nowTime);
        Set<Subscription> subscriptionsSet = new HashSet<>();
        subscriptionsSet.add(newSub);
        User user = userRepository.findByUsername(username);
        user.setSubscriptions(subscriptionsSet);
        userRepository.save(user);
    }
}
