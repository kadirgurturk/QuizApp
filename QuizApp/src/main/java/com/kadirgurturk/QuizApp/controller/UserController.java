package com.kadirgurturk.QuizApp.controller;

import com.kadirgurturk.QuizApp.buisness.service.UserService;
import com.kadirgurturk.QuizApp.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz/api/v1/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping
    public List<User> getAllUsers()
    {
        return userService.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User newUser)
    {
        return userService.save(newUser);
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId)
    {
        return userService.findById(userId).orElseThrow(() -> new RuntimeException("USER blunamado"));
    }

    @PutMapping("/{userId}")
    public User updateUserById(@PathVariable Long userId,@RequestBody User user)
    {
        return userService.updateUserById(userId,user);
    }

    @DeleteMapping("/{userId}")
    public void deleteOneUser(@PathVariable Long userId)
    {
        userService.deleteById(userId);
    }

}
