package com.perflibnetcracker.authenticationservice.service.implementation;

import com.perflibnetcracker.authenticationservice.DTO.SubscriptionInfoDTO;
import com.perflibnetcracker.authenticationservice.DTO.UserInfoDTO;
import com.perflibnetcracker.authenticationservice.model.Subscription;
import com.perflibnetcracker.authenticationservice.model.User;
import com.perflibnetcracker.authenticationservice.repository.SubscriptionRepository;
import com.perflibnetcracker.authenticationservice.repository.UserRepository;
import com.perflibnetcracker.authenticationservice.service.SubscriptionsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Set;

@Service
public class SubscriptionsServiceImpl implements SubscriptionsService {
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionsServiceImpl(UserRepository userRepository, SubscriptionRepository subscriptionRepository) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    // TODO(Kuptsov) MINOR: В случае если будут несколько подписок,
    //  нужно подумать о том как это будет работать
    @Override
    public UserInfoDTO getUserInfoDTOByUsername(String username) {
        User user = userRepository.findByUsername(username);
        Set<Subscription> subscriptions = user.getSubscriptions();
        UserInfoDTO userInfoDTO = new UserInfoDTO(user);
        if (subscriptions.size() > 0) {
            Subscription subscription = subscriptions
                    .stream()
                    .findFirst()
                    .get();
            if (subscription.getEndTime().isBefore(LocalDateTime.now())) {
                userInfoDTO.setHasSub(true);
            }
            if (subscription.getFreeBook() > 0) {
                userInfoDTO.setHasFreeBook(true);
            }
        }
        return userInfoDTO;
    }

    @Override
    public void addSubscriptionToUser(String username, Integer days) {
        Subscription subscriptionForDB = new Subscription();
        subscriptionForDB.setEndTime(LocalDateTime.now());
        if (days == 7) {
            //TODO(Kuptsov) MINOR: В идеале нужно вынести кол-во беспл. книг в конфигурационные файлы
            subscriptionForDB.setFreeBook(3);
        } else if (days == 30) {
            subscriptionForDB.setFreeBook(5);
        }
        User user = userRepository.findByUsername(username);
        user.getSubscriptions().add(subscriptionForDB);
        userRepository.save(user);
    }

    @Override
    public SubscriptionInfoDTO getSubscriptionInfoDTOByUsername(String username) {
        Collection<Subscription> subscriptions = subscriptionRepository.getSubscriptionsByUsername(username);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        SubscriptionInfoDTO subscriptionInfoDTO = new SubscriptionInfoDTO();
        if (subscriptions.size() > 0) {
            // TODO(Kuptsov) MINOR: Подумать что делать тут если будут несколько подписок
            Subscription subscription = subscriptions
                    .stream()
                    .findFirst()
                    .get();
            subscriptionInfoDTO.setEndTime(subscription.getEndTime().format(formatter));
            subscriptionInfoDTO.setFreeBook(subscription.getFreeBook());
        }
        return subscriptionInfoDTO;
    }
}
