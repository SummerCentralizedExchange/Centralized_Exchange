package ru.spbstu.sce.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.spbstu.sce.accesscontrol.TokenProvider;
import ru.spbstu.sce.db.entity.user.SigninRequest;
import ru.spbstu.sce.db.entity.user.SignupRequest;
import ru.spbstu.sce.db.entity.user.User;
import ru.spbstu.sce.db.repositories.UserRepository;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@RestController
@RequestMapping("/auth")
public class SecurityController {
    private final Logger logger = LoggerFactory.getLogger(SecurityController.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/signup")
    ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByLogin(signupRequest.username())) {
            logger.info("User {} already exists", signupRequest.username());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Choose different name");
        }
        if (signupRequest.username().isEmpty()) {
            return ResponseEntity.badRequest().body("Username cannot be empty");
        }
        if (signupRequest.password().isEmpty()) {
            return ResponseEntity.badRequest().body("Password cannot be empty");
        }
        logger.info("Creating new user {}", signupRequest.username());
        String encodedPassword = passwordEncoder.encode(signupRequest.password());
        userRepository.save(new User(signupRequest.username(), encodedPassword));
        logger.info("User {} created", signupRequest.username());
        return ResponseEntity.ok("User created successfully");
    }

    @PostMapping("/signin")
    ResponseEntity<?> signin(@RequestBody SigninRequest signinRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.username(), signinRequest.password()));
            logger.info(authentication.getPrincipal().toString());
        } catch (BadCredentialsException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Instant now = Instant.now();
        String apikey = tokenProvider.generateToken(authentication, now);
        return ResponseEntity.ok(apikey);
    }

}
