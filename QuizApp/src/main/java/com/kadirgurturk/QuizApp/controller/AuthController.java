package com.kadirgurturk.QuizApp.controller;

import com.kadirgurturk.QuizApp.buisness.request.UserRequest.UserRequest;
import com.kadirgurturk.QuizApp.buisness.service.UserService;
import com.kadirgurturk.QuizApp.entity.User;
import com.kadirgurturk.QuizApp.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("quiz/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;

    private UserService userService;

    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public String login(@RequestBody UserRequest loginRequest){

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwtToken = jwtTokenProvider.generateToken(auth);

        return "Bearer " + jwtToken;

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequest register)
    {
        if(userService.findByName(register.getUserName()).isPresent()){
            return new ResponseEntity<>("User already in use.", HttpStatus.BAD_REQUEST);
        }

            User user = new User();
            user.setUserName(register.getUserName());

            user.setPassword(passwordEncoder.encode(register.getPassword()));
            userService.save(user);
            return new ResponseEntity<>("User succesfuly registered", HttpStatus.CREATED);




    }


}
