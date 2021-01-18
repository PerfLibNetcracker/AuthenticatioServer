package com.perflibnetcracker.authenticationservice.service.implementation;

import com.perflibnetcracker.authenticationservice.DTO.UserBookDTO;
import com.perflibnetcracker.authenticationservice.model.Book;
import com.perflibnetcracker.authenticationservice.repository.BookRepository;
import com.perflibnetcracker.authenticationservice.service.RatedService;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

@Service
public class RatedServiceImpl implements RatedService {

    private final BookRepository bookRepository;

    public RatedServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public UserBookDTO isBookRatedByUser(String username, Long bookId) {
        Book ratedBook = bookRepository.getBookRatedByUser(username, bookId);
        UserBookDTO userBookDTO = new UserBookDTO();
        if (nonNull(ratedBook)) {
            userBookDTO.setId(ratedBook.getId());
            userBookDTO.setIsRated(true);
        }
        return userBookDTO;
    }
}
