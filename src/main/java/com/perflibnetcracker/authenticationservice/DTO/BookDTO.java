package com.perflibnetcracker.authenticationservice.DTO;

import com.perflibnetcracker.authenticationservice.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BookDTO {

    private Long id;
    private String name;
    private Boolean meRated;

    public BookDTO(Book book, Boolean meRated) {
        this.id = book.getId();
        this.name = book.getName();
        this.meRated = meRated;
    }
}
