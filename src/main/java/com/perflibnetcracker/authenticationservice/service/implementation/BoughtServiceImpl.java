package com.perflibnetcracker.authenticationservice.service.implementation;

import com.perflibnetcracker.authenticationservice.DTO.UserBoughtBooksDTO;
import com.perflibnetcracker.authenticationservice.model.Book;
import com.perflibnetcracker.authenticationservice.model.BoughtBooks;
import com.perflibnetcracker.authenticationservice.model.Subscription;
import com.perflibnetcracker.authenticationservice.model.User;
import com.perflibnetcracker.authenticationservice.repository.BookRepository;
import com.perflibnetcracker.authenticationservice.repository.SubscriptionRepository;
import com.perflibnetcracker.authenticationservice.repository.UserRepository;
import com.perflibnetcracker.authenticationservice.service.BoughtService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BoughtServiceImpl implements BoughtService {

    private final UserService userService;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    public BoughtServiceImpl(UserService userService, BookRepository bookRepository,
                             UserRepository userRepository, SubscriptionRepository subscriptionRepository) {
        this.userService = userService;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public UserBoughtBooksDTO getUserBoughtBooksDTO(String username, Long bookId) {
        boolean hasBought = userRepository.hasUserBoughtBook(username, bookId);
        User user = userRepository.findByUsername(username);
        return new UserBoughtBooksDTO(hasBought, user);
    }

    @Override
    public void addBookForBoughtBooks(String username, Long bookId) {
        Book book = bookRepository.getOne(bookId);
        BoughtBooks boughtBooks = new BoughtBooks();
        boughtBooks.setBookId(bookId);
        boughtBooks.setName(book.getName());
        boughtBooks.setPrice(book.getPrice());
        User user = userService.findByUsername(username);
        user.getBoughtBooks().add(boughtBooks);
        userRepository.save(user);
    }

    @Override
    public void buyBookBySubscription(String username, Long bookId) {
        Collection<Subscription> subscriptions = subscriptionRepository.getSubscriptionsByUsername(username);
        // TODO(Kuptsov) MINOR: Подумать что делать тут если будут несколько подписок
        if (subscriptions.size() > 0) {
            Subscription subscription = subscriptions
                    .stream()
                    .findFirst()
                    .get();
            if (userRepository.findByUsername(username).getBoughtBooks()
                    .stream()
                    .anyMatch(boughtBook -> boughtBook.getBookId().equals(bookId))) {
                return;
            }
            subscription.setFreeBook(subscription.getFreeBook() - 1);
            subscriptionRepository.save(subscription);
        }
        addBookForBoughtBooks(username, bookId);
    }
}
