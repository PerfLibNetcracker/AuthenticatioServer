package com.perflibnetcracker.authenticationservice.repository;

import com.perflibnetcracker.authenticationservice.DTO.BookDTO;
import com.perflibnetcracker.authenticationservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select new com.perflibnetcracker.authenticationservice.DTO.BookDTO(b, sum(case when us.username = :username then 1 else 0 end) > 0) " +
            "from Book b left join b.users us " +
            "where b.id = :id " +
            "group by b ")
    BookDTO findAll1(@Param("username") String username, @Param("id") Long id);

    @Modifying
    @Query("UPDATE Book b SET b.rating = (b.rating + :newRat) / 2 WHERE b.id = :id")
    void setBookForRatting(@Param("newRat") Double newRat, @Param("id") Long id);

}
