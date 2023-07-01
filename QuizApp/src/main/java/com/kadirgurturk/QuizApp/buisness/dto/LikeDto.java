package com.kadirgurturk.QuizApp.buisness.dto;

import com.kadirgurturk.QuizApp.entity.Comment;
import com.kadirgurturk.QuizApp.entity.Like;
import lombok.Data;

@Data
public class LikeDto {

    Long id;
    Long userId;
    String userName;
    Long postId;

    public LikeDto(Like entity) {
        id = entity.getId();
        userId = entity.getUser().getId();
        userName = entity.getUser().getUserName();
        postId = entity.getPost().getId();

    }

}
