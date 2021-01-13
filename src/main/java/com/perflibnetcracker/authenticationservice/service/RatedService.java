package com.perflibnetcracker.authenticationservice.service;

import com.perflibnetcracker.authenticationservice.DTO.UserBookDTO;

public interface RatedService {
    UserBookDTO isBookRatedByUser(String username, Long bookId);
}
