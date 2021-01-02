package com.perflibnetcracker.authenticationservice.mappers;

import com.perflibnetcracker.authenticationservice.DTO.UserDTO;
import com.perflibnetcracker.authenticationservice.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDTO userToDTO(User user);
}
