package com.kadirgurturk.QuizApp.controller;

import com.kadirgurturk.QuizApp.buisness.request.AuthRequest.AuthResponse;
import com.kadirgurturk.QuizApp.buisness.request.AuthRequest.RefreshRequest;
import com.kadirgurturk.QuizApp.buisness.request.UserRequest.UserRequest;
import com.kadirgurturk.QuizApp.buisness.service.RefreshTokenService;
import com.kadirgurturk.QuizApp.buisness.service.UserService;
import com.kadirgurturk.QuizApp.entity.RefreshToken;
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

    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody UserRequest loginRequest){

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwtToken = jwtTokenProvider.generateToken(auth);

        User user = userService.findByName(loginRequest.getUserName()).get();

        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessege("Bearer " + jwtToken);
        authResponse.setAccessToken("Bearer " + jwtToken);
        authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
        authResponse.setMessege("");
        authResponse.setUserId(user.getId());

        return authResponse;

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequest register)
    {

        AuthResponse authResponse = new AuthResponse();

        if(userService.findByName(register.getUserName()).isPresent()){
            return new ResponseEntity<>("User already in use.", HttpStatus.BAD_REQUEST);
        }

            User user = new User();
            user.setUserName(register.getUserName());

            user.setPassword(passwordEncoder.encode(register.getPassword()));

            user.setAvatar_id(0);
            userService.save(user);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(register.getUserName(),register.getPassword());
            Authentication auth = authenticationManager.authenticate(authToken);

            SecurityContextHolder.getContext().setAuthentication(auth);

            String jwtToken = jwtTokenProvider.generateToken(auth);

        authResponse.setMessege("User successfully registered.");
        authResponse.setAccessToken("Bearer " + jwtToken);
        authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
        authResponse.setUserId(user.getId());

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);

    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshRequest refreshRequest) {
        AuthResponse response = new AuthResponse();
        RefreshToken token = refreshTokenService.getByUser(refreshRequest.getUserId());
        if(token.getToken().equals(refreshRequest.getRefreshToken()) &&
                !refreshTokenService.isRefreshExpired(token)) {

            User user = token.getUser();
            String jwtToken = jwtTokenProvider.generateJwtTokenByUserId(user.getId());
            response.setMessege("token successfully refreshed.");
            response.setAccessToken("Bearer " + jwtToken);
            response.setRefreshToken(refreshRequest.getRefreshToken());
            response.setUserId(user.getId());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessege("refresh token is not valid.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

    }


}
