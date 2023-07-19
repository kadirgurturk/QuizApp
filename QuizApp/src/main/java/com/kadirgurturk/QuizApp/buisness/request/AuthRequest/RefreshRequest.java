package com.kadirgurturk.QuizApp.buisness.request.AuthRequest;

import lombok.Data;

@Data
public class RefreshRequest {

    Long userId;
    String refreshToken;
}
