package com.perflibnetcracker.authenticationservice.DTO;

import com.perflibnetcracker.authenticationservice.model.Subscription;
import com.perflibnetcracker.authenticationservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserDTO {
    private Long id;
    private String username;
    private Set<Subscription> subscriptions;
    private Boolean hasSub;

    public UserDTO(User user, Boolean hasSub) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.hasSub = hasSub;
        this.subscriptions = user.getSubscriptions();
    }
}
