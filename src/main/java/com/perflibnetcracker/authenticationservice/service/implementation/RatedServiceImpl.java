package com.perflibnetcracker.authenticationservice.service.implementation;

import com.perflibnetcracker.authenticationservice.DTO.BookDTO;
import com.perflibnetcracker.authenticationservice.repository.BookRepository;
import com.perflibnetcracker.authenticationservice.service.RatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatedServiceImpl implements RatedService {

    BookRepository bookRepository;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookDTO ratedByMe(String username, Long id) {
        return bookRepository.findAll1(username, id);
    }
}
