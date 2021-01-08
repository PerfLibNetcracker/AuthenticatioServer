package com.perflibnetcracker.authenticationservice.service;

public interface BookService {

    void newRated(Double newRat, Long id);

    void setNewRatingForBookByUser(Long id, String username);
}
