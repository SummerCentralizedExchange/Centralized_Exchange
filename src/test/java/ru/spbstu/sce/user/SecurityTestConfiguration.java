package ru.spbstu.sce.user;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Collections;
import java.util.List;

@TestConfiguration
public class SecurityTestConfiguration {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        User basicUser = new User("testuser", "password", Collections.emptyList());

        return new InMemoryUserDetailsManager(List.of(
                basicUser
        ));
    }

}
