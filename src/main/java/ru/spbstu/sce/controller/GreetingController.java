package ru.spbstu.sce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.sce.service.Greeting;

import java.security.Principal;

@RestController
@RequestMapping("/secured")
public class GreetingController {

    private static final String template = "Hello, %s!";

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(String.format(template, name));
    }

    @GetMapping("/user")
    public String userAccess(Principal principal) {
        if (principal != null) {
            return principal.getName();
        } else {
            return "You are not logged in";
        }
    }
}