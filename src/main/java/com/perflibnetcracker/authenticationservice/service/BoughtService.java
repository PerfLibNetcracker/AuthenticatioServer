package com.perflibnetcracker.authenticationservice.service;

import com.perflibnetcracker.authenticationservice.DTO.UserBoughtBooksDTO;

public interface BoughtService {
    /**
     * Возвращает DTO с содержанием купленных книг (если они есть)
     *
     * @param username пользователя
     * @param bookId   id книги из БД
     * @return DTO с купленными книгами, наличием книг
     */
    UserBoughtBooksDTO getUserBoughtBooksDTO(String username, Long bookId);

    void addBookForBoughtBooks(String username, Long bookId);

    void buyBookBySubscription(String username, Long bookId);
}
