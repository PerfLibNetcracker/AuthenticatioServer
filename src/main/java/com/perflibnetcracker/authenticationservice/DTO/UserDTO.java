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

    public UserDTO(User user, Boolean hasSub) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.hasSub = hasSub;
    }
}
