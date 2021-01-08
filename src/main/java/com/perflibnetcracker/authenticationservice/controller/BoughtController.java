package com.perflibnetcracker.authenticationservice.controller;

import com.perflibnetcracker.authenticationservice.DTO.UserDTO;
import com.perflibnetcracker.authenticationservice.DTO.UserForBoughtDTO;
import com.perflibnetcracker.authenticationservice.model.Book;
import com.perflibnetcracker.authenticationservice.repository.UserRepository;
import com.perflibnetcracker.authenticationservice.service.BoughtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "${spring.frontend.url}")
public class BoughtController {

   private UserRepository userRepository;

   @Autowired
   public void setUserRepository(UserRepository userRepository) {
       this.userRepository = userRepository;
   }

   private BoughtService boughtService;

   @Autowired
   public void setBoughtService(BoughtService boughtService) {
       this.boughtService = boughtService;
   }

    @GetMapping("${spring.urlmap}/check-bought/{id_book}")
    public UserForBoughtDTO userBoughtBooks(@AuthenticationPrincipal UserDetails currentUser, @PathVariable Long id_book) {
        UserForBoughtDTO userForBoughDTO = userRepository.findUserBought(currentUser.getUsername(), id_book);
        return userForBoughDTO;
    }

    @GetMapping("${spring.urlmap}/add-book-for-bought-books/{id}")
    public ResponseEntity<Book> addBookForBoughtBooks(@AuthenticationPrincipal UserDetails currentUser, @PathVariable(value = "id") Long id) {
        boughtService.addBookForBoughtBooks(currentUser.getUsername(), id);
        return new ResponseEntity("Book added success!", HttpStatus.OK);
    }
}
