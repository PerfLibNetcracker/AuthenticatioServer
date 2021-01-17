package com.perflibnetcracker.authenticationservice.controller;


import com.perflibnetcracker.authenticationservice.DTO.AuthenticationDTO;
import com.perflibnetcracker.authenticationservice.DTO.UserBookDTO;
import com.perflibnetcracker.authenticationservice.DTO.UserInfoDTO;
import com.perflibnetcracker.authenticationservice.mappers.UserMapper;
import com.perflibnetcracker.authenticationservice.model.Book;
import com.perflibnetcracker.authenticationservice.model.User;
import com.perflibnetcracker.authenticationservice.repository.UserRepository;
import com.perflibnetcracker.authenticationservice.service.AuthenticationService;
import com.perflibnetcracker.authenticationservice.service.BookService;
import com.perflibnetcracker.authenticationservice.service.RatedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "${spring.frontend.url}")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BookService bookService;
    private final RatedService ratedService;

    public AuthenticationController(AuthenticationService authenticationService, UserRepository userRepository, UserMapper userMapper, BookService bookService, RatedService ratedService) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
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
        return authenticationService.saveUser(user);
    }

    @GetMapping("${spring.urlmap}/{id}")
    public UserInfoDTO getUser(@PathVariable Long id) {
        return userMapper.userToDTO(userRepository.findById(id).orElseThrow());
    }

    @GetMapping("${spring.urlmap}/user-book-rated/{bookId}")
    public UserBookDTO getRatedUserBooks(@AuthenticationPrincipal UserDetails currentUser, @PathVariable Long bookId) {
        return ratedService.isBookRatedByUser(currentUser.getUsername(), bookId);
    }

    @RequestMapping(value = "${spring.urlmap}/userLogout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
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
        return new ResponseEntity<>("Logout Successful!", HttpStatus.OK);
    }

    @PutMapping("${spring.urlmap}/update-book/{id}")
    public ResponseEntity<String> updateRatingForBook(@AuthenticationPrincipal UserDetails currentUser, @PathVariable(value = "id") Long id,
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
