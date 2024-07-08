package ru.spbstu.sce.user;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    public User createUser(CreateUserRequest createUserRequest) {
        if(createUserRequest.getUserName().isEmpty() || createUserRequest.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Username and password must not be empty");
        }

        UUID uid = UUID.randomUUID();
        String apiKey = UUID.randomUUID().toString();

        User user = new User(createUserRequest.getUserName(), createUserRequest.getPassword(), uid, apiKey);

        /** TO DO
         * add user registration to the database if there is one*/

        return user;
    }
}
