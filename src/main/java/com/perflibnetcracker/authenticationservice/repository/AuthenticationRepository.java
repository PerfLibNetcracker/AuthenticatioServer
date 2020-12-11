package com.perflibnetcracker.authenticationservice.repository;

import com.perflibnetcracker.authenticationservice.model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationRepository extends JpaRepository<Credential, Long> {

    public Credential findByEmailId(String emailId);

    public Credential findByEmailIdAndPassword(String emailId, String password);

}
