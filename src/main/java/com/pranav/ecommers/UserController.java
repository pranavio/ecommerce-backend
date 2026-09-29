package com.pranav.ecommers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final userService userService;

    @GetMapping("/api/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @PostMapping("/api/users")
    public String addUser(@RequestBody User user) {
        userService.addUser(user);
        return "User added successfully";
    }
    @GetMapping("/api/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
