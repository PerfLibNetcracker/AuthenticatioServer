package com.perflibnetcracker.authenticationservice.repository;

import com.perflibnetcracker.authenticationservice.DTO.UserDTO;
import com.perflibnetcracker.authenticationservice.DTO.UserBoughtBooksDTO;
import com.perflibnetcracker.authenticationservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getById(Long id);

    User findByUsername(String userName);

    User findByUsernameAndPassword(String userName, String password);
    // Проверяет есть ли у User подписка и возможность получить бесплатно книги по подиске
    @Query("select new com.perflibnetcracker.authenticationservice.DTO.UserDTO(u, (sum(case when sub.endTime > :endTime then 1 else 0 end) > 0), " +
            "(sum(case when (sub.freeBook > 0 and sub.endTime > :endTime) then 1 else 0 end) > 0)) " +
            "from User u left join u.subscriptions sub " +
            "where u.username = :username " +
            "group by u ")
    UserDTO findUserWithSubscriptionAndWithFreeBook(@Param("username") String username, @Param("endTime") LocalDateTime endTime);
    // Проверяет покупал ли User книгу
    @Query("select new com.perflibnetcracker.authenticationservice.DTO.UserBoughtBooksDTO((sum(case when us_b.bookId = :bookId then 1 else 0 end) > 0), us) " +
            "from User us left join us.boughtBooks us_b " +
            "where us.username = :username " +
            "group by us ")
    UserBoughtBooksDTO findUserBought(@Param("username") String username, @Param("bookId") Long bookId);
}
