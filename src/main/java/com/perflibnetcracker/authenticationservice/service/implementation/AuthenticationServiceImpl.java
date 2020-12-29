package com.perflibnetcracker.authenticationservice.service.implementation;

import com.perflibnetcracker.authenticationservice.model.Role;
import com.perflibnetcracker.authenticationservice.model.User;
import com.perflibnetcracker.authenticationservice.repository.RoleRepository;
import com.perflibnetcracker.authenticationservice.repository.UserRepository;
import com.perflibnetcracker.authenticationservice.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {


    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    private final UserRepository userRepository;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Метод реализует запись в БД нового пользователя
    public User saveUser(User user) throws Exception {
        String tempUsername = user.getUsername();
        if(tempUsername != null && !tempUsername.equals("")) {
            User userObj = fetchUserByUsername(tempUsername);
            if(userObj != null) {
                throw new Exception("Пользователь с именем " + tempUsername + "уже существует");
            }
        }
        User userForDB = user;
        userForDB.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName("ROLE_USER");
        Set setRoles = new HashSet();
        setRoles.add(role);
        userForDB.setRoles(setRoles);
        userRepository.save(userForDB);
        return userForDB;
    }

    public User fetchUserByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }

    public User fetchUserByUserNameAndPassword(String userName, String password) {
        return userRepository.findByUsernameAndPassword(userName, password);
    }
}
