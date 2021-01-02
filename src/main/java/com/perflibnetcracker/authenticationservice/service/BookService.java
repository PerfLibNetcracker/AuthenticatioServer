package com.perflibnetcracker.authenticationservice.service;

import org.springframework.stereotype.Service;

@Service
public interface BookService {

     void newRated(Double newRat, Long id);

    void setNewRatFOrBookByUser(Long id, String username);
}
