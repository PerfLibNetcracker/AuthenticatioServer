package com.perflibnetcracker.authenticationservice.controller;


import com.perflibnetcracker.authenticationservice.DTO.UserBookDTO;
import com.perflibnetcracker.authenticationservice.model.Book;
import com.perflibnetcracker.authenticationservice.service.BookService;
import com.perflibnetcracker.authenticationservice.service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "${spring.frontend.url}")
public class RatingController {
    private final BookService bookService;
    private final RatingService ratingService;

    public RatingController(BookService bookService, RatingService ratingService) {
        this.bookService = bookService;
        this.ratingService = ratingService;
    }

    @GetMapping("${spring.urlmap}/user-book-rated/{bookId}")
    public UserBookDTO getUserRatedBooks(@AuthenticationPrincipal UserDetails currentUser,
                                         @PathVariable Long bookId) {
        return ratingService.isBookRatedByUser(currentUser.getUsername(), bookId);
    }

    @PutMapping("${spring.urlmap}/update-book/{id}")
    public ResponseEntity<String> updateRatingForBook(@AuthenticationPrincipal UserDetails currentUser,
                                                      @PathVariable(value = "id") Long id,
                                                      @RequestBody Book bookDetails) throws Exception {
        Double newRating = bookDetails.getRating();
        if (newRating == null) {
            throw new Exception("Cannot be null!");
        }
        bookService.newRated(newRating, id);
        bookService.setNewRatingForBookByUser(id, currentUser.getUsername());
        return new ResponseEntity<>("Rating correct!", HttpStatus.OK);
    }
}
