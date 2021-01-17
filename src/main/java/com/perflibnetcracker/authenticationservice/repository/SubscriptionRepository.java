package com.perflibnetcracker.authenticationservice.repository;

import com.perflibnetcracker.authenticationservice.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
@Transactional
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("select user.subscriptions from Subscription subcription " +
            "join User user on user.username = :username")
    Collection<Subscription> getSubscriptionsByUsername(@Param("username") String username);

    //TODO(Kuptsov) MAJOR: Нужно переделать запрос скорее всего на NativeQuery по возможности
    @Query("SELECT sub1.endTime " +
            "FROM Subscription sub1 " +
            "WHERE sub1.subId = " +
            "   (SELECT sub.subId " +
            "   from User u " +
            "   left join u.subscriptions sub " +
            "   where u.username = :username " +
            "   group by sub)")
    LocalDateTime getEndTimeByUsername(@Param("username") String username);

    //TODO(Kuptsov) MAJOR: Нужно переделать запрос скорее всего на NativeQuery по возможности
    @Query("SELECT sub1.freeBook " +
            "FROM Subscription sub1 " +
            "WHERE sub1.subId = " +
            "   (SELECT sub.subId " +
            "   from User u " +
            "   left join u.subscriptions sub " +
            "   where u.username = :username " +
            "   group by sub)")
    Integer getFreeBooksCountByUsername(@Param("username") String username);
}
