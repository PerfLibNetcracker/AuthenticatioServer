package com.perflibnetcracker.authenticationservice.repository;

import com.perflibnetcracker.authenticationservice.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
@Transactional
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    Subscription findOneByEndTime(LocalDateTime time);

    @Modifying
    @Query("UPDATE Subscription sub1 SET sub1.freeBook=sub1.freeBook - 1 WHERE sub1.subId = (SELECT sub.subId from User u left join u.subscriptions sub where u.username = :username group by sub)")
    void setNewValuesForFreeBook(@Param("username") String username);

    @Query("SELECT sub1.endTime FROM Subscription sub1 WHERE sub1.subId = (SELECT sub.subId from User u left join u.subscriptions sub where u.username = :username group by sub)")
    LocalDateTime getEndTimeByUsername(@Param("username") String username);

    @Query("SELECT sub1.freeBook FROM Subscription sub1 WHERE sub1.subId = (SELECT sub.subId from User u left join u.subscriptions sub where u.username = :username group by sub)")
    Integer getCountFreeBooksByUsername(@Param("username") String username);
}
