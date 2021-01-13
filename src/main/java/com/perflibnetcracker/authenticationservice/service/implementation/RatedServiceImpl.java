package com.perflibnetcracker.authenticationservice.service.implementation;

import com.perflibnetcracker.authenticationservice.DTO.UserBookDTO;
import com.perflibnetcracker.authenticationservice.repository.BookRepository;
import com.perflibnetcracker.authenticationservice.service.RatedService;
import org.springframework.stereotype.Service;

@Service
public class RatedServiceImpl implements RatedService {

    private final BookRepository bookRepository;

    public RatedServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public UserBookDTO isBookRatedByUser(String username, Long bookId) {
        return bookRepository.findUserWithSubscription(username, bookId);
    }
}
