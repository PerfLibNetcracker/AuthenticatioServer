package com.perflibnetcracker.authenticationservice.repository;

import com.perflibnetcracker.authenticationservice.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    Subscription findOneByEndTime(LocalDateTime time);
}
