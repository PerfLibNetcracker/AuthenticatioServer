package com.perflibnetcracker.authenticationservice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthenticationBean {
    private String message;


    @Override
    public String toString() {
        return String.format("Login [message=%s]", message);
    }
}
