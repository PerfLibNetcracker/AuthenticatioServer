package com.perflibnetcracker.authenticationservice.service.implementation;

import com.perflibnetcracker.authenticationservice.model.Book;
import com.perflibnetcracker.authenticationservice.model.User;
import com.perflibnetcracker.authenticationservice.repository.BookRepository;
import com.perflibnetcracker.authenticationservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private UserServiceImpl userService;
    private BookRepository bookRepository;

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void newRated(Double newRat, Long id) {
        bookRepository.setBookForRatting(newRat, id);
    }

    @Override
    public void setNewRatForBookByUser(Long id, String username) {
        Book book = bookRepository.getOne(id);
        User user = userService.findByUsername(username);
        Set users = book.getUsers();
        users.add(user);
        book.setUsers(users);
        bookRepository.save(book);
    }
}
