package com.perflibnetcracker.authenticationservice.service;

import com.perflibnetcracker.authenticationservice.exceptions.UserRoleNotFoundException;
import com.perflibnetcracker.authenticationservice.model.User;


public interface AuthenticationService {
    User saveUser(User user) throws UserRoleNotFoundException;
}
