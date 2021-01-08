package com.perflibnetcracker.authenticationservice.service;

public interface BookService {

    void newRated(Double newRat, Long id);

    void setNewRattingForBookByUser(Long id, String username);
}
