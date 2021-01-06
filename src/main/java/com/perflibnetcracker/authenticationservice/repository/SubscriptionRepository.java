package com.perflibnetcracker.authenticationservice.repository;

import com.perflibnetcracker.authenticationservice.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    Subscription findOneByEndTime(LocalDateTime time);
}
