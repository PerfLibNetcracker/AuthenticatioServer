package com.perflibnetcracker.authenticationservice.DTO;

import com.perflibnetcracker.authenticationservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    private Long id;
    private String username;
    private Boolean hasSub;
    private Boolean hasFreeBook;

    public UserInfoDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }
}
