package com.perflibnetcracker.authenticationservice.repository;

import com.perflibnetcracker.authenticationservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from User u " +
            "inner join Book b on b.id = :bookId and u member of b.ratedUsers " +
            "where u.username = :username")
    Book getBookRatedByUser(@Param("username") String username, @Param("bookId") Long bookId);

    @Modifying
    @Query("UPDATE Book b SET b.rating = (b.rating + :newRat) / 2 WHERE b.id = :id")
    void setBookForRatting(@Param("newRat") Double newRat, @Param("id") Long id);

}
