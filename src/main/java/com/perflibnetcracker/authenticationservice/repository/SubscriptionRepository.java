package com.perflibnetcracker.authenticationservice.repository;

import com.perflibnetcracker.authenticationservice.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
@Transactional
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Query("select subscription from User user " +
            "join user.subscriptions subscription on subscription.endTime > :currentTime " +
            "where user.username = :username")
    Subscription getUserNonExpiredSubscriptionByUsername(@Param("username") String username,
                                                         @Param("currentTime") LocalDateTime currentTime);

    @Query("select (count(subscription) > 0) from User user  " +
            "join user.subscriptions subscription on subscription.endTime > :currentTime " +
            "where user.username = :username")
    Boolean userHasNonExpiredSubscriptionByUsername(@Param("username") String username,
                                                    @Param("currentTime") LocalDateTime currentTime);
}
