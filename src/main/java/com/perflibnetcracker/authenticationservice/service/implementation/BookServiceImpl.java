package com.perflibnetcracker.authenticationservice.service.implementation;

import com.perflibnetcracker.authenticationservice.model.Book;
import com.perflibnetcracker.authenticationservice.model.User;
import com.perflibnetcracker.authenticationservice.repository.BookRepository;
import com.perflibnetcracker.authenticationservice.repository.UserRepository;
import com.perflibnetcracker.authenticationservice.service.BookService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static java.util.Objects.isNull;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public BookServiceImpl(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void setNewRatingForBookById(Double newRating, Long bookId) {
        bookRepository.setNewRatingForBookById(newRating, bookId);
    }

    @Override
    public void addToBookRatedUsersUserByBookId(String username, Long bookId) {
        Book book = bookRepository.getOne(bookId);
        User user = userRepository.findByUsername(username);
        if (isNull(user)) {
            throw new UsernameNotFoundException("Пользователь под именем " + username
                    + " не был найден при проставлении рейтинга книге под id: " + bookId);
        }
        book.getRatedUsers().add(user);
        bookRepository.save(book);
    }
}
