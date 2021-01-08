package com.perflibnetcracker.authenticationservice.service;

import com.perflibnetcracker.authenticationservice.DTO.BookDTO;

public interface RatedService {

    BookDTO ratedByUser(String username, Long id);

}
