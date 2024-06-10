package com.example.m183project.service;

import com.example.m183project.domain.Recipe;
import com.example.m183project.domain.User;
import com.example.m183project.exception.LoginException;
import com.example.m183project.exception.UsernameAlreadyExistsException;
import com.example.m183project.mapper.UserMapper;
import com.example.m183project.repository.UserRepository;
import com.example.m183project.service.dto.CredentialsDTO;
import com.example.m183project.service.dto.RecipeDTO;
import com.example.m183project.service.dto.UserDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.CharBuffer;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDTO login (CredentialsDTO credentialsDTO) {
        Optional<User> optionalUser = userRepository.findByUsername(credentialsDTO.username());
        if (optionalUser.isEmpty()) {
            log.error("Login failed. User not found");
            throw new LoginException("Unknown user", HttpStatus.NOT_FOUND);
        }
        User user = optionalUser.get();
        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDTO.password()),
                user.getPassword())) {
            log.info("Login for user {} was successful", user.getUsername());
            return userMapper.toDto(user);
        } else {
            log.error("Login failed. Invalid password for user '{}'", user.getUsername());
            throw new LoginException("Invalid password", HttpStatus.BAD_REQUEST);
        }
    }

    public UserDTO register(CredentialsDTO credentialsDTO) {
        if (userRepository.findByUsername(credentialsDTO.username()).isPresent()) {
            throw new UsernameAlreadyExistsException("Username is already taken");
        }
        User user = userMapper.credentialsToUser(credentialsDTO);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(credentialsDTO.password())));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
}
