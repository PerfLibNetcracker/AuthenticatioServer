package com.perflibnetcracker.authenticationservice.repository;

import com.perflibnetcracker.authenticationservice.DTO.UserDTO;
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

    @Query("select new com.perflibnetcracker.authenticationservice.DTO.UserDTO(u, sum(case when sub.endTime > :endTime then 1 else 0 end) > 0) from User u left join u.subscriptions sub " +
            "where u.username = :username " +
            "group by u ")
    UserDTO findUserWithSub(@Param("username") String username, @Param("endTime") LocalDateTime endTime);
}
