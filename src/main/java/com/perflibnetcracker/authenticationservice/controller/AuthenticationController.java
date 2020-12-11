package com.perflibnetcracker.authenticationservice.controller;


import com.perflibnetcracker.authenticationservice.model.Credential;
import com.perflibnetcracker.authenticationservice.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/api/service/authentication/register-user")
    public Credential registerUser(@RequestBody Credential credential) throws Exception {
        String tempEmailId = credential.getEmailId();
        if (tempEmailId != null && !tempEmailId.equals("")) {
            Credential credentialObj = authenticationService.fetchUserByEmailId(tempEmailId);
            if(credentialObj != null) {
                throw new Exception("User with " + tempEmailId + "is already has");
            }
        }
        Credential credentialObj;
        credentialObj = authenticationService.saveUser(credential);
        return credentialObj;
    }

    @PostMapping("/api/service/authentication/login-user")
    public Credential loginUser(@RequestBody Credential credential) throws Exception {
        String tempEmailId = credential.getEmailId();
        String tempPassword = credential.getPassword();
        Credential credentialObj = null;
        if (tempEmailId != null && tempPassword != null) {
            credentialObj = authenticationService.fetchUserByEmailIdAndPassword(tempEmailId, tempPassword);

        }
        if(credentialObj == null) {
            throw new Exception("User not found. Bad credentials");
        }
        return credentialObj;
    }


}
