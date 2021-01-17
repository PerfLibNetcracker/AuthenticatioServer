package com.perflibnetcracker.authenticationservice.repository;

import com.perflibnetcracker.authenticationservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String userName);

    User findByUsernameAndPassword(String userName, String password);

    //TODO(Kutpsov) VERYMINOR: Возможно потребуется серьёзный рефактор query если будут изменения в коде, будьте внимательны

    /**
     * Проверяет покупал ли User книгу
     *
     * @param username
     * @param bookId
     * @return
     */
    @Query(value = "select count(bb) > 0 from auth_service.users u " +
            "join auth_service.bought_books bb on bb.book_id = :book_id " +
            "join auth_service.users_bought_books ubb on ubb.user_id = u.id and ubb.bought_books_id = bb.book_id " +
            "where u.username = :username", nativeQuery = true)
    Boolean hasUserBoughtBook(@Param("username") String username, @Param("book_id") Long bookId);
}
