package com.kadirgurturk.QuizApp.buisness.service;

import com.kadirgurturk.QuizApp.database.RefreshTokenRepository;
import com.kadirgurturk.QuizApp.entity.RefreshToken;
import com.kadirgurturk.QuizApp.entity.User;
import lombok.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import java.util.Date;

@Service
public class RefreshTokenService {

    @Value("${refresh.token.val}")
    Long expireSeconds;

    private RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String createRefreshToken(User user) {
        RefreshToken token = refreshTokenRepository.findByUserId(user.getId());
        if(token == null) {
            token =	new RefreshToken();
            token.setUser(user);
        }
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Date.from(Instant.now().plusSeconds(expireSeconds)));
        refreshTokenRepository.save(token);
        return token.getToken();
    }



    public boolean isRefreshExpired(RefreshToken token) {
        return token.getExpiryDate().before(new Date());
    }

    public RefreshToken getByUser(Long userId) {
        return refreshTokenRepository.findByUserId(userId);
    }

}
