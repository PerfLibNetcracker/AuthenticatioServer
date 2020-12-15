package com.perflibnetcracker.authenticationservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthenticationDTO {
    private String message;


    @Override
    public String toString() {
        return String.format("Login [message=%s]", message);
    }
}
