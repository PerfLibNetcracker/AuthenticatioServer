package com.perflibnetcracker.authenticationservice.controller;


import com.perflibnetcracker.authenticationservice.DTO.AuthenticationDTO;
import com.perflibnetcracker.authenticationservice.DTO.BookDTO;
import com.perflibnetcracker.authenticationservice.DTO.UserDTO;
import com.perflibnetcracker.authenticationservice.mappers.UserMapper;
import com.perflibnetcracker.authenticationservice.model.Book;
import com.perflibnetcracker.authenticationservice.model.User;
import com.perflibnetcracker.authenticationservice.service.AuthenticationService;
import com.perflibnetcracker.authenticationservice.service.BookService;
import com.perflibnetcracker.authenticationservice.service.RatedService;
import com.perflibnetcracker.authenticationservice.service.implementation.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "${spring.frontend.url}")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final BookService bookService;
    private final RatedService ratedService;

    public AuthenticationController(AuthenticationService authenticationService, UserService userService, UserMapper userMapper, BookService bookService, RatedService ratedService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.userMapper = userMapper;
        this.bookService = bookService;
        this.ratedService = ratedService;
    }

    @GetMapping("${spring.urlmap}")
    public AuthenticationDTO success() {
        return new AuthenticationDTO("successes");
    }

    @PostMapping("${spring.urlmap}/registration")
    public User newUser(@RequestBody User user) throws Exception {
        User userObj = authenticationService.saveUser(user);
        return userObj;
    }

    @GetMapping("${spring.urlmap}/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        return userMapper.userToDTO(user);
    }

    @GetMapping("${spring.urlmap}/rated/{id_book}")
    public BookDTO getRatedForUser(@AuthenticationPrincipal UserDetails currentUser, @PathVariable Long id_book) {
        return ratedService.ratedByUser(currentUser.getUsername(), id_book);
    }

    @RequestMapping(value = "${spring.urlmap}/userLogout")
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        SecurityContextHolder.clearContext();
        if (session != null) {
            session.invalidate();
        }
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                cookie.setMaxAge(0);
            }
        }
        System.out.println("logout" + SecurityContextHolder.getContext().getAuthentication());
        SecurityContextHolder.clearContext();
        System.out.println("logout context" + SecurityContextHolder.getContext());
        return new ResponseEntity("Logout Successful!", HttpStatus.OK);
    }

    @PutMapping("${spring.urlmap}/update-book/{id}")
    public ResponseEntity<Book> updateRatingForBook(@AuthenticationPrincipal UserDetails currentUser, @PathVariable(value = "id") Long id,
                                               @RequestBody Book bookDetails) {
        Double newRating = bookDetails.getRating();
        bookService.newRated(newRating, id);
        bookService.setNewRatingForBookByUser(id, currentUser.getUsername());
        return new ResponseEntity("Rating correct!", HttpStatus.OK);
    }
}
