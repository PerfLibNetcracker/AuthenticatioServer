package com.perflibnetcracker.authenticationservice.service;

import com.perflibnetcracker.authenticationservice.model.Book;
import com.perflibnetcracker.authenticationservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public interface BookService {

     void newRated(Double newRat, Long id);
     void setNewRatFOrBookByUser(Long id, String username);
}
