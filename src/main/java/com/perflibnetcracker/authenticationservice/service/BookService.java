package com.perflibnetcracker.authenticationservice.service;

public interface BookService {
    void setNewRatingForBookById(Double newRating, Long bookId);

    void addToBookRatedUsersUserByBookId(String username, Long bookId);
}
