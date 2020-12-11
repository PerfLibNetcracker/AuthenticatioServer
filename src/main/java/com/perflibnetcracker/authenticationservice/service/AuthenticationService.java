package com.perflibnetcracker.authenticationservice.service;

import com.perflibnetcracker.authenticationservice.model.Credential;
import com.perflibnetcracker.authenticationservice.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationRepository authenticationRepository;

    @Autowired
    public AuthenticationService(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    public Credential saveUser(Credential credential) {
        return authenticationRepository.save(credential);
    }

    public Credential fetchUserByEmailId(String email) {
        return authenticationRepository.findByEmailId(email);
    }

    public Credential fetchUserByEmailIdAndPassword(String email, String password) {
        return authenticationRepository.findByEmailIdAndPassword(email, password);
    }
}
