package com.perflibnetcracker.authenticationservice.DTO;

import com.fasterxml.jackson.annotation.*;
import com.perflibnetcracker.authenticationservice.model.Author;
import com.perflibnetcracker.authenticationservice.model.Book;
import com.perflibnetcracker.authenticationservice.model.Genre;
import com.perflibnetcracker.authenticationservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BookDTO {
    private Long id;
    private String name;
    private Boolean meRated;

//    private Set<User> user;

    public BookDTO(Book book, Boolean meRated){
//        this.user = book.getUsers();
        this.id = book.getId();
        this.name = book.getName();
        this.meRated = meRated;
    }

    public String getUser(User user) {
        return user != null ? user.getUsername() : "<none>";
    }
}
