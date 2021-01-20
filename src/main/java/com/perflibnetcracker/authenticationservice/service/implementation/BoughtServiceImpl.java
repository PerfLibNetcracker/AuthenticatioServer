package com.perflibnetcracker.authenticationservice.service.implementation;

import com.perflibnetcracker.authenticationservice.DTO.UserBoughtBooksDTO;
import com.perflibnetcracker.authenticationservice.exceptions.SubscriptionNotFoundException;
import com.perflibnetcracker.authenticationservice.exceptions.UserAlreadyBoughtBookException;
import com.perflibnetcracker.authenticationservice.model.Book;
import com.perflibnetcracker.authenticationservice.model.BoughtBook;
import com.perflibnetcracker.authenticationservice.model.Subscription;
import com.perflibnetcracker.authenticationservice.model.User;
import com.perflibnetcracker.authenticationservice.repository.BookRepository;
import com.perflibnetcracker.authenticationservice.repository.SubscriptionRepository;
import com.perflibnetcracker.authenticationservice.repository.UserRepository;
import com.perflibnetcracker.authenticationservice.service.BoughtService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class BoughtServiceImpl implements BoughtService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    public BoughtServiceImpl(BookRepository bookRepository,
                             UserRepository userRepository,
                             SubscriptionRepository subscriptionRepository) {
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
        BoughtBook boughtBook = new BoughtBook();
        boughtBook.setBookId(book.getId());
        boughtBook.setName(book.getName());
        boughtBook.setPrice(book.getPrice());
        User user = userRepository.findByUsername(username);
        if (isNull(user)) {
            throw new UsernameNotFoundException("Пользователь с именем " + username
                    + " не был найден при добавлении покупки книги c id:" + bookId);
        }
        user.getBoughtBooks().add(boughtBook);
        userRepository.save(user);
    }

    @Override
    public void buyBookBySubscription(String username, Long bookId)
            throws UserAlreadyBoughtBookException, SubscriptionNotFoundException {
        if (userRepository.findByUsername(username).getBoughtBooks()
                .stream()
                .anyMatch(boughtBook -> boughtBook.getBookId().equals(bookId))) {
            throw new UserAlreadyBoughtBookException("Пользователь под именем " + username
                    + " пытался купить книгу под id: "
                    + bookId + ", однако она у него уже куплена");
        }
        Subscription subscription =
                subscriptionRepository.getUserNonExpiredSubscriptionByUsername(username, LocalDateTime.now());
        if (nonNull(subscription)) {
            subscription.setFreeBookCount(subscription.getFreeBookCount() - 1);
            subscriptionRepository.save(subscription);
            addBookForBoughtBooks(username, bookId);
        } else {
            throw new SubscriptionNotFoundException("Пользователь под именем " + username
                    + " пытался купить книгу с id: " + bookId
                    + ", по подписке, но у пользователя не оказалось активной подписки.");
        }
    }
}
