package ru.spbstu.sce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.sce.user.CreateUserRequest;
import ru.spbstu.sce.user.CreateUserResponse;
import ru.spbstu.sce.user.User;
import ru.spbstu.sce.user.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) {
        User user = userService.createUser(request);
        return new CreateUserResponse(user.getUid(), user.getApiKey());
    }
}
