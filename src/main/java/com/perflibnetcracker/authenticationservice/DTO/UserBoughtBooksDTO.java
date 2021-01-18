package com.perflibnetcracker.authenticationservice.DTO;

import com.perflibnetcracker.authenticationservice.model.BoughtBook;
import com.perflibnetcracker.authenticationservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBoughtBooksDTO {
    private Long id;
    private String username;
    private Set<BoughtBook> boughtBooks;
    private Boolean bought;

    public UserBoughtBooksDTO(Boolean bought, User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.boughtBooks = user.getBoughtBooks();
        this.bought = bought;
    }
}
