package com.perflibnetcracker.authenticationservice.service;

public interface BookService {

    void newRatingForBookById(Double newRat, Long id);

    void setUserRatedBook(Long id, String username);
}
