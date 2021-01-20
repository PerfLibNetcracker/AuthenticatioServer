package com.perflibnetcracker.authenticationservice.service;

import com.perflibnetcracker.authenticationservice.DTO.UserBoughtBooksDTO;
import com.perflibnetcracker.authenticationservice.exceptions.BookNotFoundException;
import com.perflibnetcracker.authenticationservice.exceptions.SubscriptionNotFoundException;
import com.perflibnetcracker.authenticationservice.exceptions.UserAlreadyBoughtBookException;

public interface BoughtService {
    /**
     * Возвращает DTO с содержанием купленных книг (если они есть)
     *
     * @param username пользователя
     * @param bookId   id книги из БД
     * @return DTO с купленными книгами, наличием книг
     */
    UserBoughtBooksDTO getUserBoughtBooksDTO(String username, Long bookId);

    void addBookForBoughtBooks(String username, Long bookId) throws BookNotFoundException;

    void buyBookBySubscription(String username, Long bookId)
            throws UserAlreadyBoughtBookException, SubscriptionNotFoundException, BookNotFoundException;
}
