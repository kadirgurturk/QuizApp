package com.kadirgurturk.QuizApp.buisness.dto;

import com.kadirgurturk.QuizApp.entity.Comment;
import com.kadirgurturk.QuizApp.entity.Like;
import lombok.Data;

@Data
public class LikeDto {

    Long id;
    Long userId;
    String title;
    String text;
    Integer avatarId;
    String userName;
    Long postId;

    public LikeDto(Like entity) {
        id = entity.getId();
        userId = entity.getPost().getUser().getId();
        title = entity.getPost().getTitle();
        text = entity.getPost().getText();
        avatarId = entity.getPost().getUser().getAvatar_id();
        userName = entity.getUser().getUserName();
        postId = entity.getPost().getId();

    }

}
