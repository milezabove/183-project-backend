package com.example.m183project.web.rest;

import com.example.m183project.config.UserAuthProvider;
import com.example.m183project.service.UserService;
import com.example.m183project.service.dto.CredentialsDTO;
import com.example.m183project.service.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api")
@Slf4j
public class AuthenticationController {
    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    public AuthenticationController(UserService userService, UserAuthProvider userAuthProvider) {
        this.userService = userService;
        this.userAuthProvider = userAuthProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody CredentialsDTO credentialsDto) {
        UserDTO user = userService.login(credentialsDto);
        user.setToken(userAuthProvider.createToken(user));
        log.info("Successful login for {}", user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register (@RequestBody CredentialsDTO registerDTO) {
        UserDTO user = userService.register(registerDTO);
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(user);
    }
}
