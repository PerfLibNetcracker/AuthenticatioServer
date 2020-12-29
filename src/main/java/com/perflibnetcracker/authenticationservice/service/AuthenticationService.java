package com.perflibnetcracker.authenticationservice.service;

import com.perflibnetcracker.authenticationservice.model.User;


public interface AuthenticationService {

    User saveUser(User user) throws Exception;

    User fetchUserByUsername(String userName);

    User fetchUserByUserNameAndPassword(String userName, String password);
}
