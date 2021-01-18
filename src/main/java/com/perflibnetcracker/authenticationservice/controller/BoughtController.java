package com.perflibnetcracker.authenticationservice.controller;

import com.perflibnetcracker.authenticationservice.DTO.UserBoughtBooksDTO;
import com.perflibnetcracker.authenticationservice.repository.UserRepository;
import com.perflibnetcracker.authenticationservice.service.BoughtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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


    @GetMapping("${spring.urlmap}/check-bought/{bookId}")
    public UserBoughtBooksDTO userBoughtBooks(@AuthenticationPrincipal UserDetails currentUser,
                                              @PathVariable Long bookId) {
        return boughtService.getUserBoughtBooksDTO(currentUser.getUsername(), bookId);
    }

    @PostMapping("${spring.urlmap}/add-book-for-bought-books/{id_book}")
    public ResponseEntity<String> addBookForBoughtBooks(@AuthenticationPrincipal UserDetails currentUser,
                                                        @PathVariable(value = "id_book") Long idBook) {
        boughtService.addBookForBoughtBooks(currentUser.getUsername(), idBook);
        return new ResponseEntity<>("Book added successes!", HttpStatus.OK);
    }

    @PostMapping("${spring.urlmap}/add-book-for-bought-books-by-subscription/{id_book}")
    public ResponseEntity<String> addBookForBoughtBooksBySubscription(@AuthenticationPrincipal UserDetails currentUser,
                                                                      @PathVariable(value = "id_book") Long idBook) {
        boughtService.buyBookBySubscription(currentUser.getUsername(), idBook);
        return new ResponseEntity<>("Book added successes", HttpStatus.OK);
    }
}
