package com.perflibnetcracker.authenticationservice.service.implementation;

import com.perflibnetcracker.authenticationservice.model.Role;
import com.perflibnetcracker.authenticationservice.model.User;
import com.perflibnetcracker.authenticationservice.repository.RoleRepository;
import com.perflibnetcracker.authenticationservice.repository.UserRepository;
import com.perflibnetcracker.authenticationservice.service.AuthenticationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public User saveUser(User user) throws Exception {
        String tempUsername = user.getUsername();
        if (tempUsername != null && !tempUsername.equals("")) {
            User userObj = fetchUserByUsername(tempUsername);
            if (userObj != null) {
                throw new Exception("Пользователь с именем " + tempUsername + "уже существует");
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName("ROLE_USER");
        Set<Role> setRoles = new HashSet<>();
        setRoles.add(role);
        user.setRoles(setRoles);
        userRepository.save(user);
        return user;
    }

    public User fetchUserByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }

    public User fetchUserByUserNameAndPassword(String userName, String password) {
        return userRepository.findByUsernameAndPassword(userName, password);
    }
}
