package com.perflibnetcracker.authenticationservice.service.implementation;

import com.perflibnetcracker.authenticationservice.DTO.SubscriptionInfoDTO;
import com.perflibnetcracker.authenticationservice.DTO.UserInfoDTO;
import com.perflibnetcracker.authenticationservice.exceptions.AlreadyHasSubscriptionException;
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
    public void addSubscriptionToUser(String username, Integer days) throws AlreadyHasSubscriptionException {
        User user = userRepository.findByUsername(username);
        if (isNull(user)) {
            throw new NullPointerException("USER не был найден по Username");
        }
        if (user.getSubscriptions().size() > 0) {
            // TODO(Kuptsov) MINOR: Подумать что делать тут если будут несколько подписок
            Subscription subscription = user.getSubscriptions()
                    .stream()
                    .findFirst()
                    .orElse(null);
            if (nonNull(subscription) && subscription.getEndTime().isAfter(LocalDateTime.now())) {
                throw new AlreadyHasSubscriptionException("User под именем " + username + " уже имеет подписку");
            }
        }
        Subscription subscriptionForDB = new Subscription();
        subscriptionForDB.setEndTime(LocalDateTime.now().plusDays(days));
        if (days == 7) {
            //TODO(Kuptsov) MINOR: В идеале нужно вынести кол-во беспл. книг в конфигурационные файлы
            subscriptionForDB.setFreeBookCount(3);
        } else if (days == 30) {
            subscriptionForDB.setFreeBookCount(5);
        }
        // TODO(Kuptsov) MINOR: Хорошо бы обьединить как-то подписки и купленные книги по подписке
        //  может закинуть купленные книги подписок в подписки?
        user.getBoughtBooks().clear();
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
            if (subscription.getEndTime().isAfter(LocalDateTime.now())) {
                subscriptionInfoDTO.setEndTime(subscription.getEndTime().format(formatter));
                subscriptionInfoDTO.setFreeBookCount(subscription.getFreeBookCount());
            }
        }
        return subscriptionInfoDTO;
    }
}
