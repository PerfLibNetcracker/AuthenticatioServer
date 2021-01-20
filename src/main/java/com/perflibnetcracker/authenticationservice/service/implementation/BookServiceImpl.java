package com.perflibnetcracker.authenticationservice.service.implementation;

import com.perflibnetcracker.authenticationservice.model.Book;
import com.perflibnetcracker.authenticationservice.model.User;
import com.perflibnetcracker.authenticationservice.repository.BookRepository;
import com.perflibnetcracker.authenticationservice.service.BookService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final UserService userService;
    private final BookRepository bookRepository;

    public BookServiceImpl(UserService userService, BookRepository bookRepository) {
        this.userService = userService;
        this.bookRepository = bookRepository;
    }

    @Override
    public void newRatingForBookById(Double newRat, Long id) {
        bookRepository.setNewRatingForBookById(newRat, id);
    }

    @Override
    public void setUserRatedBook(Long id, String username) {
        Book book = bookRepository.getOne(id);
        User user = userService.findByUsername(username);
        book.getRatedUsers().add(user);
        bookRepository.save(book);
    }
}
