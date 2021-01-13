package com.perflibnetcracker.authenticationservice.DTO;

import com.perflibnetcracker.authenticationservice.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserBookDTO {

    private Long id;
    private String name;
    private Boolean isRated;

    public UserBookDTO(Book book, Boolean isRated) {
        this.id = book.getId();
        this.name = book.getName();
        this.isRated = isRated;
    }
}
