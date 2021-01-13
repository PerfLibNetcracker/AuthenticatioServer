package com.perflibnetcracker.authenticationservice.mappers;

import com.perflibnetcracker.authenticationservice.DTO.UserInfoDTO;
import com.perflibnetcracker.authenticationservice.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserInfoDTO userToDTO(User user);
}
