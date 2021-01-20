package com.perflibnetcracker.authenticationservice.repository;

import com.perflibnetcracker.authenticationservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String userName);

    Boolean existsByUsername(String username);

    /**
     * Проверяет покупал ли пользователь книгу
     *
     * @param username пользователя
     * @param bookId   ID книги
     * @return true - пользователь купил книгу, false - не купил
     */
    @Query(value = "select (count(userBoughtBook) > 0) from User user " +
            "join user.boughtBooks userBoughtBook on userBoughtBook.bookId = :bookId " +
            "where user.username = :username")
    Boolean hasUserBoughtBook(@Param("username") String username, @Param("bookId") Long bookId);
}
