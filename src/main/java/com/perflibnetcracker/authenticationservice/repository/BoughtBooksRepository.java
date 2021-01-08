package com.perflibnetcracker.authenticationservice.repository;


import com.perflibnetcracker.authenticationservice.model.BoughtBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoughtBooksRepository extends JpaRepository<BoughtBooks, Long> {
}
