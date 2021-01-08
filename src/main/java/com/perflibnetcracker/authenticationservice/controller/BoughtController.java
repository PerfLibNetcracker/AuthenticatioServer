package com.perflibnetcracker.authenticationservice.controller;

import com.perflibnetcracker.authenticationservice.DTO.UserBoughtBooksDTO;
import com.perflibnetcracker.authenticationservice.model.Book;
import com.perflibnetcracker.authenticationservice.repository.UserRepository;
import com.perflibnetcracker.authenticationservice.service.BoughtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "${spring.frontend.url}")
public class BoughtController {

    private final UserRepository userRepository;
    private final BoughtService boughtService;

    public BoughtController(UserRepository userRepository, BoughtService boughtService) {
        this.userRepository = userRepository;
        this.boughtService = boughtService;
    }


    @GetMapping("${spring.urlmap}/check-bought/{id_book}")
    public UserBoughtBooksDTO userBoughtBooks(@AuthenticationPrincipal UserDetails currentUser, @PathVariable Long id_book) {
        UserBoughtBooksDTO userForBoughDTO = userRepository.findUserBought(currentUser.getUsername(), id_book);
        return userForBoughDTO;
    }

    @GetMapping("${spring.urlmap}/add-book-for-bought-books/{id}")
    public ResponseEntity<Book> addBookForBoughtBooks(@AuthenticationPrincipal UserDetails currentUser, @PathVariable(value = "id") Long id) {
        boughtService.addBookForBoughtBooks(currentUser.getUsername(), id);
        return new ResponseEntity("Book added success!", HttpStatus.OK);
    }
}
