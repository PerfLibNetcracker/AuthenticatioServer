package com.perflibnetcracker.authenticationservice.repository;


import com.perflibnetcracker.authenticationservice.model.BoughtBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoughtBooksRepository extends JpaRepository<BoughtBook, Long> {
}
