package com.perflibnetcracker.authenticationservice.service.implementation;

import com.perflibnetcracker.authenticationservice.DTO.UserDTO;
import com.perflibnetcracker.authenticationservice.model.Subscription;
import com.perflibnetcracker.authenticationservice.model.User;
import com.perflibnetcracker.authenticationservice.repository.SubscriptionRepository;
import com.perflibnetcracker.authenticationservice.repository.UserRepository;
import com.perflibnetcracker.authenticationservice.service.SubscriptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class SubscriptionsServiceImpl implements SubscriptionsService {

    private UserRepository userRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private SubscriptionRepository subscriptionRepository;
    @Autowired
    public void setSubscriptionRepository(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }


    @Override
    public UserDTO hasSub(String username, LocalDateTime localDateTime) {
        return userRepository.findUserWithSub(username, localDateTime);
    }

    @Override
    public void addSub(String username, Integer days) {
        LocalDateTime nowTime = LocalDateTime.now().plusDays(days);
        Subscription subscriptionForDB = new Subscription();
        subscriptionForDB.setEndTime(nowTime);
        subscriptionRepository.save(subscriptionForDB);
        Subscription newSub = subscriptionRepository.findOneByEndTime(nowTime);
        Set<Subscription> subscriptionsSet = new HashSet<>();
        subscriptionsSet.add(newSub);
        User user = userRepository.findByUsername(username);
        user.setSubscriptions(subscriptionsSet);
        userRepository.save(user);
    }
}
