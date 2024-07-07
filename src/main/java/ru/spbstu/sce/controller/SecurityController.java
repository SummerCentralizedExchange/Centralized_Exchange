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
import ru.spbstu.sce.accessControl.JwtCore;
import ru.spbstu.sce.db.entity.user.SinginRequest;
import ru.spbstu.sce.db.entity.user.SignupRequest;
import ru.spbstu.sce.db.entity.user.User;
import ru.spbstu.sce.db.repositorys.UserRepository;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@RestController
@RequestMapping("/auth")
public class SecurityController {
    private final Logger logger = LoggerFactory.getLogger(SecurityController.class);

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtCore jwtCore;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtCore(JwtCore jwtCore) {
        this.jwtCore = jwtCore;
    }

    @PostMapping("/signup")
    ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByLogin(signupRequest.getUserName())) {
            logger.info("User {} already exists", signupRequest.getUserName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Choose different name");
        }

        String apiKey = UUID.randomUUID().toString();
        logger.info("Creating new user {}", signupRequest.getUserName());
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        User user = new User();
        user.setLogin(signupRequest.getUserName());
        user.setPassword(encodedPassword);
        user.setApiKey(apiKey);
        userRepository.save(user);
        logger.info("User {} created", signupRequest.getUserName());

        return ResponseEntity.ok("Success, baby");
    }

    @PostMapping("/signin")
    ResponseEntity<?> signin(@RequestBody SinginRequest singinRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(singinRequest.getUserName(), singinRequest.getPassword()));
            logger.info(authentication.getPrincipal().toString());
        } catch (BadCredentialsException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String apikey = jwtCore.generateToken(authentication);
        return ResponseEntity.ok(apikey);
    }

}
