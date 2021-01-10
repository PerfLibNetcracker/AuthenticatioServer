package com.perflibnetcracker.authenticationservice.service.implementation;

import com.perflibnetcracker.authenticationservice.model.Book;
import com.perflibnetcracker.authenticationservice.model.BoughtBooks;
import com.perflibnetcracker.authenticationservice.model.User;
import com.perflibnetcracker.authenticationservice.repository.BookRepository;
import com.perflibnetcracker.authenticationservice.repository.BoughtBooksRepository;
import com.perflibnetcracker.authenticationservice.repository.SubscriptionRepository;
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
    private final SubscriptionRepository subscriptionRepository;

    public BoughtServiceImpl(UserService userService, BookRepository bookRepository, BoughtBooksRepository boughtBooksRepository,
                             UserRepository userRepository, SubscriptionRepository subscriptionRepository) {
        this.userService = userService;
        this.bookRepository = bookRepository;
        this.boughtBooksRepository = boughtBooksRepository;
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public void addBookForBoughtBooks(String username, Long bookId) {
        Book book = bookRepository.getOne(bookId);
        BoughtBooks boughtBooks = new BoughtBooks();
        boughtBooks.setBookId(bookId);
        boughtBooks.setName(book.getName());
        boughtBooks.setPrice(book.getPrice());
        boughtBooksRepository.save(boughtBooks);
        User user = userService.findByUsername(username);
        Set setForBoughtBook = user.getBoughtBooks();
        BoughtBooks newBoughtBooksForUser = boughtBooksRepository.getOne(bookId);
        setForBoughtBook.add(newBoughtBooksForUser);
        user.setBoughtBooks(setForBoughtBook);
        userRepository.save(user);
    }

    @Override
    public void buyBookBySubscription(String username, Long bookId) {
        subscriptionRepository.setNewValuesForFreeBook(username);
        addBookForBoughtBooks(username, bookId);
    }
}
