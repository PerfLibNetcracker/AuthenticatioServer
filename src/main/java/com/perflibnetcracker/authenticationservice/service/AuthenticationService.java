package com.perflibnetcracker.authenticationservice.service;

import com.perflibnetcracker.authenticationservice.model.User;
import com.perflibnetcracker.authenticationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface AuthenticationService {

    public User saveUser(User user);

    public User fetchUserByUsername(String userName);

    public User fetchUserByUserNameAndPassword(String userName, String password);
}
