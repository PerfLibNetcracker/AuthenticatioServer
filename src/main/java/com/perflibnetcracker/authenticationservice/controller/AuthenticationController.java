package com.perflibnetcracker.authenticationservice.controller;


import com.perflibnetcracker.authenticationservice.DTO.AuthenticationDTO;
import com.perflibnetcracker.authenticationservice.DTO.UserInfoDTO;
import com.perflibnetcracker.authenticationservice.mappers.UserMapper;
import com.perflibnetcracker.authenticationservice.model.User;
import com.perflibnetcracker.authenticationservice.repository.UserRepository;
import com.perflibnetcracker.authenticationservice.service.AuthenticationService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "${spring.frontend.url}")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AuthenticationController(AuthenticationService authenticationService, UserRepository userRepository, UserMapper userMapper) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @GetMapping("${spring.urlmap}")
    public AuthenticationDTO success() {
        return new AuthenticationDTO("successes");
    }

    @PostMapping("${spring.urlmap}/registration")
    public User newUser(@RequestBody User user) throws Exception {
        return authenticationService.saveUser(user);
    }

    @GetMapping("${spring.urlmap}/{id}")
    public UserInfoDTO getUser(@PathVariable Long id) {
        return userMapper.userToDTO(userRepository.findById(id).orElseThrow());
    }
}
