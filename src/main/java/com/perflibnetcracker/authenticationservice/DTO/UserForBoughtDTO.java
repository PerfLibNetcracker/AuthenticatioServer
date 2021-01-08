package com.perflibnetcracker.authenticationservice.DTO;

import com.perflibnetcracker.authenticationservice.model.BoughtBooks;
import com.perflibnetcracker.authenticationservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserForBoughtDTO {
    private Long id;
    private String username;
    private Set<BoughtBooks> boughtBooks;
    private Boolean bought;

    public UserForBoughtDTO(Boolean bought, User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.boughtBooks = user.getBoughtBooks();
        this.bought = bought;
    }
}
