package com.perflibnetcracker.authenticationservice.service.implementation;

import com.perflibnetcracker.authenticationservice.model.User;
import com.perflibnetcracker.authenticationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class AuthenticationServiceImpl {

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        userForDB.setRoles(new HashSet<>());
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
