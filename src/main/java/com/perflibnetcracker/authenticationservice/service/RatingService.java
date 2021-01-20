package com.perflibnetcracker.authenticationservice.service;

import com.perflibnetcracker.authenticationservice.DTO.UserBookDTO;

public interface RatingService {
    UserBookDTO getIsBookRatedByUserDTO(String username, Long bookId);
}
