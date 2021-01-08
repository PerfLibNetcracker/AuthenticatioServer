package com.perflibnetcracker.authenticationservice.service.implementation;

import com.perflibnetcracker.authenticationservice.model.Book;
import com.perflibnetcracker.authenticationservice.model.BoughtBooks;
import com.perflibnetcracker.authenticationservice.model.User;
import com.perflibnetcracker.authenticationservice.repository.BookRepository;
import com.perflibnetcracker.authenticationservice.repository.BoughtBooksRepository;
import com.perflibnetcracker.authenticationservice.repository.UserRepository;
import com.perflibnetcracker.authenticationservice.service.BoughtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class BoughtServiceImpl implements BoughtService {

    private UserServiceImpl userService;
    private BookRepository bookRepository;
    private BoughtBooksRepository boughtBooksRepository;
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setBoughtService(BoughtBooksRepository boughtBooksRepository) {
        this.boughtBooksRepository = boughtBooksRepository;
    }

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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
