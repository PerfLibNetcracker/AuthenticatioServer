package com.perflibnetcracker.authenticationservice.repository;

import com.perflibnetcracker.authenticationservice.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Repository
@Transactional
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("select user.subscriptions from Subscription subcription " +
            "join User user on user.username = :username")
    Collection<Subscription> getSubscriptionsByUsername(@Param("username") String username);
}
