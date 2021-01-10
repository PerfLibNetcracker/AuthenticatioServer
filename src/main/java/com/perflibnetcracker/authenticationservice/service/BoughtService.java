package com.perflibnetcracker.authenticationservice.service;

public interface BoughtService {
    void addBookForBoughtBooks(String username, Long bookId);

    void buyBookBySubscription(String username, Long bookId);
}
