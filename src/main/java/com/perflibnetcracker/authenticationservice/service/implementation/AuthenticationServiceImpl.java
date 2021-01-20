package com.perflibnetcracker.authenticationservice.service.implementation;

import com.perflibnetcracker.authenticationservice.exceptions.UserRoleNotFoundException;
import com.perflibnetcracker.authenticationservice.model.Role;
import com.perflibnetcracker.authenticationservice.model.User;
import com.perflibnetcracker.authenticationservice.repository.RoleRepository;
import com.perflibnetcracker.authenticationservice.repository.UserRepository;
import com.perflibnetcracker.authenticationservice.service.AuthenticationService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

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

    public User saveUser(User user) throws UsernameNotFoundException, UserRoleNotFoundException {
        String username = user.getUsername();
        if (!Strings.isBlank(username)) {
            if (userRepository.existsByUsername(username)) {
                throw new UsernameNotFoundException("Пользователь с именем " + username + " уже существует");
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String roleName = "ROLE_USER";
        Role role = roleRepository.findByName(roleName);
        if (isNull(role)) {
            throw new UserRoleNotFoundException("Роль с названием " + roleName + " не была найдена, проверьте БД");
        }
        user.getRoles().add(role);
        userRepository.save(user);
        return user;
    }
}
