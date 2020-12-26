package com.perflibnetcracker.authenticationservice.controller;


import com.perflibnetcracker.authenticationservice.DTO.AuthenticationDTO;
import com.perflibnetcracker.authenticationservice.DTO.BookDTO;
import com.perflibnetcracker.authenticationservice.DTO.UserDTO;
import com.perflibnetcracker.authenticationservice.exceptions.ResourceNotFoundException;
import com.perflibnetcracker.authenticationservice.mappers.UserMapper;
import com.perflibnetcracker.authenticationservice.model.Book;
import com.perflibnetcracker.authenticationservice.model.User;
import com.perflibnetcracker.authenticationservice.repository.BookRepository;
import com.perflibnetcracker.authenticationservice.service.BookService;
import com.perflibnetcracker.authenticationservice.service.RatedService;
import com.perflibnetcracker.authenticationservice.service.implementation.AuthenticationServiceImpl;
import com.perflibnetcracker.authenticationservice.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

@RestController
@CrossOrigin
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;

    @Autowired
    private AuthenticationController(AuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }

    private UserServiceImpl userService;

    @Autowired
    private void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    private BookService bookService;
    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    private RatedService ratedService;
    @Autowired
    public void setRatedService(RatedService ratedService) {
        this.ratedService = ratedService;
    }


    @GetMapping("/api/service/authentication/authenticated/")
    public AuthenticationDTO success() {
        return new AuthenticationDTO("successes");
    }

    @PostMapping("/api/service/authentication/registration/")
    public User newUser(@RequestBody User user) throws Exception {
        User userObj = authenticationService.saveUser(user);
        return userObj;
    }

    @GetMapping("/api/service/authentication/authenticated/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        return userMapper.userToDTO(user);
    }

    @GetMapping("/api/service/authentication/authenticated/rated/{id_book}")
    public BookDTO getRatedForUser(@AuthenticationPrincipal UserDetails currentUser, @PathVariable Long id_book){

        System.out.println(currentUser.getUsername());
        return ratedService.ratedByMe(currentUser.getUsername(), id_book);
    }

    @RequestMapping(value = "/api/service/authentication/authenticated/userLogout")
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        SecurityContextHolder.clearContext();
        // session= request.getSession(false);
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

    @PutMapping("/api/service/authentication/authenticated/update-book/{id}")
    public ResponseEntity<Book> updateEmployee(@AuthenticationPrincipal UserDetails currentUser, @PathVariable(value = "id") Long id,
                                               @RequestBody Book bookDetails) {
        System.out.println(currentUser.getUsername());
        System.out.println(bookDetails);
        Double newRating = bookDetails.getRating();
        bookService.newRated(newRating, id);
        bookService.setNewRatFOrBookByUser(id, currentUser.getUsername());
        return new ResponseEntity("Rating correct!", HttpStatus.OK);
    }

}
