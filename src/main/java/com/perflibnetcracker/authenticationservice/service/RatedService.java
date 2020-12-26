package com.perflibnetcracker.authenticationservice.service;

import com.perflibnetcracker.authenticationservice.DTO.BookDTO;

import java.util.List;

public interface RatedService {
    BookDTO ratedByMe(String username, Long id);
}
