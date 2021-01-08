package com.perflibnetcracker.authenticationservice.service;

import com.perflibnetcracker.authenticationservice.DTO.BookDTO;

public interface RatedService {
    BookDTO ratingByUser(String username, Long bookId);
}
