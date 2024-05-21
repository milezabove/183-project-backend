package com.example.m183project.mapper;
import com.example.m183project.domain.User;
import com.example.m183project.service.dto.CredentialsDTO;
import com.example.m183project.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {
    UserDTO toDto(User user);

    User toEntity(UserDTO userDTO);

    @Mapping(target = "password", ignore = true)
    User credentialsToUser(CredentialsDTO credentialsDTO);
}
