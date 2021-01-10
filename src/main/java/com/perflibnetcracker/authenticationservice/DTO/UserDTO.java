package com.perflibnetcracker.authenticationservice.DTO;

import com.perflibnetcracker.authenticationservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserDTO {
    private Long id;
    private String username;
    private Boolean hasSub;
    private Boolean hasFreeBook;

    public UserDTO(User user, Boolean hasSub, Boolean hasFreeBook) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.hasSub = hasSub;
        this.hasFreeBook = hasFreeBook;
    }
}
