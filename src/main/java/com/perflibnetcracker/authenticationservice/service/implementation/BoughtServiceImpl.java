package com.perflibnetcracker.authenticationservice.service.implementation;

import com.perflibnetcracker.authenticationservice.model.Book;
import com.perflibnetcracker.authenticationservice.model.BoughtBooks;
import com.perflibnetcracker.authenticationservice.model.User;
import com.perflibnetcracker.authenticationservice.repository.BookRepository;
import com.perflibnetcracker.authenticationservice.repository.BoughtBooksRepository;
import com.perflibnetcracker.authenticationservice.repository.UserRepository;
import com.perflibnetcracker.authenticationservice.service.BoughtService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BoughtServiceImpl implements BoughtService {

    private final UserService userService;
    private final BookRepository bookRepository;
    private final BoughtBooksRepository boughtBooksRepository;
    private final UserRepository userRepository;

    public BoughtServiceImpl(UserService userService, BookRepository bookRepository, BoughtBooksRepository boughtBooksRepository, UserRepository userRepository) {
        this.userService = userService;
        this.bookRepository = bookRepository;
        this.boughtBooksRepository = boughtBooksRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addBookForBoughtBooks(String username, Long id) {
        Book book = bookRepository.getOne(id);
        BoughtBooks boughtBooks = new BoughtBooks();
        boughtBooks.setBookId(id);
        boughtBooks.setName(book.getName());
        boughtBooks.setPrice(book.getPrice());
        boughtBooksRepository.save(boughtBooks);
        User user = userService.findByUsername(username);
        Set setForBoughtBook = user.getBoughtBooks();
        BoughtBooks newBoughtBooksForUser = boughtBooksRepository.getOne(id);
        setForBoughtBook.add(newBoughtBooksForUser);
        user.setBoughtBooks(setForBoughtBook);
        userRepository.save(user);
    }
}
